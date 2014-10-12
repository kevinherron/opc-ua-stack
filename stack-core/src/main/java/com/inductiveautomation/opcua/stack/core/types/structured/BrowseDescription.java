package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.BrowseDirection;

public class BrowseDescription implements UaStructure {

    public static final NodeId TypeId = Identifiers.BrowseDescription;
    public static final NodeId BinaryEncodingId = Identifiers.BrowseDescription_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowseDescription_Encoding_DefaultXml;

    protected final NodeId _nodeId;
    protected final BrowseDirection _browseDirection;
    protected final NodeId _referenceTypeId;
    protected final Boolean _includeSubtypes;
    protected final Long _nodeClassMask;
    protected final Long _resultMask;

    public BrowseDescription(NodeId _nodeId, BrowseDirection _browseDirection, NodeId _referenceTypeId, Boolean _includeSubtypes, Long _nodeClassMask, Long _resultMask) {
        this._nodeId = _nodeId;
        this._browseDirection = _browseDirection;
        this._referenceTypeId = _referenceTypeId;
        this._includeSubtypes = _includeSubtypes;
        this._nodeClassMask = _nodeClassMask;
        this._resultMask = _resultMask;
    }

    public NodeId getNodeId() { return _nodeId; }

    public BrowseDirection getBrowseDirection() { return _browseDirection; }

    public NodeId getReferenceTypeId() { return _referenceTypeId; }

    public Boolean getIncludeSubtypes() { return _includeSubtypes; }

    public Long getNodeClassMask() { return _nodeClassMask; }

    public Long getResultMask() { return _resultMask; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(BrowseDescription browseDescription, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", browseDescription._nodeId);
        encoder.encodeSerializable("BrowseDirection", browseDescription._browseDirection);
        encoder.encodeNodeId("ReferenceTypeId", browseDescription._referenceTypeId);
        encoder.encodeBoolean("IncludeSubtypes", browseDescription._includeSubtypes);
        encoder.encodeUInt32("NodeClassMask", browseDescription._nodeClassMask);
        encoder.encodeUInt32("ResultMask", browseDescription._resultMask);
    }

    public static BrowseDescription decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        BrowseDirection _browseDirection = decoder.decodeSerializable("BrowseDirection", BrowseDirection.class);
        NodeId _referenceTypeId = decoder.decodeNodeId("ReferenceTypeId");
        Boolean _includeSubtypes = decoder.decodeBoolean("IncludeSubtypes");
        Long _nodeClassMask = decoder.decodeUInt32("NodeClassMask");
        Long _resultMask = decoder.decodeUInt32("ResultMask");

        return new BrowseDescription(_nodeId, _browseDirection, _referenceTypeId, _includeSubtypes, _nodeClassMask, _resultMask);
    }

    static {
        DelegateRegistry.registerEncoder(BrowseDescription::encode, BrowseDescription.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowseDescription::decode, BrowseDescription.class, BinaryEncodingId, XmlEncodingId);
    }

}
