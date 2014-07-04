package com.digitalpetri.opcua.stack.core.serialization;

import java.util.UUID;
import java.util.function.BiConsumer;

import com.digitalpetri.opcua.stack.core.util.annotations.UByte;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt16;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt32;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt64;
import com.digitalpetri.opcua.stack.core.types.builtin.*;

public interface UaEncoder {

    void encodeBoolean(String field, Boolean value);

    void encodeSByte(String field, Byte value);

    void encodeInt16(String field, Short value);

    void encodeInt32(String field, Integer value);

    void encodeInt64(String field, Long value);

    void encodeByte(String field, @UByte Short value);

    void encodeUInt16(String field, @UInt16 Integer value);

    void encodeUInt32(String field, @UInt32 Long value);

    void encodeUInt64(String field, @UInt64 Long value);

    void encodeFloat(String field, Float value);

    void encodeDouble(String field, Double value);

    void encodeString(String field, String value);

    void encodeDateTime(String field, DateTime value);

    void encodeGuid(String field, UUID value);

    void encodeByteString(String field, ByteString value);

    void encodeXmlElement(String field, XmlElement value);

    void encodeNodeId(String field, NodeId value);

    void encodeExpandedNodeId(String field, ExpandedNodeId value);

    void encodeStatusCode(String field, StatusCode value);

    void encodeQualifiedName(String field, QualifiedName value);

    void encodeLocalizedText(String field, LocalizedText value);

    void encodeExtensionObject(String field, ExtensionObject value);

    void encodeDataValue(String field, DataValue value);

    void encodeVariant(String field, Variant value);

    void encodeDiagnosticInfo(String field, DiagnosticInfo value);

    <T extends UaStructure> void encodeMessage(String field, T message);

    <T extends UaSerializable> void encodeSerializable(String field, T value);

    <T> void encodeArray(String field, T[] values, BiConsumer<String, T> encoder);

}
