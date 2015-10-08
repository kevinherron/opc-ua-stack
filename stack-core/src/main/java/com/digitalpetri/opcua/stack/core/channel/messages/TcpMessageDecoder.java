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
