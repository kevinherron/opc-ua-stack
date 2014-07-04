package com.digitalpetri.opcua.stack.core.channels;

import java.nio.ByteOrder;
import java.util.List;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channels.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channels.headers.SymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channels.messages.MessageType;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
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
        if (secureChannel != null) secureChannel.bind(ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (secureChannel != null) secureChannel.unbind();

        super.channelInactive(ctx);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ServiceResponse message, ByteBuf out) throws Exception {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

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

            messageBuffer.release();
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
            if (securityHeader.getTokenId() != secureChannel.getCurrentTokenId()) {
                if (securityHeader.getTokenId() != secureChannel.getPreviousTokenId()) {
                    throw new UaException(StatusCodes.Bad_SecureChannelTokenUnknown,
                            "unknown secure channel token: " + securityHeader.getTokenId());
                }
            } else {
                secureChannel.setPreviousTokenId(-1L);
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
