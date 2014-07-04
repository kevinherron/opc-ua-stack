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
