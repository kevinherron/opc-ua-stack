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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ReadValueId")
public class ReadValueId implements UaStructure {

    public static final NodeId TypeId = Identifiers.ReadValueId;
    public static final NodeId BinaryEncodingId = Identifiers.ReadValueId_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ReadValueId_Encoding_DefaultXml;

    protected final NodeId _nodeId;
    protected final UInteger _attributeId;
    protected final String _indexRange;
    protected final QualifiedName _dataEncoding;

    public ReadValueId() {
        this._nodeId = null;
        this._attributeId = null;
        this._indexRange = null;
        this._dataEncoding = null;
    }

    public ReadValueId(NodeId _nodeId, UInteger _attributeId, String _indexRange, QualifiedName _dataEncoding) {
        this._nodeId = _nodeId;
        this._attributeId = _attributeId;
        this._indexRange = _indexRange;
        this._dataEncoding = _dataEncoding;
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

    public QualifiedName getDataEncoding() {
        return _dataEncoding;
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


    public static void encode(ReadValueId readValueId, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", readValueId._nodeId);
        encoder.encodeUInt32("AttributeId", readValueId._attributeId);
        encoder.encodeString("IndexRange", readValueId._indexRange);
        encoder.encodeQualifiedName("DataEncoding", readValueId._dataEncoding);
    }

    public static ReadValueId decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        UInteger _attributeId = decoder.decodeUInt32("AttributeId");
        String _indexRange = decoder.decodeString("IndexRange");
        QualifiedName _dataEncoding = decoder.decodeQualifiedName("DataEncoding");

        return new ReadValueId(_nodeId, _attributeId, _indexRange, _dataEncoding);
    }

    static {
        DelegateRegistry.registerEncoder(ReadValueId::encode, ReadValueId.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ReadValueId::decode, ReadValueId.class, BinaryEncodingId, XmlEncodingId);
    }

}
