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

package com.digitalpetri.opcua.stack.core.serialization.binary;

import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceCounterDataType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class VariantSerializationTest extends BinarySerializationFixture {

    @DataProvider(name = "VariantProvider")
    public Object[][] getVariants() {
        return new Object[][]{
                {new Variant(null)},
                {new Variant("hello, world")},
                {new Variant(42)},
                {new Variant(new Integer[]{0, 1, 2, 3})},
                {new Variant(new Integer[][]{{0, 1}, {2, 3}})},
                {new Variant(new Long[]{0L, 1L, 2L, 3L})},
                {new Variant(new Long[][]{{0L, 1L}, {2L, 3L}})},
                {new Variant(new UInteger[]{Unsigned.uint(0), Unsigned.uint(1), Unsigned.uint(2), Unsigned.uint(3)})},
                {new Variant(new UInteger[][]{{Unsigned.uint(0), Unsigned.uint(1)}, {Unsigned.uint(2), Unsigned.uint(3)}})},
                {new Variant(new Variant[] {new Variant(0), new Variant(1), new Variant(2)})}
        };
    }

    @Test(dataProvider = "VariantProvider")
    public void testVariantRoundTrip(Variant variant) {
        encoder.encodeVariant(null, variant);
        Variant decoded = decoder.decodeVariant(null);

        assertEquals(decoded, variant);
    }

    @Test
    public void testVariant_UaStructure() {
        ServiceCounterDataType sc1 = new ServiceCounterDataType(Unsigned.uint(1), Unsigned.uint(2));

        Variant v = new Variant(sc1);
        encoder.encodeVariant(null, v);
        Variant decoded = decoder.decodeVariant(null);

        ExtensionObject extensionObject = (ExtensionObject) decoded.getValue();
        ServiceCounterDataType sc2 = extensionObject.decode();

        Assert.assertEquals(sc1.getTotalCount(), sc2.getTotalCount());
        Assert.assertEquals(sc1.getErrorCount(), sc2.getErrorCount());
    }

    @DataProvider(name = "PrimitiveArrayVariantProvider")
    public Object[][] getPrimitiveArrayVariants() {
        return new Object[][]{
                {new Variant(new int[]{0, 1, 2, 3}),
                        new Variant(new Integer[]{0, 1, 2, 3})},

                {new Variant(new int[][]{{0, 1}, {2, 3}}),
                        new Variant(new Integer[][]{{0, 1}, {2, 3}})},

                {new Variant(new long[]{0L, 1L, 2L, 3L}),
                        new Variant(new Long[]{0L, 1L, 2L, 3L})},

                {new Variant(new long[][]{{0L, 1L}, {2L, 3L}}),
                        new Variant(new Long[][]{{0L, 1L}, {2L, 3L}})}
        };
    }

    @Test(dataProvider = "PrimitiveArrayVariantProvider",
            description = "Test that after primitive array types given to variants come out as expected after encoding/decoding.")
    public void testPrimitiveArrayVariantRoundTrip(Variant variant, Variant expected) {
        encoder.encodeVariant(null, variant);
        Variant decoded = decoder.decodeVariant(null);

        assertEquals(decoded, expected);
    }

}
