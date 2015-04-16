package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("BrowsePathTarget")
public class BrowsePathTarget implements UaStructure {

    public static final NodeId TypeId = Identifiers.BrowsePathTarget;
    public static final NodeId BinaryEncodingId = Identifiers.BrowsePathTarget_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowsePathTarget_Encoding_DefaultXml;

    protected final ExpandedNodeId _targetId;
    protected final UInteger _remainingPathIndex;

    public BrowsePathTarget() {
        this._targetId = null;
        this._remainingPathIndex = null;
    }

    public BrowsePathTarget(ExpandedNodeId _targetId, UInteger _remainingPathIndex) {
        this._targetId = _targetId;
        this._remainingPathIndex = _remainingPathIndex;
    }

    public ExpandedNodeId getTargetId() {
        return _targetId;
    }

    public UInteger getRemainingPathIndex() {
        return _remainingPathIndex;
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


    public static void encode(BrowsePathTarget browsePathTarget, UaEncoder encoder) {
        encoder.encodeExpandedNodeId("TargetId", browsePathTarget._targetId);
        encoder.encodeUInt32("RemainingPathIndex", browsePathTarget._remainingPathIndex);
    }

    public static BrowsePathTarget decode(UaDecoder decoder) {
        ExpandedNodeId _targetId = decoder.decodeExpandedNodeId("TargetId");
        UInteger _remainingPathIndex = decoder.decodeUInt32("RemainingPathIndex");

        return new BrowsePathTarget(_targetId, _remainingPathIndex);
    }

    static {
        DelegateRegistry.registerEncoder(BrowsePathTarget::encode, BrowsePathTarget.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowsePathTarget::decode, BrowsePathTarget.class, BinaryEncodingId, XmlEncodingId);
    }

}
