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
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("SimpleAttributeOperand")
public class SimpleAttributeOperand extends FilterOperand {

    public static final NodeId TypeId = Identifiers.SimpleAttributeOperand;
    public static final NodeId BinaryEncodingId = Identifiers.SimpleAttributeOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SimpleAttributeOperand_Encoding_DefaultXml;

    protected final NodeId _typeDefinitionId;
    protected final QualifiedName[] _browsePath;
    protected final UInteger _attributeId;
    protected final String _indexRange;

    public SimpleAttributeOperand() {
        super();
        this._typeDefinitionId = null;
        this._browsePath = null;
        this._attributeId = null;
        this._indexRange = null;
    }

    public SimpleAttributeOperand(NodeId _typeDefinitionId, QualifiedName[] _browsePath, UInteger _attributeId, String _indexRange) {
        super();
        this._typeDefinitionId = _typeDefinitionId;
        this._browsePath = _browsePath;
        this._attributeId = _attributeId;
        this._indexRange = _indexRange;
    }

    public NodeId getTypeDefinitionId() {
        return _typeDefinitionId;
    }

    public QualifiedName[] getBrowsePath() {
        return _browsePath;
    }

    public UInteger getAttributeId() {
        return _attributeId;
    }

    public String getIndexRange() {
        return _indexRange;
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


    public static void encode(SimpleAttributeOperand simpleAttributeOperand, UaEncoder encoder) {
        encoder.encodeNodeId("TypeDefinitionId", simpleAttributeOperand._typeDefinitionId);
        encoder.encodeArray("BrowsePath", simpleAttributeOperand._browsePath, encoder::encodeQualifiedName);
        encoder.encodeUInt32("AttributeId", simpleAttributeOperand._attributeId);
        encoder.encodeString("IndexRange", simpleAttributeOperand._indexRange);
    }

    public static SimpleAttributeOperand decode(UaDecoder decoder) {
        NodeId _typeDefinitionId = decoder.decodeNodeId("TypeDefinitionId");
        QualifiedName[] _browsePath = decoder.decodeArray("BrowsePath", decoder::decodeQualifiedName, QualifiedName.class);
        UInteger _attributeId = decoder.decodeUInt32("AttributeId");
        String _indexRange = decoder.decodeString("IndexRange");

        return new SimpleAttributeOperand(_typeDefinitionId, _browsePath, _attributeId, _indexRange);
    }

    static {
        DelegateRegistry.registerEncoder(SimpleAttributeOperand::encode, SimpleAttributeOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SimpleAttributeOperand::decode, SimpleAttributeOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
