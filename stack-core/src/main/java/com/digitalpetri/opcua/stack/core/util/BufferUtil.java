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

package com.digitalpetri.opcua.stack.core.util;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class BufferUtil {

    private static final ByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;

    public static ByteBuf buffer() {
        return allocator.buffer().order(ByteOrder.LITTLE_ENDIAN);
    }

    public static ByteBuf buffer(int initialCapacity) {
        return allocator.buffer(initialCapacity).order(ByteOrder.LITTLE_ENDIAN);
    }

    public static CompositeByteBuf compositeBuffer() {
        return allocator.compositeBuffer();
    }

}
