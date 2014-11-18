package com.inductiveautomation.opcua.stack.core.serialization.xml;

import java.util.UUID;
import java.util.function.BiConsumer;

import com.inductiveautomation.opcua.stack.core.UaSerializationException;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;
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
import com.inductiveautomation.opcua.stack.core.util.annotations.UBytePrimitive;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt16Primitive;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32Primitive;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt64Primitive;

public class XmlEncoder implements UaEncoder {

    @Override
    public void encodeBoolean(String field, Boolean value) {

    }

    @Override
    public void encodeSByte(String field, Byte value) {

    }

    @Override
    public void encodeInt16(String field, Short value) {

    }

    @Override
    public void encodeInt32(String field, Integer value) {

    }

    @Override
    public void encodeInt64(String field, Long value) {

    }

    @Override
    public void encodeByte(String field, @UBytePrimitive Short value) {

    }

    @Override
    public void encodeUInt16(String field, @UInt16Primitive Integer value) {

    }

    @Override
    public void encodeUInt32(String field, @UInt32Primitive Long value) {

    }

    @Override
    public void encodeUInt64(String field, @UInt64Primitive Long value) {

    }

    @Override
    public void encodeByte(String field, UByte value) throws UaSerializationException {

    }

    @Override
    public void encodeUInt16(String field, UShort value) throws UaSerializationException {

    }

    @Override
    public void encodeUInt32(String field, UInteger value) throws UaSerializationException {

    }

    @Override
    public void encodeUInt64(String field, ULong value) throws UaSerializationException {

    }

    @Override
    public void encodeFloat(String field, Float value) {

    }

    @Override
    public void encodeDouble(String field, Double value) {

    }

    @Override
    public void encodeString(String field, String value) {

    }

    @Override
    public void encodeDateTime(String field, DateTime value) {

    }

    @Override
    public void encodeGuid(String field, UUID value) {

    }

    @Override
    public void encodeByteString(String field, ByteString value) {

    }

    @Override
    public void encodeXmlElement(String field, XmlElement value) {

    }

    @Override
    public void encodeNodeId(String field, NodeId value) {

    }

    @Override
    public void encodeExpandedNodeId(String field, ExpandedNodeId value) {

    }

    @Override
    public void encodeStatusCode(String field, StatusCode value) {

    }

    @Override
    public void encodeQualifiedName(String field, QualifiedName value) {

    }

    @Override
    public void encodeLocalizedText(String field, LocalizedText value) {

    }

    @Override
    public void encodeExtensionObject(String field, ExtensionObject value) {

    }

    @Override
    public void encodeDataValue(String field, DataValue value) {

    }

    @Override
    public void encodeVariant(String field, Variant value) {

    }

    @Override
    public void encodeDiagnosticInfo(String field, DiagnosticInfo value) {

    }

    @Override
    public <T extends UaStructure> void encodeMessage(String field, T message) {

    }

    @Override
    public <T extends UaEnumeration> void encodeEnumeration(String field, T value) throws UaSerializationException {

    }

    @Override
    public <T extends UaSerializable> void encodeSerializable(String field, T value) {

    }

    @Override
    public <T> void encodeArray(String field, T[] values, BiConsumer<String, T> encoder) {

    }

}
