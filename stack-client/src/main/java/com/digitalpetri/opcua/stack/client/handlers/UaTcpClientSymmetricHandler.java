package com.digitalpetri.opcua.stack.client.handlers;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.MessageAbortedException;
import com.digitalpetri.opcua.stack.core.channel.SerializationQueue;
import com.digitalpetri.opcua.stack.core.channel.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channel.headers.SymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channel.messages.ErrorMessage;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.channel.messages.TcpMessageDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UaTcpClientSymmetricHandler extends ByteToMessageCodec<UaRequestFuture> implements HeaderDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<Long, UaRequestFuture> pending = Maps.newConcurrentMap();

    private List<ByteBuf> chunkBuffers;

    private final int maxChunkCount;
    private final int maxChunkSize;

    private final UaTcpStackClient client;
    private final SerializationQueue serializationQueue;
    private final ClientSecureChannel secureChannel;
    private final CompletableFuture<ClientSecureChannel> handshakeFuture;

    public UaTcpClientSymmetricHandler(UaTcpStackClient client,
                                       SerializationQueue serializationQueue,
                                       ClientSecureChannel secureChannel,
                                       CompletableFuture<ClientSecureChannel> handshakeFuture) {
        this.client = client;
        this.serializationQueue = serializationQueue;
        this.secureChannel = secureChannel;
        this.handshakeFuture = handshakeFuture;

        maxChunkCount = serializationQueue.getParameters().getLocalMaxChunkCount();
        maxChunkSize = serializationQueue.getParameters().getLocalReceiveBufferSize();

        chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        List<UaMessage> awaitingHandshake =
                ctx.channel().attr(UaTcpClientAcknowledgeHandler.KEY_AWAITING_HANDSHAKE).get();

        if (awaitingHandshake != null) {
            logger.debug("{} message(s) queued before handshake completed; sending now.", awaitingHandshake.size());
            awaitingHandshake.forEach(m -> ctx.pipeline().write(m));
            ctx.flush();

            ctx.channel().attr(UaTcpClientAcknowledgeHandler.KEY_AWAITING_HANDSHAKE).remove();
        }

        client.getExecutorService().execute(() -> handshakeFuture.complete(secureChannel));
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, UaRequestFuture message, ByteBuf out) throws Exception {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

            try {
                binaryEncoder.setBuffer(messageBuffer);
                binaryEncoder.encodeMessage(null, message.getRequest());

                List<ByteBuf> chunks = chunkEncoder.encodeSymmetricRequest(
                        secureChannel,
                        MessageType.SecureMessage,
                        messageBuffer
                );

                long requestId = chunkEncoder.getLastRequestId();
                pending.put(requestId, message);

                // No matter how we complete, make sure the entry in pending is removed.
                // This covers the case where the request fails due to a timeout in the
                // upper layers as well as normal completion.
                message.getFuture().whenComplete(
                        (r, x) -> pending.remove(requestId));

                ctx.executor().execute(() -> {
                    chunks.forEach(c -> ctx.write(c, ctx.voidPromise()));
                    ctx.flush();
                });
            } catch (UaException e) {
                logger.error("Error encoding {}: {}", message.getClass(), e.getMessage(), e);
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
                    onSecureMessage(ctx, buffer.readSlice(messageLength));
                    break;

                case Error:
                    onError(ctx, buffer.readSlice(messageLength));
                    break;

                default:
                    out.add(buffer.readSlice(messageLength).retain());
            }
        }
    }

    private void onSecureMessage(ChannelHandlerContext ctx, ByteBuf buffer) throws UaException {
        buffer.skipBytes(3 + 1 + 4); // skip messageType, chunkType, messageSize

        long secureChannelId = buffer.readUnsignedInt();
        if (secureChannelId != secureChannel.getChannelId()) {
            throw new UaException(StatusCodes.Bad_SecureChannelIdInvalid,
                    "invalid secure channel id: " + secureChannelId);
        }

        SymmetricSecurityHeader securityHeader = SymmetricSecurityHeader.decode(buffer);

        ChannelSecurity channelSecurity = secureChannel.getChannelSecurity();
        long currentTokenId = channelSecurity.getCurrentToken().getTokenId().longValue();

        if (securityHeader.getTokenId() != currentTokenId) {
            long previousTokenId = channelSecurity.getPreviousToken()
                    .map(t -> t.getTokenId().longValue())
                    .orElse(-1L);

            if (securityHeader.getTokenId() != previousTokenId) {
                String message = String.format(
                        "received unknown secure channel token. " +
                                "tokenId=%s, previousTokenId=%s, currentTokenId=%s",
                        securityHeader.getTokenId(), previousTokenId, currentTokenId);

                throw new UaException(StatusCodes.Bad_SecureChannelTokenUnknown, message);
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
            chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);

            serializationQueue.decode((binaryDecoder, chunkDecoder) -> {
                ByteBuf decodedBuffer = null;
                try {
                    decodedBuffer = chunkDecoder.decodeSymmetric(secureChannel, buffersToDecode);

                    binaryDecoder.setBuffer(decodedBuffer);
                    UaResponseMessage response = binaryDecoder.decodeMessage(null);

                    UaRequestFuture request = pending.remove(chunkDecoder.getLastRequestId());

                    if (request != null) {
                        client.getExecutorService().execute(
                                () -> request.getFuture().complete(response));
                    } else {
                        logger.warn("No UaRequestFuture for requestId={}", chunkDecoder.getLastRequestId());
                    }
                } catch (MessageAbortedException e) {
                    logger.debug("Received message abort chunk; error={}, reason={}", e.getStatusCode(), e.getMessage());

                    UaRequestFuture request = pending.remove(chunkDecoder.getLastRequestId());

                    if (request != null) {
                        client.getExecutorService().execute(
                                () -> request.getFuture().completeExceptionally(e));
                    } else {
                        logger.warn("No UaRequestFuture for requestId={}", chunkDecoder.getLastRequestId());
                    }
                } catch (Throwable t) {
                    logger.error("Error decoding symmetric message: {}", t.getMessage(), t);
                    ctx.close();
                    serializationQueue.pause();
                } finally {
                    if (decodedBuffer != null) {
                        decodedBuffer.release();
                    }
                    buffersToDecode.clear();
                }
            });
        }
    }

    private void onError(ChannelHandlerContext ctx, ByteBuf buffer) {
        try {
            ErrorMessage error = TcpMessageDecoder.decodeError(buffer);

            logger.error("Received error message: " + error);
        } catch (UaException e) {
            logger.error("An exception occurred while decoding an error message: {}", e.getMessage(), e);
        } finally {
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        handshakeFuture.completeExceptionally(
                new UaException(StatusCodes.Bad_ConnectionClosed, "connection closed"));

        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Exception caught: {}", cause.getMessage(), cause);
        ctx.close();
    }

}
