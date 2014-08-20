package com.digitalpetri.opcua.stack.server.channel;

import java.nio.ByteOrder;
import java.util.List;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.ExceptionHandler;
import com.digitalpetri.opcua.stack.core.channel.SerializationQueue;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channel.headers.SymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.structured.ChannelSecurityToken;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.digitalpetri.opcua.stack.server.ServiceRequest;
import com.digitalpetri.opcua.stack.server.ServiceResponse;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServer;
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

    private final UaTcpServer server;
    private final SerializationQueue serializationQueue;
    private final ServerSecureChannel secureChannel;

    public UaTcpServerSymmetricHandler(UaTcpServer server,
                                       SerializationQueue serializationQueue,
                                       ServerSecureChannel secureChannel) {

        this.server = server;
        this.serializationQueue = serializationQueue;
        this.secureChannel = secureChannel;

        maxChunkCount = serializationQueue.getParameters().getLocalMaxChunkCount();
        chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (secureChannel != null) {
            secureChannel.attr(UaTcpServer.BoundChannelKey).set(ctx.channel());
        }

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (secureChannel != null) {
            secureChannel.attr(UaTcpServer.BoundChannelKey).remove();
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

                final List<ByteBuf> chunks = chunkEncoder.encodeSymmetric(
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

        while (buffer.readableBytes() >= HeaderLength &&
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

            SymmetricSecurityHeader securityHeader = SymmetricSecurityHeader.decode(buffer);

            ChannelSecurity channelSecurity = secureChannel.getChannelSecurity();
            long currentTokenId = channelSecurity.getCurrentToken().getTokenId();

            if (securityHeader.getTokenId() != currentTokenId) {
                long previousTokenId = channelSecurity.getPreviousToken()
                        .map(ChannelSecurityToken::getTokenId)
                        .orElse(-1L);

                if (securityHeader.getTokenId() != previousTokenId) {
                    throw new UaException(StatusCodes.Bad_SecureChannelTokenUnknown,
                            "unknown secure channel token: " + securityHeader.getTokenId());
                }
            }

            chunkBuffers.add(buffer.readerIndex(0).retain());

            if (chunkBuffers.size() > maxChunkCount) {
                throw new UaException(StatusCodes.Bad_TcpMessageTooLarge,
                        String.format("max chunk count exceeded (%s)", maxChunkCount));
            }

            if (chunkType == 'F') {
                final List<ByteBuf> buffersToDecode = chunkBuffers;
                chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);

                serializationQueue.decode((binaryDecoder, chunkDecoder) -> {
                    try {
                        ByteBuf messageBuffer = chunkDecoder.decodeSymmetric(
                                secureChannel,
                                MessageType.SecureMessage,
                                buffersToDecode
                        );

                        binaryDecoder.setBuffer(messageBuffer);
                        UaRequestMessage request = binaryDecoder.decodeMessage(null);

                        ServiceRequest<UaRequestMessage, UaResponseMessage> serviceRequest = new ServiceRequest<>(
                                request,
                                chunkDecoder.getRequestId(),
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

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        chunkBuffers.forEach(ByteBuf::release);
        chunkBuffers.clear();

        ExceptionHandler.exceptionCaught(ctx, cause);
    }

}
