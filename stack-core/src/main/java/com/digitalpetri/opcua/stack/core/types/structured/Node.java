
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class Node implements UaStructure {

	public static final NodeId TypeId = Identifiers.Node;
	public static final NodeId BinaryEncodingId = Identifiers.Node_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.Node_Encoding_DefaultXml;

	protected final NodeId _nodeId;
	protected final NodeClass _nodeClass;
	protected final QualifiedName _browseName;
	protected final LocalizedText _displayName;
	protected final LocalizedText _description;
	protected final Long _writeMask;
	protected final Long _userWriteMask;
	protected final ReferenceNode[] _references;

	public Node(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, ReferenceNode[] _references) {

		this._nodeId = _nodeId;
		this._nodeClass = _nodeClass;
		this._browseName = _browseName;
		this._displayName = _displayName;
		this._description = _description;
		this._writeMask = _writeMask;
		this._userWriteMask = _userWriteMask;
		this._references = _references;
	}

	public NodeId getNodeId() { return _nodeId; }
	public NodeClass getNodeClass() { return _nodeClass; }
	public QualifiedName getBrowseName() { return _browseName; }
	public LocalizedText getDisplayName() { return _displayName; }
	public LocalizedText getDescription() { return _description; }
	public Long getWriteMask() { return _writeMask; }
	public Long getUserWriteMask() { return _userWriteMask; }
	public ReferenceNode[] getReferences() { return _references; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(Node node, UaEncoder encoder) {
		encoder.encodeNodeId("NodeId", node._nodeId);
        encoder.encodeSerializable("NodeClass", node._nodeClass);
		encoder.encodeQualifiedName("BrowseName", node._browseName);
		encoder.encodeLocalizedText("DisplayName", node._displayName);
		encoder.encodeLocalizedText("Description", node._description);
		encoder.encodeUInt32("WriteMask", node._writeMask);
		encoder.encodeUInt32("UserWriteMask", node._userWriteMask);
        encoder.encodeArray("References", node._references, encoder::encodeSerializable);
	}

	public static Node decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeSerializable("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);

		return new Node(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);
	}

	static {
		DelegateRegistry.registerEncoder(Node::encode, Node.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(Node::decode, Node.class, BinaryEncodingId, XmlEncodingId);
	}

}
