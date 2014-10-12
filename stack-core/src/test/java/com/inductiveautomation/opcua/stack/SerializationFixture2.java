package com.inductiveautomation.opcua.stack;

import java.nio.ByteOrder;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import static org.testng.Assert.assertEquals;

public class SerializationFixture2 {

    protected <T> void assertSerializable(T encoded,
                                          BiFunction<T, ByteBuf, ByteBuf> encoder,
                                          Function<ByteBuf, T> decoder) {

        ByteBuf buffer = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);

        T decoded = decoder.apply(encoder.apply(encoded, buffer));

        assertEquals(encoded, decoded);
    }

}
