package com.digitalpetri.opcua.stack.core.serialization;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

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
import com.digitalpetri.opcua.stack.core.util.annotations.UByte;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt16;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt32;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt64;

public interface UaDecoder {

    Boolean decodeBoolean(String field);

    Byte decodeSByte(String field);

    Short decodeInt16(String field);

    Integer decodeInt32(String field);

    Long decodeInt64(String field);

    @UByte
    Short decodeByte(String field);

    @UInt16
    Integer decodeUInt16(String field);

    @UInt32
    Long decodeUInt32(String field);

    @UInt64
    Long decodeUInt64(String field);

    Float decodeFloat(String field);

    Double decodeDouble(String field);

    String decodeString(String field);

    DateTime decodeDateTime(String field);

    UUID decodeGuid(String field);

    ByteString decodeByteString(String field);

    XmlElement decodeXmlElement(String field);

    NodeId decodeNodeId(String field);

    ExpandedNodeId decodeExpandedNodeId(String field);

    StatusCode decodeStatusCode(String field);

    QualifiedName decodeQualifiedName(String field);

    LocalizedText decodeLocalizedText(String field);

    ExtensionObject decodeExtensionObject(String field);

    DataValue decodeDataValue(String field);

    Variant decodeVariant(String field);

    DiagnosticInfo decodeDiagnosticInfo(String field);

    <T extends UaStructure> T decodeMessage(String field);

    <T extends UaSerializable> T decodeSerializable(String field, Class<T> clazz);

    <T> T[] decodeArray(String field, Function<String, T> decoder, Class<T> clazz);

    <T> T[] decodeArray(String field, BiFunction<String, Class<T>, T> decoder, Class<T> clazz);

}
