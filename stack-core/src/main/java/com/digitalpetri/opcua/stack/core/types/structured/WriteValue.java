/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("WriteValue")
public class WriteValue implements UaStructure {

    public static final NodeId TypeId = Identifiers.WriteValue;
    public static final NodeId BinaryEncodingId = Identifiers.WriteValue_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.WriteValue_Encoding_DefaultXml;

    protected final NodeId _nodeId;
    protected final UInteger _attributeId;
    protected final String _indexRange;
    protected final DataValue _value;

    public WriteValue() {
        this._nodeId = null;
        this._attributeId = null;
        this._indexRange = null;
        this._value = null;
    }

    public WriteValue(NodeId _nodeId, UInteger _attributeId, String _indexRange, DataValue _value) {
        this._nodeId = _nodeId;
        this._attributeId = _attributeId;
        this._indexRange = _indexRange;
        this._value = _value;
    }

    public NodeId getNodeId() {
        return _nodeId;
    }

    public UInteger getAttributeId() {
        return _attributeId;
    }

    public String getIndexRange() {
        return _indexRange;
    }

    public DataValue getValue() {
        return _value;
    }

    @Override
    public NodeId getTypeId() {
        return TypeId;
    }

    @Override
    public NodeId getBinaryEncodingId() {
        return BinaryEncodingId;
    }

    @Override
    public NodeId getXmlEncodingId() {
        return XmlEncodingId;
    }


    public static void encode(WriteValue writeValue, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", writeValue._nodeId);
        encoder.encodeUInt32("AttributeId", writeValue._attributeId);
        encoder.encodeString("IndexRange", writeValue._indexRange);
        encoder.encodeDataValue("Value", writeValue._value);
    }

    public static WriteValue decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        UInteger _attributeId = decoder.decodeUInt32("AttributeId");
        String _indexRange = decoder.decodeString("IndexRange");
        DataValue _value = decoder.decodeDataValue("Value");

        return new WriteValue(_nodeId, _attributeId, _indexRange, _value);
    }

    static {
        DelegateRegistry.registerEncoder(WriteValue::encode, WriteValue.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(WriteValue::decode, WriteValue.class, BinaryEncodingId, XmlEncodingId);
    }

}
