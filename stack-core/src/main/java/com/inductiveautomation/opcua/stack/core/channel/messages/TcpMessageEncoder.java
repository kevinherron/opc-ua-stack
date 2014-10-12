package com.inductiveautomation.opcua.stack.core.channel.messages;

import java.nio.ByteOrder;
import java.util.function.Consumer;

import com.inductiveautomation.opcua.stack.core.UaException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class TcpMessageEncoder {

    public static ByteBuf encode(HelloMessage helloMessage) throws UaException {
        return encode(
                MessageType.Hello,
                (b) -> HelloMessage.encode(helloMessage, b),
                Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN)
        );
    }

    public static ByteBuf encode(AcknowledgeMessage acknowledgeMessage) throws UaException {
        return encode(
                MessageType.Acknowledge,
                b -> AcknowledgeMessage.encode(acknowledgeMessage, b),
                Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN)
        );
    }

    public static ByteBuf encode(ErrorMessage errorMessage) throws UaException {
        return encode(
                MessageType.Error,
                (b) -> ErrorMessage.encode(errorMessage, b),
                Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN)
        );
    }

    /**
     * Encode a simple UA TCP message.
     *
     * @param messageType    {@link MessageType#Hello}, {@link MessageType#Acknowledge}, or {@link MessageType#Error}.
     * @param messageEncoder a function that encodes the message payload.
     * @param buffer         the {@link ByteBuf} to encode into.
     */
    private static ByteBuf encode(MessageType messageType, Consumer<ByteBuf> messageEncoder, ByteBuf buffer) throws UaException {
        buffer.writeMedium(MessageType.toMediumInt(messageType));
        buffer.writeByte('F');

        int lengthIndex = buffer.writerIndex();
        buffer.writeInt(0);

        int indexBefore = buffer.writerIndex();
        messageEncoder.accept(buffer);
        int indexAfter = buffer.writerIndex();
        int bytesWritten = indexAfter - indexBefore;

        buffer.writerIndex(lengthIndex);
        buffer.writeInt(8 + bytesWritten);
        buffer.writerIndex(indexAfter);

        return buffer;
    }

}
