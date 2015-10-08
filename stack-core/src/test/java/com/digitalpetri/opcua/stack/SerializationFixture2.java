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

package com.digitalpetri.opcua.stack;

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
