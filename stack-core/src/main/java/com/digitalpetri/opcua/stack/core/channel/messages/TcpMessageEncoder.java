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

package com.digitalpetri.opcua.stack.core.channel.messages;

import java.nio.ByteOrder;
import java.util.function.Consumer;

import com.digitalpetri.opcua.stack.core.UaException;
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
