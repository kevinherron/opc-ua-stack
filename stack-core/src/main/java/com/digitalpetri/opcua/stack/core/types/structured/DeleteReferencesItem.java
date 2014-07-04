
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class DeleteReferencesItem implements UaStructure {

	public static final NodeId TypeId = Identifiers.DeleteReferencesItem;
	public static final NodeId BinaryEncodingId = Identifiers.DeleteReferencesItem_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.DeleteReferencesItem_Encoding_DefaultXml;

	protected final NodeId _sourceNodeId;
	protected final NodeId _referenceTypeId;
	protected final Boolean _isForward;
	protected final ExpandedNodeId _targetNodeId;
	protected final Boolean _deleteBidirectional;

	public DeleteReferencesItem(NodeId _sourceNodeId, NodeId _referenceTypeId, Boolean _isForward, ExpandedNodeId _targetNodeId, Boolean _deleteBidirectional) {

		this._sourceNodeId = _sourceNodeId;
		this._referenceTypeId = _referenceTypeId;
		this._isForward = _isForward;
		this._targetNodeId = _targetNodeId;
		this._deleteBidirectional = _deleteBidirectional;
	}

	public NodeId getSourceNodeId() { return _sourceNodeId; }
	public NodeId getReferenceTypeId() { return _referenceTypeId; }
	public Boolean getIsForward() { return _isForward; }
	public ExpandedNodeId getTargetNodeId() { return _targetNodeId; }
	public Boolean getDeleteBidirectional() { return _deleteBidirectional; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(DeleteReferencesItem deleteReferencesItem, UaEncoder encoder) {
		encoder.encodeNodeId("SourceNodeId", deleteReferencesItem._sourceNodeId);
		encoder.encodeNodeId("ReferenceTypeId", deleteReferencesItem._referenceTypeId);
		encoder.encodeBoolean("IsForward", deleteReferencesItem._isForward);
		encoder.encodeExpandedNodeId("TargetNodeId", deleteReferencesItem._targetNodeId);
		encoder.encodeBoolean("DeleteBidirectional", deleteReferencesItem._deleteBidirectional);
	}

	public static DeleteReferencesItem decode(UaDecoder decoder) {
        NodeId _sourceNodeId = decoder.decodeNodeId("SourceNodeId");
        NodeId _referenceTypeId = decoder.decodeNodeId("ReferenceTypeId");
        Boolean _isForward = decoder.decodeBoolean("IsForward");
        ExpandedNodeId _targetNodeId = decoder.decodeExpandedNodeId("TargetNodeId");
        Boolean _deleteBidirectional = decoder.decodeBoolean("DeleteBidirectional");

		return new DeleteReferencesItem(_sourceNodeId, _referenceTypeId, _isForward, _targetNodeId, _deleteBidirectional);
	}

	static {
		DelegateRegistry.registerEncoder(DeleteReferencesItem::encode, DeleteReferencesItem.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(DeleteReferencesItem::decode, DeleteReferencesItem.class, BinaryEncodingId, XmlEncodingId);
	}

}
