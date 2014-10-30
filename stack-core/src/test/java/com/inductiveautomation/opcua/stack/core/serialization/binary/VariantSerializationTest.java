package com.inductiveautomation.opcua.stack.core.serialization.binary;

import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.ServiceCounterDataType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
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
                {new Variant(new UInteger[]{uint(0), uint(1), uint(2), uint(3)})},
                {new Variant(new UInteger[][]{{uint(0), uint(1)}, {uint(2), uint(3)}})}
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
        ServiceCounterDataType sc1 = new ServiceCounterDataType(uint(1), uint(2));

        Variant v = new Variant(sc1);
        encoder.encodeVariant(null, v);
        Variant decoded = decoder.decodeVariant(null);

        ServiceCounterDataType sc2 = (ServiceCounterDataType) decoded.getValue();

        assertEquals(sc1.getTotalCount(), sc2.getTotalCount());
        assertEquals(sc1.getErrorCount(), sc2.getErrorCount());
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
