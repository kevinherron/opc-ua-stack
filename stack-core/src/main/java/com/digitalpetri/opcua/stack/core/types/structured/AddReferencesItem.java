package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

@UaDataType("AddReferencesItem")
public class AddReferencesItem implements UaStructure {

    public static final NodeId TypeId = Identifiers.AddReferencesItem;
    public static final NodeId BinaryEncodingId = Identifiers.AddReferencesItem_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AddReferencesItem_Encoding_DefaultXml;

    protected final NodeId _sourceNodeId;
    protected final NodeId _referenceTypeId;
    protected final Boolean _isForward;
    protected final String _targetServerUri;
    protected final ExpandedNodeId _targetNodeId;
    protected final NodeClass _targetNodeClass;

    public AddReferencesItem() {
        this._sourceNodeId = null;
        this._referenceTypeId = null;
        this._isForward = null;
        this._targetServerUri = null;
        this._targetNodeId = null;
        this._targetNodeClass = null;
    }

    public AddReferencesItem(NodeId _sourceNodeId, NodeId _referenceTypeId, Boolean _isForward, String _targetServerUri, ExpandedNodeId _targetNodeId, NodeClass _targetNodeClass) {
        this._sourceNodeId = _sourceNodeId;
        this._referenceTypeId = _referenceTypeId;
        this._isForward = _isForward;
        this._targetServerUri = _targetServerUri;
        this._targetNodeId = _targetNodeId;
        this._targetNodeClass = _targetNodeClass;
    }

    public NodeId getSourceNodeId() {
        return _sourceNodeId;
    }

    public NodeId getReferenceTypeId() {
        return _referenceTypeId;
    }

    public Boolean getIsForward() {
        return _isForward;
    }

    public String getTargetServerUri() {
        return _targetServerUri;
    }

    public ExpandedNodeId getTargetNodeId() {
        return _targetNodeId;
    }

    public NodeClass getTargetNodeClass() {
        return _targetNodeClass;
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


    public static void encode(AddReferencesItem addReferencesItem, UaEncoder encoder) {
        encoder.encodeNodeId("SourceNodeId", addReferencesItem._sourceNodeId);
        encoder.encodeNodeId("ReferenceTypeId", addReferencesItem._referenceTypeId);
        encoder.encodeBoolean("IsForward", addReferencesItem._isForward);
        encoder.encodeString("TargetServerUri", addReferencesItem._targetServerUri);
        encoder.encodeExpandedNodeId("TargetNodeId", addReferencesItem._targetNodeId);
        encoder.encodeEnumeration("TargetNodeClass", addReferencesItem._targetNodeClass);
    }

    public static AddReferencesItem decode(UaDecoder decoder) {
        NodeId _sourceNodeId = decoder.decodeNodeId("SourceNodeId");
        NodeId _referenceTypeId = decoder.decodeNodeId("ReferenceTypeId");
        Boolean _isForward = decoder.decodeBoolean("IsForward");
        String _targetServerUri = decoder.decodeString("TargetServerUri");
        ExpandedNodeId _targetNodeId = decoder.decodeExpandedNodeId("TargetNodeId");
        NodeClass _targetNodeClass = decoder.decodeEnumeration("TargetNodeClass", NodeClass.class);

        return new AddReferencesItem(_sourceNodeId, _referenceTypeId, _isForward, _targetServerUri, _targetNodeId, _targetNodeClass);
    }

    static {
        DelegateRegistry.registerEncoder(AddReferencesItem::encode, AddReferencesItem.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AddReferencesItem::decode, AddReferencesItem.class, BinaryEncodingId, XmlEncodingId);
    }

}
