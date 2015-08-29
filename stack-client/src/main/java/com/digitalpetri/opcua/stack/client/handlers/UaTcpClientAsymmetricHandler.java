package com.digitalpetri.opcua.stack.client.handlers;

import java.nio.ByteOrder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.SerializationQueue;
import com.digitalpetri.opcua.stack.core.channel.headers.AsymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channel.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channel.messages.ErrorMessage;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.channel.messages.TcpMessageDecoder;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;
import com.digitalpetri.opcua.stack.core.types.structured.ChannelSecurityToken;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.OpenSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.OpenSecureChannelResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.digitalpetri.opcua.stack.core.util.NonceUtil;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpClientAsymmetricHandler extends SimpleChannelInboundHandler<ByteBuf> implements HeaderDecoder {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<ByteBuf> chunkBuffers = Lists.newArrayList();

    private ScheduledFuture renewFuture;

    private final AtomicReference<AsymmetricSecurityHeader> headerRef = new AtomicReference<>();

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

        sendOpenSecureChannelRequest(ctx, request);
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
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.readableBytes() >= HeaderLength &&
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
                    throw new UaException(StatusCodes.Bad_TcpMessageTypeInvalid,
                            "unexpected MessageType: " + messageType);
            }
        }
    }

    private void onOpenSecureChannel(ChannelHandlerContext ctx, ByteBuf buffer) throws UaException {
        buffer.skipBytes(3); // Skip messageType

        char chunkType = (char) buffer.readByte();

        if (chunkType == 'A') {
            chunkBuffers.forEach(ByteBuf::release);
            chunkBuffers.clear();
        } else {
            buffer.skipBytes(4); // Skip messageSize

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

            if (chunkType == 'F') {
                final List<ByteBuf> buffersToDecode = chunkBuffers;
                chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);

                serializationQueue.decode((binaryDecoder, chunkDecoder) -> {
                    ByteBuf messageBuffer = null;

                    try {
                        messageBuffer = chunkDecoder.decodeAsymmetric(
                                secureChannel,
                                MessageType.OpenSecureChannel,
                                buffersToDecode
                        );

                        OpenSecureChannelResponse response = binaryDecoder
                                .setBuffer(messageBuffer)
                                .decodeMessage(null);

                        secureChannel.setChannelId(response.getSecurityToken().getChannelId().longValue());
                        logger.debug("Received OpenSecureChannelResponse.");

                        installSecurityToken(ctx, response);
                    } catch (Throwable t) {
                        logger.error("Error decoding OpenSecureChannelResponse: {}", t.getMessage(), t);
                        ctx.close();
                    } finally {
                        if (messageBuffer != null) {
                            messageBuffer.release();
                        }
                        buffersToDecode.clear();
                    }
                });
            }
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
                ctx.pipeline().addFirst(new UaTcpClientSymmetricHandler(
                        client, serializationQueue, secureChannel, handshakeFuture));
            }
        });

        logger.debug("SecureChannel id={}, lifetime={}ms, createdAt={}",
                secureChannel.getChannelId(), revisedLifetime, createdAt);
    }

    private void sendOpenSecureChannelRequest(ChannelHandlerContext ctx, OpenSecureChannelRequest request) {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

            try {
                binaryEncoder.setBuffer(messageBuffer);
                binaryEncoder.encodeMessage(null, request);

                List<ByteBuf> chunks = chunkEncoder.encodeAsymmetricRequest(
                        secureChannel,
                        MessageType.OpenSecureChannel,
                        messageBuffer
                );

                ctx.executor().execute(() -> {
                    chunks.forEach(c -> ctx.write(c, ctx.voidPromise()));
                    ctx.flush();
                });

                logger.debug("Sent OpenSecureChannelRequest ({}, id={}).",
                        request.getRequestType(), secureChannel.getChannelId());
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

                List<ByteBuf> chunks = chunkEncoder.encodeSymmetricRequest(
                        secureChannel,
                        MessageType.CloseSecureChannel,
                        messageBuffer
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

            handshakeFuture.completeExceptionally(new UaException(errorCode, "error=" + errorMessage.getReason()));
        } catch (UaException e) {
            logger.error("An exception occurred while decoding an error message: {}", e.getMessage(), e);
        } finally {
            ctx.close();
        }
    }

}
