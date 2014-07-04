
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class ViewNode extends InstanceNode {

	public static final NodeId TypeId = Identifiers.ViewNode;
	public static final NodeId BinaryEncodingId = Identifiers.ViewNode_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.ViewNode_Encoding_DefaultXml;

	protected final Boolean _containsNoLoops;
	protected final Short _eventNotifier;

	public ViewNode(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, ReferenceNode[] _references, Boolean _containsNoLoops, Short _eventNotifier) {
        super(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);

		this._containsNoLoops = _containsNoLoops;
		this._eventNotifier = _eventNotifier;
	}

	public Boolean getContainsNoLoops() { return _containsNoLoops; }
	public Short getEventNotifier() { return _eventNotifier; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(ViewNode viewNode, UaEncoder encoder) {
		encoder.encodeNodeId("NodeId", viewNode._nodeId);
        encoder.encodeSerializable("NodeClass", viewNode._nodeClass);
		encoder.encodeQualifiedName("BrowseName", viewNode._browseName);
		encoder.encodeLocalizedText("DisplayName", viewNode._displayName);
		encoder.encodeLocalizedText("Description", viewNode._description);
		encoder.encodeUInt32("WriteMask", viewNode._writeMask);
		encoder.encodeUInt32("UserWriteMask", viewNode._userWriteMask);
        encoder.encodeArray("References", viewNode._references, encoder::encodeSerializable);
		encoder.encodeBoolean("ContainsNoLoops", viewNode._containsNoLoops);
		encoder.encodeByte("EventNotifier", viewNode._eventNotifier);
	}

	public static ViewNode decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeSerializable("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);
        Boolean _containsNoLoops = decoder.decodeBoolean("ContainsNoLoops");
        Short _eventNotifier = decoder.decodeByte("EventNotifier");

		return new ViewNode(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references, _containsNoLoops, _eventNotifier);
	}

	static {
		DelegateRegistry.registerEncoder(ViewNode::encode, ViewNode.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(ViewNode::decode, ViewNode.class, BinaryEncodingId, XmlEncodingId);
	}

}
