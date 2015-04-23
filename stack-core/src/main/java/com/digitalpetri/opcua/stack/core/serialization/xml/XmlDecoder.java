package com.digitalpetri.opcua.stack.core.serialization.xml;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaSerializationException;
import com.digitalpetri.opcua.stack.core.serialization.DecoderDelegate;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.serialization.UaSerializable;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryDecoder;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.ULong;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned;
import com.digitalpetri.opcua.stack.core.util.Namespaces;
import com.google.common.collect.Lists;

import static io.netty.buffer.Unpooled.wrappedBuffer;

public class XmlDecoder implements UaDecoder {

    private final XMLInputFactory factory = XMLInputFactory.newFactory();

    private volatile XMLStreamReader streamReader;

    public XmlDecoder() {
    }

    public XmlDecoder(InputStream inputStream) throws XMLStreamException {
        setInput(inputStream);
    }

    public XmlDecoder(Reader reader) throws XMLStreamException {
        setInput(reader);
    }

    public XmlDecoder setInput(InputStream inputStream) throws XMLStreamException {
        streamReader = factory.createXMLStreamReader(inputStream);

        return this;
    }

    public XmlDecoder setInput(Reader reader) throws XMLStreamException {
        streamReader = factory.createXMLStreamReader(reader);

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
        return parseElement(field, s -> Unsigned.ubyte(Short.parseShort(s)));
    }

    @Override
    public UShort decodeUInt16(String field) throws UaSerializationException {
        return parseElement(field, s -> Unsigned.ushort(Integer.parseInt(s)));
    }

    @Override
    public UInteger decodeUInt32(String field) throws UaSerializationException {
        return parseElement(field, s -> Unsigned.uint(Long.parseLong(s)));
    }

