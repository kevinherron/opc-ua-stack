package com.inductiveautomation.opcua.stack.core.serialization.xml;

import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaSerializationException;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaSerializable;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.XmlElement;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.ULong;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ulong;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public class XmlDecoder implements UaDecoder {

    private static final String XmlSchemaNamespace = "http://www.w3.org/2001/XMLSchema-instance";

    private final XMLInputFactory factory = XMLInputFactory.newFactory();

    private volatile XMLStreamReader streamReader;

    public XmlDecoder() {
    }

    public XmlDecoder(InputStream inputStream) throws XMLStreamException {
        setInputStream(inputStream);
    }

    public XmlDecoder setInputStream(InputStream inputStream) throws XMLStreamException {
        streamReader = factory.createXMLStreamReader(inputStream);

        return this;
    }

    @Override
    public Boolean decodeBoolean(String field) throws UaSerializationException {
        return parseElement(field, Boolean::valueOf);
    }

    @Override
    public Byte decodeSByte(String field) throws UaSerializationException {
        return parseElement(field, Byte::parseByte);
    }

    @Override
    public Short decodeInt16(String field) throws UaSerializationException {
        return parseElement(field, Short::parseShort);
    }

    @Override
    public Integer decodeInt32(String field) throws UaSerializationException {
        return parseElement(field, Integer::parseInt);
    }

    @Override
    public Long decodeInt64(String field) throws UaSerializationException {
        return parseElement(field, Long::parseLong);
    }

    @Override
    public UByte decodeByte(String field) throws UaSerializationException {
        return parseElement(field, s -> ubyte(Short.parseShort(s)));
    }

    @Override
    public UShort decodeUInt16(String field) throws UaSerializationException {
        return parseElement(field, s -> ushort(Integer.parseInt(s)));
    }

    @Override
    public UInteger decodeUInt32(String field) throws UaSerializationException {
        return parseElement(field, s -> uint(Long.parseLong(s)));
    }

    @Override
    public ULong decodeUInt64(String field) throws UaSerializationException {
        return parseElement(field, s -> ulong(Long.parseUnsignedLong(s)));
    }

    @Override
    public Float decodeFloat(String field) throws UaSerializationException {
        return parseElement(field, Float::parseFloat);
    }

    @Override
    public Double decodeDouble(String field) throws UaSerializationException {
        return parseElement(field, Double::parseDouble);
    }

    @Override
    public String decodeString(String field) throws UaSerializationException {
        return parseElement(field, content -> content);
    }

    @Override
    public DateTime decodeDateTime(String field) throws UaSerializationException {
        return parseElement(field, content -> {
            Calendar calendar = DatatypeConverter.parseDateTime(content);

            return new DateTime(calendar.getTime());
        });
    }

    @Override
    public UUID decodeGuid(String field) throws UaSerializationException {
        requireNextStartElement(field);

        UUID uuid;

        if (nextStartElement("String")) {
            try {
                String text = streamReader.getElementText();
                uuid = UUID.fromString(text);

                requireNextEndElement("String");
                requireNextEndElement(field);
            } catch (XMLStreamException e) {
                throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
            }
        } else {
            uuid = new UUID(0, 0);

            requireCurrentElement(field);
        }

        return uuid;
    }

    @Override
    public ByteString decodeByteString(String field) throws UaSerializationException {
        return parseNillableElement(field, content -> {
            if (content != null) {
                byte[] bs = DatatypeConverter.parseBase64Binary(content);

                return ByteString.of(bs);
            } else {
                return ByteString.NullValue;
            }
        });
    }

    @Override
    public XmlElement decodeXmlElement(String field) {
        return null;
    }

    @Override
    public NodeId decodeNodeId(String field) throws UaSerializationException {
        requireNextStartElement(field);

        NodeId nodeId;

        if (nextStartElement("Identifier")) {
            try {
                String text = streamReader.getElementText();
                nodeId = NodeId.parse(text);

                requireNextEndElement("Identifier");
                requireNextEndElement(field);
            } catch (XMLStreamException e) {
                throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
            }
        } else {
            nodeId = NodeId.NullValue;
        }

        return nodeId;
    }

    @Override
    public ExpandedNodeId decodeExpandedNodeId(String field) {
        return null;
    }

    @Override
    public StatusCode decodeStatusCode(String field) {
        return null;
    }

    @Override
    public QualifiedName decodeQualifiedName(String field) {
        return null;
    }

    @Override
    public LocalizedText decodeLocalizedText(String field) {
        return null;
    }

    @Override
    public ExtensionObject decodeExtensionObject(String field) {
        return null;
    }

    @Override
    public DataValue decodeDataValue(String field) {
        return null;
    }

    @Override
    public Variant decodeVariant(String field) {
        return null;
    }

    @Override
    public DiagnosticInfo decodeDiagnosticInfo(String field) {
        return null;
    }

    @Override
    public <T extends UaStructure> T decodeMessage(String field) {
        return null;
    }

    @Override
    public <T extends UaSerializable> T decodeSerializable(String field, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T[] decodeArray(String field, Function<String, T> decoder, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T[] decodeArray(String field, BiFunction<String, Class<T>, T> decoder, Class<T> clazz) {
        return null;
    }


    private <T> T parseElement(String element, Function<String, T> parser) throws UaSerializationException {
        requireNextStartElement(element);

        T parsed = parser.apply(readCharacterContent());

        requireNextEndElement(element);

        return parsed;
    }

    private <T> T parseNillableElement(String element, Function<String, T> parser) throws UaSerializationException {
        requireNextStartElement(element);

        T parsed;

        String nilValue = streamReader.getAttributeValue(XmlSchemaNamespace, "nil");

        if (nilValue != null && Boolean.parseBoolean(nilValue)) {
            parsed = parser.apply(null);
        } else {
            parsed = parser.apply(readCharacterContent());
        }

        requireNextEndElement(element);

        return parsed;
    }

    private boolean nextStartElement(String element) throws UaSerializationException {
        try {
            streamReader.nextTag();

            return streamReader.getEventType() == XMLStreamConstants.START_ELEMENT &&
                    ((element == null || element.isEmpty()) || element.equals(streamReader.getLocalName()));

        } catch (XMLStreamException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

    private void requireNextStartElement(String element) throws UaSerializationException {
        if (!nextStartElement(element)) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "start of element '" + element + "' not found");
        }
    }

    private boolean nextEndElement(String element) throws UaSerializationException {
        try {
            streamReader.nextTag();

            return streamReader.getEventType() == XMLStreamConstants.END_ELEMENT &&
                    ((element == null || element.isEmpty()) || element.equals(streamReader.getLocalName()));

        } catch (XMLStreamException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

    private void requireNextEndElement(String element) throws UaSerializationException {
        if (!nextEndElement(element)) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "end of element '" + element + "' not found");
        }
    }

    private boolean currentElement(String element) {
        return element.equals(streamReader.getLocalName());
    }

    private void requireCurrentElement(String element) throws UaSerializationException {
        if (!currentElement(element)) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                    "expected current element '" + element + "'");
        }
    }

    private String readCharacterContent() throws UaSerializationException {
        try {
            while (streamReader.hasNext()) {
                streamReader.next();

                if (streamReader.getEventType() == XMLStreamReader.CHARACTERS) {
                    return streamReader.getText();
                }
            }

            throw new UaSerializationException(StatusCodes.Bad_DecodingError, "no character content found");
        } catch (XMLStreamException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

}
