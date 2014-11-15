package com.inductiveautomation.opcua.stack.server.handlers;

import java.nio.ByteOrder;
import java.util.List;

import com.google.common.primitives.Ints;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.channel.ChannelParameters;
import com.inductiveautomation.opcua.stack.core.channel.ExceptionHandler;
import com.inductiveautomation.opcua.stack.core.channel.SerializationQueue;
import com.inductiveautomation.opcua.stack.core.channel.headers.HeaderDecoder;
import com.inductiveautomation.opcua.stack.core.channel.messages.AcknowledgeMessage;
import com.inductiveautomation.opcua.stack.core.channel.messages.HelloMessage;
import com.inductiveautomation.opcua.stack.core.channel.messages.MessageType;
import com.inductiveautomation.opcua.stack.core.channel.messages.TcpMessageDecoder;
import com.inductiveautomation.opcua.stack.core.channel.messages.TcpMessageEncoder;
import com.inductiveautomation.opcua.stack.server.tcp.SocketServer;
import com.inductiveautomation.opcua.stack.server.tcp.UaTcpServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UaTcpServerHelloHandler extends ByteToMessageDecoder implements HeaderDecoder {

    public static final AttributeKey<String> ENDPOINT_URL_KEY = AttributeKey.valueOf("endpoint-url");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SocketServer socketServer;

    public UaTcpServerHelloHandler(SocketServer socketServer) {
        this.socketServer = socketServer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        buffer = buffer.order(ByteOrder.LITTLE_ENDIAN);

        while (buffer.readableBytes() >= HeaderLength &&
                buffer.readableBytes() >= getMessageLength(buffer)) {

            int messageLength = getMessageLength(buffer);
            MessageType messageType = MessageType.fromMediumInt(buffer.getMedium(buffer.readerIndex()));

            switch (messageType) {
                case Hello:
                    onHello(ctx, buffer.readSlice(messageLength));
                    break;

                default:
                    throw new UaException(StatusCodes.Bad_TcpMessageTypeInvalid,
                            "unexpected MessageType: " + messageType);
            }
        }
    }

    private void onHello(ChannelHandlerContext ctx, ByteBuf buffer) throws UaException {
        logger.debug("[remote={}] Received Hello message.", ctx.channel().remoteAddress());

        HelloMessage hello = TcpMessageDecoder.decodeHello(buffer);

        UaTcpServer server = socketServer.getServer(hello.getEndpointUrl());

        if (server == null) {
            server = socketServer.getFallbackServer();
        }

        ctx.channel().attr(ENDPOINT_URL_KEY).set(hello.getEndpointUrl());

        long remoteProtocolVersion = hello.getProtocolVersion();
        long remoteReceiveBufferSize = hello.getReceiveBufferSize();
        long remoteSendBufferSize = hello.getSendBufferSize();
        long remoteMaxMessageSize = hello.getMaxMessageSize();
        long remoteMaxChunkCount = hello.getMaxChunkCount();

        if (remoteProtocolVersion < PROTOCOL_VERSION) {
            throw new UaException(StatusCodes.Bad_ProtocolVersionUnsupported,
                    "unsupported protocol version: " + remoteProtocolVersion);
        }

        ChannelConfig config = server.getChannelConfig();

        /* Our receive buffer size is determined by the remote send buffer size. */
        long localReceiveBufferSize = Math.min(remoteSendBufferSize, config.getMaxChunkSize());

        /* Our send buffer size is determined by the remote receive buffer size. */
        long localSendBufferSize = Math.min(remoteReceiveBufferSize, config.getMaxChunkSize());

        /* Max message size the remote can send us; not influenced by remote configuration. */
        long localMaxMessageSize = config.getMaxMessageSize();

        /* Max chunk count the remote can send us; not influenced by remote configuration. */
        long localMaxChunkCount = config.getMaxChunkCount();

        ChannelParameters parameters = new ChannelParameters(
                Ints.saturatedCast(localMaxMessageSize),
                Ints.saturatedCast(localReceiveBufferSize),
                Ints.saturatedCast(localSendBufferSize),
                Ints.saturatedCast(localMaxChunkCount),
                Ints.saturatedCast(remoteMaxMessageSize),
                Ints.saturatedCast(remoteReceiveBufferSize),
                Ints.saturatedCast(remoteSendBufferSize),
                Ints.saturatedCast(remoteMaxChunkCount)
        );

        int maxArrayLength = config.getMaxArrayLength();
        int maxStringLength = config.getMaxStringLength();

        SerializationQueue serializationQueue = new SerializationQueue(parameters, maxArrayLength, maxStringLength);
        ctx.pipeline().addLast(new UaTcpServerAsymmetricHandler(server, serializationQueue));
        ctx.pipeline().remove(this);

        logger.debug("[remote={}] Removed HelloHandler, added AsymmetricHandler.", ctx.channel().remoteAddress());

        AcknowledgeMessage acknowledge = new AcknowledgeMessage(
                PROTOCOL_VERSION,
                localReceiveBufferSize,
                localSendBufferSize,
                localMaxMessageSize,
                localMaxChunkCount
        );

        ByteBuf messageBuffer = TcpMessageEncoder.encode(acknowledge);

        ctx.executor().execute(() -> ctx.writeAndFlush(messageBuffer));

        logger.debug("[remote={}] Sent Acknowledge message.", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ExceptionHandler.exceptionCaught(ctx, cause);

        logger.error("[remote={}] Sent ErrorMessage.", ctx.channel().remoteAddress(), cause.getCause());
    }

}