    @Override
    public ULong decodeUInt64(String field) throws UaSerializationException {
        return parseElement(field, s -> Unsigned.ulong(Long.parseUnsignedLong(s)));
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

                // getElementText() advances to end of element

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
                return ByteString.NULL_VALUE;
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

                // getElementText() advances to end of element

                requireNextEndElement(field);
            } catch (XMLStreamException e) {
                throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
            }
        } else {
            nodeId = NodeId.NULL_VALUE;
        }

        return nodeId;
    }

    @Override
    public ExpandedNodeId decodeExpandedNodeId(String field) {
        return null;
    }

    @Override
    public StatusCode decodeStatusCode(String field) {
        if (nextStartElement(field)) {
            UInteger value = Unsigned.uint(0);

            if (nextStartElement("Code")) {
                value = decodeUInt32(null);
                requireNextEndElement("Code");
            }

            requireNextEndElement(field);

            return new StatusCode(value);
        } else {
            return new StatusCode(0);
        }
    }

    @Override
    public QualifiedName decodeQualifiedName(String field) {
        if (nextStartElement(field)) {
            UShort namespaceIndex = Unsigned.ushort(0);
            String name = "";

            if (nextStartElement("NamespaceIndex")) {
                namespaceIndex = decodeUInt16(null);
                requireNextEndElement("NamespaceIndex");
            }

            if (nextStartElement("Name")) {
                name = decodeString(null);
                requireNextEndElement("Name");
            }

            requireNextEndElement(field);

            return new QualifiedName(namespaceIndex, name);
        } else {
            return QualifiedName.NULL_VALUE;
        }
    }

    @Override
    public LocalizedText decodeLocalizedText(String field) {
        if (nextStartElement(field)) {
            String locale = LocalizedText.NULL_VALUE.getLocale();
            String text = LocalizedText.NULL_VALUE.getText();

            if (nextStartElement("Locale")) {
                locale = decodeString(null);
                requireNextEndElement("Locale");
            }

            if (nextStartElement("Text")) {
                text = decodeString(null);
                requireNextEndElement("Text");
            }

            requireNextEndElement(field);

            return new LocalizedText(locale, text);
        } else {
            return LocalizedText.NULL_VALUE;
        }
    }

    @Override
    public ExtensionObject decodeExtensionObject(String field) {
        if (nextStartElement(field)) {
            NodeId typeId = NodeId.NULL_VALUE;
            Object body = null;

            if (nextStartElement("TypeId")) {
                typeId = decodeNodeId(null);

                requireNextEndElement("TypeId");
            }

            if (nextStartElement("Body")) {
                try {
                    body = decodeExtensionObjectValue(typeId);
                } catch (XMLStreamException e) {
                    throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
                }

                requireNextEndElement("Body");
            }

            requireNextEndElement(field);

            if (body instanceof UaStructure) {
                return new ExtensionObject((UaStructure) body);
            } else if (body instanceof UaSerializable) {
                return new ExtensionObject((UaSerializable) body, typeId);
            } else if (body instanceof XmlElement) {
                return new ExtensionObject((XmlElement) body, typeId);
            } else if (body instanceof ByteString) {
                return new ExtensionObject((ByteString) body, typeId);
            } else {
                throw new UaSerializationException(StatusCodes.Bad_DecodingError,
                        "unrecognized ExtensionObject body: " + body);
            }
        } else {
            return new ExtensionObject(ByteString.NULL_VALUE, NodeId.NULL_VALUE);
        }
    }

    private Object decodeExtensionObjectValue(NodeId typeId) throws XMLStreamException {
        String bodyStartElement = getNextStartElement();

        if ("ByteString".equals(bodyStartElement)) {
            ByteString byteString = decodeByteString(null);

            requireNextEndElement("ByteString");

            try {
                DecoderDelegate<?> delegate = DelegateRegistry.getDecoder(typeId);

                byte[] bs = byteString.bytes();
                if (bs == null) bs = new byte[0];
                BinaryDecoder binaryDecoder = new BinaryDecoder();
                binaryDecoder.setBuffer(wrappedBuffer(bs));

                return delegate.decode(binaryDecoder);
            } catch (UaSerializationException e) {
                return byteString;
            }
        } else {
            StringBuilder builder = new StringBuilder();

            builder.append("<").append(bodyStartElement).append(">");

            while (streamReader.hasNext()) {
                streamReader.next();

                if (streamReader.hasText()) {
                    String text = streamReader.getText();

                    builder.append(text);
                } else if (streamReader.hasName()) {
                    String name = streamReader.getLocalName();

                    builder.append("<");
                    if (streamReader.isEndElement()) {
                        builder.append("/");
                    }
                    builder.append(name).append(">");

                    if (bodyStartElement.equals(name)) {
                        break;
                    }
                }
            }

            String bodyXml = builder.toString();

            try {
                DecoderDelegate<?> delegate = DelegateRegistry.getDecoder(typeId);

                XmlDecoder xmlDecoder = new XmlDecoder();
                xmlDecoder.setInput(new StringReader(bodyXml));
                xmlDecoder.requireNextStartElement(bodyStartElement);

                return delegate.decode(xmlDecoder);
            } catch (UaSerializationException e) {
                return new XmlElement(bodyXml);
            }
        }
    }

    @Override
    public DataValue decodeDataValue(String field) {
        if (nextStartElement(field)) {
            Variant value = Variant.NULL_VALUE;
            StatusCode statusCode = new StatusCode(0);
            DateTime sourceTimestamp = DateTime.MIN_VALUE;
            UShort sourcePicoseconds = Unsigned.ushort(0);
            DateTime serverTimestamp = DateTime.MIN_VALUE;
            UShort serverPicoseconds = Unsigned.ushort(0);

            if (nextStartElement("Value")) {
                value = decodeVariant(null);

                requireNextEndElement("Value");
            }

            if (nextStartElement("StatusCode")) {
                statusCode = decodeStatusCode(null);

                requireNextEndElement("StatusCode");
            }

            if (nextStartElement("SourceTimestamp")) {
                sourceTimestamp = decodeDateTime(null);

                requireNextEndElement("SourceTimestamp");
            }

            if (nextStartElement("SourcePicoseconds")) {
                sourcePicoseconds = decodeUInt16(null);

                requireNextEndElement("SourcePicoseconds");
            }

            if (nextStartElement("ServerTimestamp")) {
                serverTimestamp = decodeDateTime(null);

                requireNextEndElement("ServerTimestamp");
            }

            if (nextStartElement("ServerPicoseconds")) {
                serverPicoseconds = decodeUInt16(null);

                requireNextEndElement("ServerPicoseconds");
            }

            requireNextEndElement(field);

            return new DataValue(
                    value, statusCode,
                    sourceTimestamp, sourcePicoseconds,
                    serverTimestamp, serverPicoseconds);
        } else {
            return new DataValue(Variant.NULL_VALUE);
        }
    }

    @Override
    public Variant decodeVariant(String field) {
        if (nextStartElement(field)) {
            Object value = null;

            if (nextStartElement("Value")) {
                try {
                    value = decodeVariantValue();
                } catch (XMLStreamException e) {
                    throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
                }

                requireNextEndElement("Value");
            }

            requireNextEndElement(field);

            return new Variant(value);
        } else {
            return Variant.NULL_VALUE;
        }
    }

    public Object decodeVariantValue() throws XMLStreamException {
        String valueStartElement = getNextStartElement();

        if (valueStartElement.startsWith("ListOf")) {
            String valueType = valueStartElement.substring(6);
            List<Object> values = Lists.newArrayList();

            while (true) {
                if (nextStartElement(valueType)) {
                    values.add(decodeBuiltinType(valueType));

                    requireNextEndElement(valueType);
                } else {
                    break;
                }
            }

            if (values.size() > 0) {
                Class<?> c = values.get(0).getClass();
                Object a = Array.newInstance(c, values.size());
                for (int i = 0; i < values.size(); i++) {
                    Array.set(a, i, values.get(i));
                }
                return a;
            } else {
                return null;
            }
        } else {
            Object value = decodeBuiltinType(valueStartElement);

            requireNextEndElement(valueStartElement);

            return value;
        }
    }

    private Object decodeBuiltinType(String type) throws UaSerializationException {
        switch (type) {
            case "Boolean":
                return decodeBoolean(null);
            case "SByte":
                return decodeSByte(null);
            case "Byte":
                return decodeByte(null);
            case "Int16":
                return decodeInt16(null);
            case "UInt16":
                return decodeUInt16(null);
            case "Int32":
                return decodeInt32(null);
            case "UInt32":
                return decodeUInt32(null);
            case "Int64":
                return decodeInt64(null);
            case "UInt64":
                return decodeUInt64(null);
            case "Float":
                return decodeFloat(null);
            case "Double":
                return decodeDouble(null);
            case "String":
                return decodeString(null);
            case "DateTime":
                return decodeDateTime(null);
            case "Guid":
                return decodeGuid(null);
            case "ByteString":
                return decodeByteString(null);
            case "XmlElement":
                return decodeXmlElement(null);
            case "NodeId":
                return decodeNodeId(null);
            case "ExpandedNodeId":
                return decodeExpandedNodeId(null);
            case "StatusCode":
                return decodeStatusCode(null);
            case "QualifiedName":
                return decodeQualifiedName(null);
            case "LocalizedText":
                return decodeLocalizedText(null);
            case "ExtensionObject":
                return decodeExtensionObject(null);
            case "DataValue":
                return decodeDataValue(null);
            case "Variant":
                return decodeVariant(null);
            case "DiagnosticInfo":
                return decodeDiagnosticInfo(null);
            default:
                throw new UaSerializationException(StatusCodes.Bad_DecodingError, "unknown builtin type: " + type);
        }
    }

    @Override
    public DiagnosticInfo decodeDiagnosticInfo(String field) {
        int symbolicId = -1;
        int namespaceUri = -1;
        int localizedText = -1;
        int locale = -1;
        String additionalInfo = null;
        StatusCode innerStatusCode = null;
        DiagnosticInfo innerDiagnosticInfo = null;

        if (nextStartElement("SymbolicId")) {
            symbolicId = decodeInt32(null);

            requireNextEndElement("SymbolicId");
        }

        if (nextStartElement("NamespaceUri")) {
            namespaceUri = decodeInt32(null);

            requireNextEndElement("NamespaceUri");
        }

        if (nextStartElement("LocalizedText")) {
            localizedText = decodeInt32(null);

            requireNextEndElement("LocalizedText");
        }

        if (nextStartElement("Locale")) {
            locale = decodeInt32(null);

            requireNextEndElement("Locale");
        }

        if (nextStartElement("AdditionalInfo")) {
            additionalInfo = decodeString(null);

            requireNextEndElement("AdditionalInfo");
        }

        if (nextStartElement("InnerStatusCode")) {
            innerStatusCode = decodeStatusCode(null);

            requireNextEndElement("InnerStatusCode");
        }

        if (nextStartElement("InnerDiagnosticInfo")) {
            innerDiagnosticInfo = decodeDiagnosticInfo(null);

            requireNextEndElement("InnerDiagnosticInfo");
        }

        return new DiagnosticInfo(
                namespaceUri, symbolicId, locale, localizedText,
                additionalInfo, innerStatusCode, innerDiagnosticInfo);
    }

    @Override
    public <T extends UaStructure> T decodeMessage(String field) {
        return null;
    }

    @Override
    public <T extends UaEnumeration> T decodeEnumeration(String field, Class<T> clazz) throws UaSerializationException {
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

        String nilValue = streamReader.getAttributeValue(Namespaces.XML_SCHEMA_INSTANCE, "nil");

        if (nilValue != null && Boolean.parseBoolean(nilValue)) {
            parsed = parser.apply(null);
        } else {
            parsed = parser.apply(readCharacterContent());
        }

        requireNextEndElement(element);

        return parsed;
    }

    private String getNextStartElement() throws UaSerializationException {
        try {
            streamReader.nextTag();

            if (streamReader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                return streamReader.getLocalName();
            } else {
                throw new UaSerializationException(StatusCodes.Bad_DecodingError, "expected start element");
            }
        } catch (XMLStreamException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

    private boolean nextStartElement(String element) throws UaSerializationException {
        try {
            if (element == null || element.isEmpty()) return true;

            streamReader.nextTag();

            return streamReader.getEventType() == XMLStreamConstants.START_ELEMENT &&
                    element.equals(streamReader.getLocalName());
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
            if (element == null || element.isEmpty()) return true;

            streamReader.nextTag();

            return streamReader.getEventType() == XMLStreamConstants.END_ELEMENT &&
                    element.equals(streamReader.getLocalName());
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
