package com.digitalpetri.opcua.stack.core.serialization;

import static org.testng.Assert.assertEquals;

import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XmlElementSerializationTest extends SerializationFixture {

    @DataProvider(name = "XmlElementProvider")
    public Object[][] getXmlElements() {
        return new Object[][]{
                {new XmlElement(null)},
                {new XmlElement("<tag>hello, world</tag>")},
        };
    }

    @Test(dataProvider = "XmlElementProvider", description = "XmlElement is round-trip serializable.")
    public void testXmlElementRoundTrip(XmlElement element) throws Exception {
        encoder.encodeXmlElement(null, element);
        XmlElement decoded = decoder.decodeXmlElement(null);

        assertEquals(decoded, element);
    }

}
