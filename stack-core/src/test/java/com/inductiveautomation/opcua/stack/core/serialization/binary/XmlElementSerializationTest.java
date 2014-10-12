package com.inductiveautomation.opcua.stack.core.serialization.binary;

import static org.testng.Assert.assertEquals;

import com.inductiveautomation.opcua.stack.core.types.builtin.XmlElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class XmlElementSerializationTest extends BinarySerializationFixture {

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
