package com.inductiveautomation.opcua.stack.core.serialization;

import java.util.UUID;
import java.util.function.BiConsumer;

import com.inductiveautomation.opcua.stack.core.UaSerializationException;
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
import com.inductiveautomation.opcua.stack.core.util.annotations.UByte;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt16;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt64;

public interface UaEncoder {

    void encodeBoolean(String field, Boolean value) throws UaSerializationException;

    void encodeSByte(String field, Byte value) throws UaSerializationException;

    void encodeInt16(String field, Short value) throws UaSerializationException;

    void encodeInt32(String field, Integer value) throws UaSerializationException;

    void encodeInt64(String field, Long value) throws UaSerializationException;

    void encodeByte(String field, @UByte Short value) throws UaSerializationException;

    void encodeUInt16(String field, @UInt16 Integer value) throws UaSerializationException;

    void encodeUInt32(String field, @UInt32 Long value) throws UaSerializationException;

    void encodeUInt64(String field, @UInt64 Long value) throws UaSerializationException;

    void encodeFloat(String field, Float value) throws UaSerializationException;

    void encodeDouble(String field, Double value) throws UaSerializationException;

    void encodeString(String field, String value) throws UaSerializationException;

    void encodeDateTime(String field, DateTime value) throws UaSerializationException;

    void encodeGuid(String field, UUID value) throws UaSerializationException;

    void encodeByteString(String field, ByteString value) throws UaSerializationException;

    void encodeXmlElement(String field, XmlElement value) throws UaSerializationException;

    void encodeNodeId(String field, NodeId value) throws UaSerializationException;

    void encodeExpandedNodeId(String field, ExpandedNodeId value) throws UaSerializationException;

    void encodeStatusCode(String field, StatusCode value) throws UaSerializationException;

    void encodeQualifiedName(String field, QualifiedName value) throws UaSerializationException;

    void encodeLocalizedText(String field, LocalizedText value) throws UaSerializationException;

    void encodeExtensionObject(String field, ExtensionObject value) throws UaSerializationException;

    void encodeDataValue(String field, DataValue value) throws UaSerializationException;

    void encodeVariant(String field, Variant value) throws UaSerializationException;

    void encodeDiagnosticInfo(String field, DiagnosticInfo value) throws UaSerializationException;

    <T extends UaStructure> void encodeMessage(String field, T message) throws UaSerializationException;

    <T extends UaSerializable> void encodeSerializable(String field, T value) throws UaSerializationException;

    <T> void encodeArray(String field, T[] values, BiConsumer<String, T> encoder) throws UaSerializationException;

}
