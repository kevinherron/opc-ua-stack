package com.digitalpetri.opcua.stack.core.serialization;

import com.digitalpetri.opcua.stack.core.types.builtin.*;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExtensionObjectSerializationTest extends SerializationFixture {

    @DataProvider
    public Object[][] getExtensionObjects() {
        return new Object[][]{
                {new ExtensionObject(ByteString.of(new byte[]{1, 2, 3, 4}), new NodeId(1, 2))},
                {new ExtensionObject(XmlElement.of("<a>hello</a>"), new NodeId(1, 2))},
        };
    }

    @Test(dataProvider = "getExtensionObjects", description = "ExtensionObject is round-trip serializable.")
    public void testExtensionObjectRoundTrip(ExtensionObject xo) throws Exception {
        encoder.encodeExtensionObject(null, xo);
        ExtensionObject decoded = decoder.decodeExtensionObject(null);

        assertEquals(decoded, xo);
    }

}
