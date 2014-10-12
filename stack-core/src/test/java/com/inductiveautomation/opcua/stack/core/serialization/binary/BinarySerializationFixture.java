package com.inductiveautomation.opcua.stack.core.serialization.binary;

import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.testng.annotations.BeforeMethod;

public abstract class BinarySerializationFixture {

    ByteBuf buffer;
    BinaryEncoder encoder;
    BinaryDecoder decoder;

    @BeforeMethod
    public void setUp() {
        buffer = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);

        encoder = new BinaryEncoder().setBuffer(buffer);
        decoder = new BinaryDecoder().setBuffer(buffer);
    }

}
