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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;

@UaDataType("ModelChangeStructureDataType")
public class ModelChangeStructureDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.ModelChangeStructureDataType;
    public static final NodeId BinaryEncodingId = Identifiers.ModelChangeStructureDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModelChangeStructureDataType_Encoding_DefaultXml;

    protected final NodeId _affected;
    protected final NodeId _affectedType;
    protected final UByte _verb;

    public ModelChangeStructureDataType() {
        this._affected = null;
        this._affectedType = null;
        this._verb = null;
    }

    public ModelChangeStructureDataType(NodeId _affected, NodeId _affectedType, UByte _verb) {
        this._affected = _affected;
        this._affectedType = _affectedType;
        this._verb = _verb;
    }

    public NodeId getAffected() { return _affected; }

    public NodeId getAffectedType() { return _affectedType; }

    public UByte getVerb() { return _verb; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ModelChangeStructureDataType modelChangeStructureDataType, UaEncoder encoder) {
        encoder.encodeNodeId("Affected", modelChangeStructureDataType._affected);
        encoder.encodeNodeId("AffectedType", modelChangeStructureDataType._affectedType);
        encoder.encodeByte("Verb", modelChangeStructureDataType._verb);
    }

    public static ModelChangeStructureDataType decode(UaDecoder decoder) {
        NodeId _affected = decoder.decodeNodeId("Affected");
        NodeId _affectedType = decoder.decodeNodeId("AffectedType");
        UByte _verb = decoder.decodeByte("Verb");

        return new ModelChangeStructureDataType(_affected, _affectedType, _verb);
    }

    static {
        DelegateRegistry.registerEncoder(ModelChangeStructureDataType::encode, ModelChangeStructureDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModelChangeStructureDataType::decode, ModelChangeStructureDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
