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

package com.digitalpetri.opcua.stack.core.channel.headers;

import com.digitalpetri.opcua.stack.core.channel.messages.TcpMessageEncoder;
import com.google.common.primitives.Ints;
import io.netty.buffer.ByteBuf;

public interface HeaderDecoder {

    long PROTOCOL_VERSION = 0L;

    int HEADER_LENGTH = 8;
    int HEADER_LENGTH_INDEX = 4;

    /**
     * Get the message length from a {@link ByteBuf} containing a {@link TcpMessageEncoder}. The reader index will not be
     * advanced.
     *
     * @param buffer {@link ByteBuf} to extract from.
     * @return The message length, which includes the size of the header.
     */
    default int getMessageLength(ByteBuf buffer) {
        return Ints.checkedCast(buffer.getUnsignedInt(buffer.readerIndex() + HEADER_LENGTH_INDEX));
    }

}
