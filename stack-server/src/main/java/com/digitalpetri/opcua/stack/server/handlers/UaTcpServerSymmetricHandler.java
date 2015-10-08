/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.server.handlers;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.List;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.application.services.ServiceResponse;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.ExceptionHandler;
import com.digitalpetri.opcua.stack.core.channel.SerializationQueue;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channel.headers.SymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channel.messages.ErrorMessage;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpStackServer;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UaTcpServerSymmetricHandler extends ByteToMessageCodec<ServiceResponse> implements HeaderDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<ByteBuf> chunkBuffers;

    private final int maxChunkCount;
    private final int maxChunkSize;

    private final UaTcpStackServer server;
    private final SerializationQueue serializationQueue;
    private final ServerSecureChannel secureChannel;

    public UaTcpServerSymmetricHandler(UaTcpStackServer server,
                                       SerializationQueue serializationQueue,
                                       ServerSecureChannel secureChannel) {

        this.server = server;
        this.serializationQueue = serializationQueue;
        this.secureChannel = secureChannel;

        maxChunkCount = serializationQueue.getParameters().getLocalMaxChunkCount();
        maxChunkSize = serializationQueue.getParameters().getLocalReceiveBufferSize();

        chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (secureChannel != null) {
            secureChannel.attr(UaTcpStackServer.BoundChannelKey).set(ctx.channel());
        }

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (secureChannel != null) {
            secureChannel.attr(UaTcpStackServer.BoundChannelKey).remove();
        }

        super.channelInactive(ctx);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServiceResponse message, ByteBuf out) throws Exception {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

            try {
                binaryEncoder.setBuffer(messageBuffer);
                binaryEncoder.encodeMessage(null, message.getResponse());

                final List<ByteBuf> chunks = chunkEncoder.encodeSymmetricResponse(
                        secureChannel,
                        MessageType.SecureMessage,
                        messageBuffer,
                        message.getRequestId()
                );

                ctx.executor().execute(() -> {
                    chunks.forEach(c -> ctx.write(c, ctx.voidPromise()));
                    ctx.flush();
                });
            } catch (UaException e) {
                logger.error("Error encoding {}: {}", message.getResponse().getClass(), e.getMessage(), e);
                ctx.close();
            } finally {
                messageBuffer.release();
            }
        });
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.readableBytes() >= HEADER_LENGTH &&
                buffer.readableBytes() >= getMessageLength(buffer)) {

            int messageLength = getMessageLength(buffer);
            MessageType messageType = MessageType.fromMediumInt(buffer.getMedium(buffer.readerIndex()));

            switch (messageType) {
                case SecureMessage:
                    onSecureMessage(ctx, buffer.readSlice(messageLength), out);
                    break;

                default:
                    out.add(buffer.readSlice(messageLength).retain());
            }
        }
    }

    private void onSecureMessage(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws UaException {
        buffer.skipBytes(3); // Skip messageType

        char chunkType = (char) buffer.readByte();

        if (chunkType == 'A') {
            chunkBuffers.forEach(ByteBuf::release);
            chunkBuffers.clear();
        } else {
            buffer.skipBytes(4); // Skip messageSize

            long secureChannelId = buffer.readUnsignedInt();
            if (secureChannelId != secureChannel.getChannelId()) {
                throw new UaException(StatusCodes.Bad_SecureChannelIdInvalid,
                        "invalid secure channel id: " + secureChannelId);
            }

            int chunkSize = buffer.readerIndex(0).readableBytes();
            if (chunkSize > maxChunkSize) {
                throw new UaException(StatusCodes.Bad_TcpMessageTooLarge,
                        String.format("max chunk size exceeded (%s)", maxChunkSize));
            }

            chunkBuffers.add(buffer.retain());

            if (chunkBuffers.size() > maxChunkCount) {
                throw new UaException(StatusCodes.Bad_TcpMessageTooLarge,
                        String.format("max chunk count exceeded (%s)", maxChunkCount));
            }

            if (chunkType == 'F') {
                final List<ByteBuf> buffersToDecode = chunkBuffers;
                chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);

                serializationQueue.decode((binaryDecoder, chunkDecoder) -> {
                    try {
                        validateChunkHeaders(buffersToDecode);

                        ByteBuf messageBuffer = chunkDecoder.decodeSymmetric(secureChannel, buffersToDecode);

                        binaryDecoder.setBuffer(messageBuffer);
                        UaRequestMessage request = binaryDecoder.decodeMessage(null);

                        ServiceRequest<UaRequestMessage, UaResponseMessage> serviceRequest = new ServiceRequest<>(
                                request,
                                chunkDecoder.getLastRequestId(),
                                server,
                                secureChannel
                        );

                        server.getExecutorService().execute(() -> server.receiveRequest(serviceRequest));

                        messageBuffer.release();
                        buffersToDecode.clear();
                    } catch (UaException e) {
                        logger.error("Error decoding symmetric message: {}", e.getMessage(), e);
                        ctx.close();
                    }
                });
            }
        }
    }


    private void validateChunkHeaders(List<ByteBuf> chunkBuffers) throws UaException {
        ChannelSecurity channelSecurity = secureChannel.getChannelSecurity();
        long currentTokenId = channelSecurity.getCurrentToken().getTokenId().longValue();
        long previousTokenId = channelSecurity.getPreviousToken()
                .map(t -> t.getTokenId().longValue())
                .orElse(-1L);

        for (ByteBuf chunkBuffer : chunkBuffers) {
            chunkBuffer.skipBytes(3 + 1 + 4 + 4); // skip messageType, chunkType, messageSize, secureChannelId

            SymmetricSecurityHeader securityHeader = SymmetricSecurityHeader.decode(chunkBuffer);

            if (securityHeader.getTokenId() != currentTokenId) {
                if (securityHeader.getTokenId() != previousTokenId) {
                    String message = String.format(
                            "received unknown secure channel token. " +
                                    "tokenId=%s, previousTokenId=%s, currentTokenId=%s",
                            securityHeader.getTokenId(), previousTokenId, currentTokenId);

                    throw new UaException(StatusCodes.Bad_SecureChannelTokenUnknown, message);
                }
            }

            chunkBuffer.readerIndex(0);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        chunkBuffers.forEach(ByteBuf::release);
        chunkBuffers.clear();

        if (cause instanceof IOException) {
            ctx.close();
            logger.debug("[remote={}] IOException caught; channel closed");
        } else {
            ErrorMessage errorMessage = ExceptionHandler.sendErrorMessage(ctx, cause);

            if (cause instanceof UaException) {
                logger.debug("[remote={}] UaException caught; sent {}",
                        ctx.channel().remoteAddress(), errorMessage, cause);
            } else {
                logger.error("[remote={}] Exception caught; sent {}",
                        ctx.channel().remoteAddress(), errorMessage, cause);
            }
        }
    }

}
