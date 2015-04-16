package com.digitalpetri.opcua.stack.core.serialization.binary;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class StringSerializationTest extends BinarySerializationFixture {

    @DataProvider(name = "StringProvider")
    public Object[][] getStrings() {
        return new Object[][]{
                {null},
                {""},
                {"Hello, world!"},
                {"水Boy"}
        };
    }

    @Test(dataProvider = "StringProvider")
    public void testStringRoundTrip(String value) {
        encoder.encodeString(null, value);
        String decoded = decoder.decodeString(null);

        assertEquals(decoded, value);
    }

}
