package com.digitalpetri.opcua.stack.core.serialization.xml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaSerializable;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
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

public class XmlDecoder implements UaDecoder {

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
    public Boolean decodeBoolean(String field) {

        return null;
    }

    @Override
    public Byte decodeSByte(String field) {
        return null;
    }

    @Override
    public Short decodeInt16(String field) {
        return null;
    }

    @Override
    public Integer decodeInt32(String field) {
        return null;
    }

    @Override
    public Integer[] decodeInt32Array(String field) {
        return new Integer[0];
    }

    @Override
    public Long decodeInt64(String field) {
        return null;
    }

    @Override
    public Short decodeByte(String field) {
        return null;
    }

    @Override
    public Integer decodeUInt16(String field) {
        return null;
    }

    @Override
    public Long decodeUInt32(String field) {
        return null;
    }

    @Override
    public Long decodeUInt64(String field) {
        return null;
    }

    @Override
    public Float decodeFloat(String field) {
        return null;
    }

    @Override
    public Double decodeDouble(String field) {
        return null;
    }

    @Override
    public String decodeString(String field) {
        return null;
    }

    @Override
    public DateTime decodeDateTime(String field) {
        return null;
    }

    @Override
    public UUID decodeGuid(String field) {
        return null;
    }

    @Override
    public ByteString decodeByteString(String field) {
        return null;
    }

    @Override
    public XmlElement decodeXmlElement(String field) {
        return null;
    }

    @Override
    public NodeId decodeNodeId(String field) {
        return null;
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

}
