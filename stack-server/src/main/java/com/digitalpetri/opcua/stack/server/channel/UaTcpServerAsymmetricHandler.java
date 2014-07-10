package com.digitalpetri.opcua.stack.server.channel;

import java.nio.ByteOrder;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecrets;
import com.digitalpetri.opcua.stack.core.channel.ExceptionHandler;
import com.digitalpetri.opcua.stack.core.channel.SerializationQueue;
import com.digitalpetri.opcua.stack.core.channel.headers.AsymmetricSecurityHeader;
import com.digitalpetri.opcua.stack.core.channel.headers.HeaderDecoder;
import com.digitalpetri.opcua.stack.core.channel.messages.MessageType;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;
import com.digitalpetri.opcua.stack.core.types.structured.ChannelSecurityToken;
import com.digitalpetri.opcua.stack.core.types.structured.OpenSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.OpenSecureChannelResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.util.BufferUtil;
import com.digitalpetri.opcua.stack.core.util.CertificateUtil;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServer;
import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.util.NonceUtil.generateNonce;
import static com.digitalpetri.opcua.stack.core.util.NonceUtil.getNonceLength;

public class UaTcpServerAsymmetricHandler extends ByteToMessageDecoder implements HeaderDecoder {

    private static final long SecureChannelLifetimeMillis = 60000L * 5L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ServerSecureChannel secureChannel;
    private volatile boolean symmetricHandlerAdded = false;

    private List<ByteBuf> chunkBuffers = Lists.newArrayList();

    private final AtomicReference<AsymmetricSecurityHeader> headerRef = new AtomicReference<>();

    private final int maxChunkCount;

    private final UaTcpServer server;
    private final SerializationQueue serializationQueue;

    public UaTcpServerAsymmetricHandler(UaTcpServer server, SerializationQueue serializationQueue) {
        this.server = server;
        this.serializationQueue = serializationQueue;

        maxChunkCount = serializationQueue.getParameters().getLocalMaxChunkCount();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.readableBytes() >= HeaderLength &&
                buffer.readableBytes() >= getMessageLength(buffer)) {

            int messageLength = getMessageLength(buffer);
            MessageType messageType = MessageType.fromMediumInt(buffer.getMedium(buffer.readerIndex()));

            switch (messageType) {
                case OpenSecureChannel:
                    onOpenSecureChannel(ctx, buffer.readSlice(messageLength));
                    break;

                case CloseSecureChannel:
                    logger.debug("Received CloseSecureChannelRequest");
                    if (secureChannel != null) {
                        server.closeSecureChannel(secureChannel);
                    }
                    buffer.skipBytes(messageLength);

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
            headerRef.set(null);
        } else {
            buffer.skipBytes(4); // Skip messageSize

            long secureChannelId = buffer.readUnsignedInt();

            if (secureChannelId == 0) {
                // Okay, this is the first OpenSecureChannelRequest... carry on.
                secureChannel = server.openSecureChannel();
            } else {
                secureChannel = server.getSecureChannel(secureChannelId);

                if (secureChannel == null) {
                    throw new UaException(StatusCodes.Bad_TcpSecureChannelUnknown,
                            "unknown secure channel id: " + secureChannelId);
                }
            }

            AsymmetricSecurityHeader securityHeader = AsymmetricSecurityHeader.decode(buffer);

            if (!headerRef.compareAndSet(null, securityHeader)) {
                if (!securityHeader.equals(headerRef.get())) {
                    throw new UaRuntimeException(StatusCodes.Bad_SecurityChecksFailed,
                            "subsequent AsymmetricSecurityHeader did not match");
                }
            }

            SecurityPolicy securityPolicy = SecurityPolicy.fromUri(securityHeader.getSecurityPolicyUri());
            secureChannel.setSecurityPolicy(securityPolicy);

            if (!securityHeader.getSenderCertificate().isNull()) {
                Certificate remoteCertificate = CertificateUtil.decode(securityHeader.getSenderCertificate().bytes());

                secureChannel.setRemoteCertificate(remoteCertificate);
            }

            if (!securityHeader.getReceiverThumbprint().isNull()) {
                Certificate localCertificate = server.getCertificate(securityHeader.getReceiverThumbprint());
                KeyPair keyPair = server.getKeyPair(securityHeader.getReceiverThumbprint());

                secureChannel.setLocalCertificate(localCertificate);
                secureChannel.setKeyPair(keyPair);
            }

            chunkBuffers.add(buffer.readerIndex(0).retain());

            if (chunkBuffers.size() > maxChunkCount) {
                throw new UaException(StatusCodes.Bad_TcpMessageTooLarge,
                        String.format("max chunk count exceeded (%s)", maxChunkCount));
            }

            if (chunkType == 'F') {
                final List<ByteBuf> buffersToDecode = chunkBuffers;

                chunkBuffers = Lists.newArrayListWithCapacity(maxChunkCount);
                headerRef.set(null);

                serializationQueue.decode((binaryDecoder, chunkDecoder) -> {
                    ByteBuf messageBuffer = chunkDecoder.decodeAsymmetric(
                            secureChannel,
                            MessageType.OpenSecureChannel,
                            buffersToDecode
                    );

                    binaryDecoder.setBuffer(messageBuffer);
                    OpenSecureChannelRequest request = binaryDecoder.decodeMessage(null);

                    logger.debug("Received OpenSecureChannelRequest ({}, id={}, token={}).",
                            request.getRequestType(), secureChannelId, secureChannel.getCurrentTokenId());

                    long requestId = chunkDecoder.getRequestId();

                    if (request.getRequestType() == SecurityTokenRequestType.Issue) {
                        issueSecurityToken(ctx, request, requestId);
                    } else {
                        renewSecurityToken(ctx, request, requestId);
                    }

                    // messageBuffer is a composite of chunkBuffers; releasing it releases them as well.
                    messageBuffer.release();
                    buffersToDecode.clear();
                });
            }
        }
    }

