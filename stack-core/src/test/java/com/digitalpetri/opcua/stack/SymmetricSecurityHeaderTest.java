package com.digitalpetri.opcua.stack;

import com.digitalpetri.opcua.stack.core.channel.headers.SymmetricSecurityHeader;
import com.google.common.primitives.UnsignedInteger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SymmetricSecurityHeaderTest extends SerializationFixture2 {

    @DataProvider(name = "parameters")
    public Object[][] getParameters() {
        return new Object[][]{
                {0},
                {Integer.MAX_VALUE - 1},
                {Integer.MAX_VALUE},
                {Integer.MAX_VALUE + 1L},
                {UnsignedInteger.MAX_VALUE.longValue()}
        };
    }

    @Test(dataProvider = "parameters", description = "SymmetricSecurityHeader is serializable.")
    public void testSerialization(long value) {
        SymmetricSecurityHeader header = new SymmetricSecurityHeader(value);

        assertSerializable(header, SymmetricSecurityHeader::encode, SymmetricSecurityHeader::decode);
    }

}
