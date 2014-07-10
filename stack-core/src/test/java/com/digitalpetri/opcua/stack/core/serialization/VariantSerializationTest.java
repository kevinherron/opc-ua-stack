package com.digitalpetri.opcua.stack.core.serialization;

import com.digitalpetri.opcua.stack.core.types.builtin.OverloadedType;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
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
                {new Variant(new Long[]{0L, 1L, 2L, 3L}, OverloadedType.UInt32)},
                {new Variant(new Long[][]{{0L, 1L}, {2L, 3L}}, OverloadedType.UInt32)}
        };
    }

    @Test(dataProvider = "VariantProvider")
    public void testVariantRoundTrip(Variant variant) {
        encoder.encodeVariant(null, variant);
        Variant decoded = decoder.decodeVariant(null);

        assertEquals(decoded, variant);
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
                        new Variant(new Long[][]{{0L, 1L}, {2L, 3L}})},

                {new Variant(new long[]{0L, 1L, 2L, 3L}, OverloadedType.UInt32),
                        new Variant(new Long[]{0L, 1L, 2L, 3L}, OverloadedType.UInt32)},

                {new Variant(new long[][]{{0L, 1L}, {2L, 3L}}, OverloadedType.UInt32),
                        new Variant(new Long[][]{{0L, 1L}, {2L, 3L}}, OverloadedType.UInt32)}
        };
    }

    @Test(dataProvider = "PrimitiveArrayVariantProvider",
            description = "Test that after primitive array types given to variants come out as expected after encoding/decoding.")
    public void testPrimitiveArrayVariantRoundTrip(Variant variant, Variant expected) {
        encoder.encodeVariant(null, variant);
        Variant decoded = decoder.decodeVariant(null);

        assertEquals(decoded, expected);
    }

    @DataProvider(name = "MismatchedTypeProvider")
    public Object[][] getMismatchedTypes() {
        return new Object[][]{
                {new Variant((byte) 42, OverloadedType.UByte)},
                {new Variant((byte) 42, OverloadedType.UInt16)},
                {new Variant((byte) 42, OverloadedType.UInt32)},
                {new Variant((byte) 42, OverloadedType.UInt64)},

                {new Variant((short) 42, OverloadedType.UInt16)},
                {new Variant((short) 42, OverloadedType.UInt32)},
                {new Variant((short) 42, OverloadedType.UInt64)},

                {new Variant(42, OverloadedType.UByte)},
                {new Variant(42, OverloadedType.UInt32)},
                {new Variant(42, OverloadedType.UInt64)},

                {new Variant(42L, OverloadedType.UByte)},
                {new Variant(42L, OverloadedType.UInt16)},
        };
    }

    @Test(dataProvider = "MismatchedTypeProvider", expectedExceptions = ClassCastException.class)
    public void variantWithMismatchedOverloadedType(Variant variant) {
        encoder.encodeVariant(null, variant);
        Variant decoded = decoder.decodeVariant(null);

        assertEquals(variant, decoded);
    }

}