    private void issueSecurityToken(ChannelHandlerContext ctx, OpenSecureChannelRequest request, long requestId) {
        secureChannel.setMessageSecurityMode(request.getSecurityMode());

        if (secureChannel.isSymmetricSigningEnabled()) {
            SecurityAlgorithm algorithm = secureChannel.getSecurityPolicy().getSymmetricEncryptionAlgorithm();
            ByteString localNonce = generateNonce(getNonceLength(algorithm));

            secureChannel.setLocalNonce(localNonce);
            secureChannel.setRemoteNonce(request.getClientNonce());

            ChannelSecrets channelSecrets = ChannelSecrets.forChannel(
                    secureChannel,
                    secureChannel.getRemoteNonce(),
                    secureChannel.getLocalNonce()
            );

            secureChannel.setChannelSecrets(channelSecrets);
        }

        ChannelSecurityToken securityToken = new ChannelSecurityToken(
                secureChannel.getChannelId(),
                server.nextTokenId(),
                DateTime.now(),
                SecureChannelLifetimeMillis
        );

        secureChannel.setCurrentTokenId(securityToken.getTokenId());

        ResponseHeader responseHeader = new ResponseHeader(
                DateTime.now(),
                request.getRequestHeader().getRequestHandle(),
                StatusCode.Good,
                null, null, null
        );

        OpenSecureChannelResponse response = new OpenSecureChannelResponse(
                responseHeader,
                PROTOCOL_VERSION,
                securityToken,
                secureChannel.getLocalNonce()
        );

        sendOpenSecureChannelResponse(ctx, requestId, response);
    }

    private void renewSecurityToken(ChannelHandlerContext ctx, OpenSecureChannelRequest request, long requestId) {
        ChannelSecurityToken securityToken = new ChannelSecurityToken(
                secureChannel.getChannelId(),
                server.nextTokenId(),
                DateTime.now(),
                SecureChannelLifetimeMillis
        );

        secureChannel.setPreviousTokenId(secureChannel.getCurrentTokenId());
        secureChannel.setCurrentTokenId(securityToken.getTokenId());

        ResponseHeader responseHeader = new ResponseHeader(
                DateTime.now(),
                request.getRequestHeader().getRequestHandle(),
                StatusCode.Good,
                null, null, null
        );

        OpenSecureChannelResponse response = new OpenSecureChannelResponse(
                responseHeader,
                PROTOCOL_VERSION,
                securityToken,
                secureChannel.getLocalNonce()
        );

        sendOpenSecureChannelResponse(ctx, requestId, response);
    }

    private void sendOpenSecureChannelResponse(ChannelHandlerContext ctx, long requestId, OpenSecureChannelResponse response) {
        serializationQueue.encode((binaryEncoder, chunkEncoder) -> {
            ByteBuf messageBuffer = BufferUtil.buffer();

            binaryEncoder.setBuffer(messageBuffer);
            binaryEncoder.encodeMessage(null, response);

            List<ByteBuf> chunks = chunkEncoder.encodeAsymmetric(
                    secureChannel,
                    MessageType.OpenSecureChannel,
                    messageBuffer,
                    requestId
            );

            if (!symmetricHandlerAdded) {
                ctx.pipeline().addFirst(new UaTcpServerSymmetricHandler(server, serializationQueue, secureChannel));
                symmetricHandlerAdded = true;
            }

            chunks.forEach(c -> ctx.write(c, ctx.voidPromise()));
            ctx.flush();

            long lifetime = response.getSecurityToken().getRevisedLifetime();
            server.secureChannelIssuedOrRenewed(secureChannel, lifetime);

            messageBuffer.release();

            logger.debug("Sent OpenSecureChannelResponse.");
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        chunkBuffers.forEach(ByteBuf::release);
        chunkBuffers.clear();

        ExceptionHandler.exceptionCaught(ctx, cause);
    }

}
