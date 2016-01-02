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

package com.digitalpetri.opcua.stack.client.handlers;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.UaServiceFaultException;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.MessageAbortedException;
import com.digitalpetri.opcua.stack.core.channel.SerializationQueue;
import com.digitalpetri.opcua.stack.core.channel.headers.AsymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channel.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channel.messages.ErrorMessage;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.channel.messages.TcpMessageDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;
import com.digitalpetri.opcua.stack.core.types.structured.ChannelSecurityToken;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.OpenSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.OpenSecureChannelResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.digitalpetri.opcua.stack.core.util.LongSequence;
import com.digitalpetri.opcua.stack.core.util.NonceUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpClientAsymmetricHandler extends ByteToMessageDecoder implements HeaderDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<ByteBuf> chunkBuffers = new ArrayList<>();

    private ScheduledFuture renewFuture;

    private volatile Timeout secureChannelTimeout;

    private final AtomicReference<AsymmetricSecurityHeader> headerRef = new AtomicReference<>();

    private final LongSequence requestId;

    private final int maxChunkCount;
    private final int maxChunkSize;

    private final UaTcpStackClient client;
    private final SerializationQueue serializationQueue;
    private final ClientSecureChannel secureChannel;
    private final CompletableFuture<ClientSecureChannel> handshakeFuture;

    public UaTcpClientAsymmetricHandler(UaTcpStackClient client,
                                        SerializationQueue serializationQueue,
                                        ClientSecureChannel secureChannel,
                                        CompletableFuture<ClientSecureChannel> handshakeFuture) {

        this.client = client;
        this.serializationQueue = serializationQueue;
        this.secureChannel = secureChannel;
        this.handshakeFuture = handshakeFuture;

        maxChunkCount = serializationQueue.getParameters().getLocalMaxChunkCount();
        maxChunkSize = serializationQueue.getParameters().getLocalReceiveBufferSize();

        secureChannel
                .attr(ClientSecureChannel.KEY_REQUEST_ID_SEQUENCE)
                .setIfAbsent(new LongSequence(1L, UInteger.MAX_VALUE));

        requestId = secureChannel.attr(ClientSecureChannel.KEY_REQUEST_ID_SEQUENCE).get();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (renewFuture != null) renewFuture.cancel(false);

        handshakeFuture.completeExceptionally(
                new UaException(StatusCodes.Bad_ConnectionClosed, "connection closed"));

        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        SecurityTokenRequestType requestType = secureChannel.getChannelId() == 0 ?
                SecurityTokenRequestType.Issue : SecurityTokenRequestType.Renew;

        ByteString clientNonce = secureChannel.isSymmetricSigningEnabled() ?
                NonceUtil.generateNonce(secureChannel.getSecurityPolicy().getSymmetricEncryptionAlgorithm()) :
                ByteString.NULL_VALUE;

        secureChannel.setLocalNonce(clientNonce);

        OpenSecureChannelRequest request = new OpenSecureChannelRequest(
                new RequestHeader(null, DateTime.now(), uint(0), uint(0), null, uint(0), null),
                uint(PROTOCOL_VERSION),
                requestType,
                secureChannel.getMessageSecurityMode(),
                secureChannel.getLocalNonce(),
                client.getChannelLifetime());

        secureChannelTimeout = startSecureChannelTimeout(ctx);

        sendOpenSecureChannelRequest(ctx, request);
    }

    private Timeout startSecureChannelTimeout(ChannelHandlerContext ctx) {
        return client.getConfig().getWheelTimer().newTimeout(
                timeout -> {
                    if (!timeout.isCancelled()) {
                        handshakeFuture.completeExceptionally(
                                new UaException(StatusCodes.Bad_Timeout,
                                        "timed out waiting for secure channel"));
                        ctx.close();
                    }
                },
                5, TimeUnit.SECONDS);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof OpenSecureChannelRequest) {
            sendOpenSecureChannelRequest(ctx, (OpenSecureChannelRequest) evt);
        } else if (evt instanceof CloseSecureChannelRequest) {
            sendCloseSecureChannelRequest(ctx, (CloseSecureChannelRequest) evt);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.readableBytes() >= HEADER_LENGTH &&
                buffer.readableBytes() >= getMessageLength(buffer)) {

            int messageLength = getMessageLength(buffer);
            MessageType messageType = MessageType.fromMediumInt(buffer.getMedium(buffer.readerIndex()));

            switch (messageType) {
                case OpenSecureChannel:
                    onOpenSecureChannel(ctx, buffer.readSlice(messageLength));
                    break;

                case Error:
                    onError(ctx, buffer.readSlice(messageLength));
                    break;

                default:
                    out.add(buffer.readSlice(messageLength).retain());

            }
        }
    }

    private void onOpenSecureChannel(ChannelHandlerContext ctx, ByteBuf buffer) throws UaException {
        if (secureChannelTimeout != null && !secureChannelTimeout.cancel()) {
            secureChannelTimeout = null;
            handshakeFuture.completeExceptionally(
                    new UaException(StatusCodes.Bad_Timeout,
                            "timed out waiting for secure channel"));
            ctx.close();
            return;
        }

        buffer.skipBytes(3 + 1 + 4); // skip messageType, chunkType, messageSize

        long secureChannelId = buffer.readUnsignedInt();

        secureChannel.setChannelId(secureChannelId);

        AsymmetricSecurityHeader securityHeader = AsymmetricSecurityHeader.decode(buffer);
        if (!headerRef.compareAndSet(null, securityHeader)) {
            if (!securityHeader.equals(headerRef.get())) {
                throw new UaRuntimeException(StatusCodes.Bad_SecurityChecksFailed,
                        "subsequent AsymmetricSecurityHeader did not match");
            }
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

        char chunkType = (char) buffer.getByte(3);

        if (chunkType == 'A' || chunkType == 'F') {
            final List<ByteBuf> buffersToDecode = chunkBuffers;
            chunkBuffers = new ArrayList<>(maxChunkCount);

            serializationQueue.decode((binaryDecoder, chunkDecoder) -> {
                ByteBuf decodedBuffer = null;

                try {
                    decodedBuffer = chunkDecoder.decodeAsymmetric(secureChannel, buffersToDecode);

                    UaResponseMessage responseMessage = binaryDecoder
                            .setBuffer(decodedBuffer)
                            .decodeMessage(null);

                    StatusCode serviceResult = responseMessage.getResponseHeader().getServiceResult();

                    if (serviceResult.isGood()) {
                        OpenSecureChannelResponse response = (OpenSecureChannelResponse) responseMessage;

                        secureChannel.setChannelId(response.getSecurityToken().getChannelId().longValue());
                        logger.debug("Received OpenSecureChannelResponse.");

                        installSecurityToken(ctx, response);
                    } else {
                        ServiceFault serviceFault = (responseMessage instanceof ServiceFault) ?
                                (ServiceFault) responseMessage :
                                new ServiceFault(responseMessage.getResponseHeader());

                        throw new UaServiceFaultException(serviceFault);
                    }
                } catch (MessageAbortedException e) {
                    logger.error("Received message abort chunk; error={}, reason={}", e.getStatusCode(), e.getMessage());
                    ctx.close();
                } catch (Throwable t) {
                    logger.error("Error decoding OpenSecureChannelResponse: {}", t.getMessage(), t);
                    ctx.close();
                } finally {
                    if (decodedBuffer != null) {
                        decodedBuffer.release();
                    }
                    buffersToDecode.clear();
                }
            });
        }
    }

    private void installSecurityToken(ChannelHandlerContext ctx, OpenSecureChannelResponse response) {
        ChannelSecurity.SecuritySecrets newKeys = null;
        if (response.getServerProtocolVersion().longValue() < PROTOCOL_VERSION) {
            throw new UaRuntimeException(StatusCodes.Bad_ProtocolVersionUnsupported,
                    "server protocol version unsupported: " + response.getServerProtocolVersion());
        }

        ChannelSecurityToken newToken = response.getSecurityToken();

        if (secureChannel.isSymmetricSigningEnabled()) {
            secureChannel.setRemoteNonce(response.getServerNonce());

            newKeys = ChannelSecurity.generateKeyPair(
                    secureChannel,
                    secureChannel.getLocalNonce(),
                    secureChannel.getRemoteNonce()
            );
        }

        ChannelSecurity oldSecrets = secureChannel.getChannelSecurity();
        ChannelSecurity.SecuritySecrets oldKeys = oldSecrets != null ? oldSecrets.getCurrentKeys() : null;
        ChannelSecurityToken oldToken = oldSecrets != null ? oldSecrets.getCurrentToken() : null;

        secureChannel.setChannelSecurity(new ChannelSecurity(newKeys, newToken, oldKeys, oldToken));

        DateTime createdAt = response.getSecurityToken().getCreatedAt();
        long revisedLifetime = response.getSecurityToken().getRevisedLifetime().longValue();

        if (revisedLifetime > 0) {
            long renewAt = (long) (revisedLifetime * 0.75);
            renewFuture = ctx.executor().schedule(() -> renewSecureChannel(ctx), renewAt, TimeUnit.MILLISECONDS);
        } else {
            logger.warn("Server revised secure channel lifetime to 0; renewal will not occur.");
        }

        ctx.executor().execute(() -> {
            // SecureChannel is ready; remove the acknowledge handler and add the symmetric handler.
            if (ctx.pipeline().get(UaTcpClientAcknowledgeHandler.class) != null) {
                ctx.pipeline().remove(UaTcpClientAcknowledgeHandler.class);
                ctx.pipeline().addLast(new UaTcpClientSymmetricHandler(
                        client, serializationQueue, secureChannel, handshakeFuture));
            }
        });

        ChannelSecurity channelSecurity = secureChannel.getChannelSecurity();

        long currentTokenId = channelSecurity.getCurrentToken().getTokenId().longValue();

        long previousTokenId = channelSecurity.getPreviousToken()
                        .map(t -> t.getTokenId().longValue()).orElse(-1L);

        logger.debug(
                "SecureChannel id={}, currentTokenId={}, previousTokenId={}, lifetime={}ms, createdAt={}",
                secureChannel.getChannelId(),
                currentTokenId,
                previousTokenId,
                revisedLifetime,
                createdAt);
    }

    private void sendOpenSecureChannelRequest(ChannelHandlerContext ctx, OpenSecureChannelRequest request) {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

            try {
                binaryEncoder.setBuffer(messageBuffer);
                binaryEncoder.encodeMessage(null, request);

                List<ByteBuf> chunks = chunkEncoder.encodeAsymmetric(
                        secureChannel,
                        MessageType.OpenSecureChannel,
                        messageBuffer,
                        requestId.getAndIncrement()
                );

                ctx.executor().execute(() -> {
                    chunks.forEach(c -> ctx.write(c, ctx.voidPromise()));
                    ctx.flush();
                });

                ChannelSecurity channelSecurity = secureChannel.getChannelSecurity();

                long currentTokenId = channelSecurity != null ?
                        channelSecurity.getCurrentToken().getTokenId().longValue() : -1L;

                long previousTokenId = channelSecurity != null ?
                        channelSecurity.getPreviousToken().map(t -> t.getTokenId().longValue()).orElse(-1L) : -1L;

                logger.debug(
                        "Sent OpenSecureChannelRequest ({}, id={}, currentToken={}, previousToken={}).",
                        request.getRequestType(),
                        secureChannel.getChannelId(),
                        currentTokenId,
                        previousTokenId);
            } catch (UaException e) {
                logger.error("Error encoding OpenSecureChannelRequest: {}", e.getMessage(), e);
                ctx.close();
            } finally {
                messageBuffer.release();
            }
        });
    }

    private void sendCloseSecureChannelRequest(ChannelHandlerContext ctx, CloseSecureChannelRequest request) {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

            try {
                binaryEncoder.setBuffer(messageBuffer);
                binaryEncoder.encodeMessage(null, request);

                List<ByteBuf> chunks = chunkEncoder.encodeSymmetric(
                        secureChannel,
                        MessageType.CloseSecureChannel,
                        messageBuffer,
                        requestId.getAndIncrement()
                );

                ctx.executor().execute(() -> {
                    chunks.forEach(c -> ctx.write(c, ctx.voidPromise()));
                    ctx.flush();
                    ctx.close();
                });

                secureChannel.setChannelId(0);

                logger.debug("Sent CloseSecureChannelRequest.");
            } catch (UaException e) {
                logger.error("Error Encoding CloseSecureChannelRequest: {}", e.getMessage(), e);
                ctx.close();
            } finally {
                messageBuffer.release();
            }
        });
    }

    private void renewSecureChannel(ChannelHandlerContext ctx) {
        ByteString clientNonce = secureChannel.isSymmetricSigningEnabled() ?
                NonceUtil.generateNonce(NonceUtil.getNonceLength(secureChannel.getSecurityPolicy().getSymmetricEncryptionAlgorithm())) :
                ByteString.NULL_VALUE;

        secureChannel.setLocalNonce(clientNonce);

        OpenSecureChannelRequest request = new OpenSecureChannelRequest(
                new RequestHeader(null, DateTime.now(), uint(0), uint(0), null, uint(0), null),
                uint(PROTOCOL_VERSION),
                SecurityTokenRequestType.Renew,
                secureChannel.getMessageSecurityMode(),
                secureChannel.getLocalNonce(),
                client.getChannelLifetime());

        sendOpenSecureChannelRequest(ctx, request);
    }

    private void onError(ChannelHandlerContext ctx, ByteBuf buffer) {
        try {
            ErrorMessage errorMessage = TcpMessageDecoder.decodeError(buffer);
            StatusCode errorCode = errorMessage.getError();

            boolean secureChannelError =
                    errorCode.getValue() == StatusCodes.Bad_TcpSecureChannelUnknown ||
                            errorCode.getValue() == StatusCodes.Bad_SecureChannelIdInvalid;

            if (secureChannelError) {
                secureChannel.setChannelId(0);
            }

            logger.error("Received error message: " + errorMessage);

            handshakeFuture.completeExceptionally(new UaException(errorCode, errorMessage.getReason()));
        } catch (UaException e) {
            logger.error("An exception occurred while decoding an error message: {}", e.getMessage(), e);
        } finally {
            ctx.close();
        }
    }

}
