package com.digitalpetri.opcua.stack.core.channel.messages;

import com.digitalpetri.opcua.stack.core.UaException;
import io.netty.buffer.ByteBuf;

public class TcpMessageDecoder {

    public static HelloMessage decodeHello(ByteBuf buffer) throws UaException {
        MessageType messageType = MessageType.fromMediumInt(buffer.readMedium());
        char chunkType = (char) buffer.readByte();
        buffer.skipBytes(4); // length

        assert (messageType == MessageType.Hello && chunkType == 'F');

        return HelloMessage.decode(buffer);
    }

    public static AcknowledgeMessage decodeAcknowledge(ByteBuf buffer) throws UaException {
        MessageType messageType = MessageType.fromMediumInt(buffer.readMedium());
        char chunkType = (char) buffer.readByte();
        buffer.skipBytes(4); // length

        assert (messageType == MessageType.Acknowledge && chunkType == 'F');

        return AcknowledgeMessage.decode(buffer);
    }

    public static ErrorMessage decodeError(ByteBuf buffer) throws UaException {
        MessageType messageType = MessageType.fromMediumInt(buffer.readMedium());
        char chunkType = (char) buffer.readByte();
        buffer.skipBytes(4); // length

        assert (messageType == MessageType.Error && chunkType == 'F');

        return ErrorMessage.decode(buffer);
    }

}
