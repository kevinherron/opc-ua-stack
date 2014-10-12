package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class ReferenceNode implements UaStructure {

    public static final NodeId TypeId = Identifiers.ReferenceNode;
    public static final NodeId BinaryEncodingId = Identifiers.ReferenceNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ReferenceNode_Encoding_DefaultXml;

    protected final NodeId _referenceTypeId;
    protected final Boolean _isInverse;
    protected final ExpandedNodeId _targetId;

    public ReferenceNode(NodeId _referenceTypeId, Boolean _isInverse, ExpandedNodeId _targetId) {
        this._referenceTypeId = _referenceTypeId;
        this._isInverse = _isInverse;
        this._targetId = _targetId;
    }

    public NodeId getReferenceTypeId() { return _referenceTypeId; }

    public Boolean getIsInverse() { return _isInverse; }

    public ExpandedNodeId getTargetId() { return _targetId; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ReferenceNode referenceNode, UaEncoder encoder) {
        encoder.encodeNodeId("ReferenceTypeId", referenceNode._referenceTypeId);
        encoder.encodeBoolean("IsInverse", referenceNode._isInverse);
        encoder.encodeExpandedNodeId("TargetId", referenceNode._targetId);
    }

    public static ReferenceNode decode(UaDecoder decoder) {
        NodeId _referenceTypeId = decoder.decodeNodeId("ReferenceTypeId");
        Boolean _isInverse = decoder.decodeBoolean("IsInverse");
        ExpandedNodeId _targetId = decoder.decodeExpandedNodeId("TargetId");

        return new ReferenceNode(_referenceTypeId, _isInverse, _targetId);
    }

    static {
        DelegateRegistry.registerEncoder(ReferenceNode::encode, ReferenceNode.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ReferenceNode::decode, ReferenceNode.class, BinaryEncodingId, XmlEncodingId);
    }

}
