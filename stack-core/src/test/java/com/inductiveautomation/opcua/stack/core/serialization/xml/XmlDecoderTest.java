package com.inductiveautomation.opcua.stack.core.serialization.xml;


import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class XmlDecoderTest {

    @Test
    public void testDecodeBoolean() throws XMLStreamException {
        XmlDecoder decoder = new XmlDecoder();

        decoder.setInputStream(new ByteArrayInputStream("<FieldName>true</FieldName>".getBytes()));
        assertTrue(decoder.decodeBoolean("FieldName"));

        decoder.setInputStream(new ByteArrayInputStream("<FieldName>false</FieldName>".getBytes()));
        assertFalse(decoder.decodeBoolean("FieldName"));

        decoder.setInputStream(new ByteArrayInputStream("<FieldName>true</FieldName>".getBytes()));
        assertTrue(decoder.decodeBoolean(null));

        decoder.setInputStream(new ByteArrayInputStream("<FieldName>false</FieldName>".getBytes()));
        assertFalse(decoder.decodeBoolean(null));
    }

}
