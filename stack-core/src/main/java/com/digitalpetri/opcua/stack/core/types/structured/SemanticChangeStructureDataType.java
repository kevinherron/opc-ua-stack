package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class SemanticChangeStructureDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.SemanticChangeStructureDataType;
    public static final NodeId BinaryEncodingId = Identifiers.SemanticChangeStructureDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SemanticChangeStructureDataType_Encoding_DefaultXml;

    protected final NodeId _affected;
    protected final NodeId _affectedType;

    public SemanticChangeStructureDataType(NodeId _affected, NodeId _affectedType) {
        this._affected = _affected;
        this._affectedType = _affectedType;
    }

    public NodeId getAffected() { return _affected; }

    public NodeId getAffectedType() { return _affectedType; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SemanticChangeStructureDataType semanticChangeStructureDataType, UaEncoder encoder) {
        encoder.encodeNodeId("Affected", semanticChangeStructureDataType._affected);
        encoder.encodeNodeId("AffectedType", semanticChangeStructureDataType._affectedType);
    }

    public static SemanticChangeStructureDataType decode(UaDecoder decoder) {
        NodeId _affected = decoder.decodeNodeId("Affected");
        NodeId _affectedType = decoder.decodeNodeId("AffectedType");

        return new SemanticChangeStructureDataType(_affected, _affectedType);
    }

    static {
        DelegateRegistry.registerEncoder(SemanticChangeStructureDataType::encode, SemanticChangeStructureDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SemanticChangeStructureDataType::decode, SemanticChangeStructureDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
