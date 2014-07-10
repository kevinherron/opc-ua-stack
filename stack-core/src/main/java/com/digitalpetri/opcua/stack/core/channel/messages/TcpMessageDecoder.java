package com.digitalpetri.opcua.stack.core.channel.messages;

import io.netty.buffer.ByteBuf;

public class TcpMessageDecoder {

    public static HelloMessage decodeHello(ByteBuf buffer) {
        MessageType messageType = MessageType.fromMediumInt(buffer.readMedium());
        char chunkType = (char) buffer.readByte();
        long length = buffer.readUnsignedInt();

        assert (messageType == MessageType.Hello && chunkType == 'F');

        return HelloMessage.decode(buffer);
    }

    public static AcknowledgeMessage decodeAcknowledge(ByteBuf buffer) {
        MessageType messageType = MessageType.fromMediumInt(buffer.readMedium());
        char chunkType = (char) buffer.readByte();
        long length = buffer.readUnsignedInt();

        assert (messageType == MessageType.Acknowledge && chunkType == 'F');

        return AcknowledgeMessage.decode(buffer);
    }

    public static ErrorMessage decodeError(ByteBuf buffer) {
        MessageType messageType = MessageType.fromMediumInt(buffer.readMedium());
        char chunkType = (char) buffer.readByte();
        long length = buffer.readUnsignedInt();

        assert (messageType == MessageType.Error && chunkType == 'F');

        return ErrorMessage.decode(buffer);
    }

}
