package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class ObjectNode extends InstanceNode {

    public static final NodeId TypeId = Identifiers.ObjectNode;
    public static final NodeId BinaryEncodingId = Identifiers.ObjectNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ObjectNode_Encoding_DefaultXml;

    protected final Short _eventNotifier;

    public ObjectNode(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, ReferenceNode[] _references, Short _eventNotifier) {
        super(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);
        this._eventNotifier = _eventNotifier;
    }

    public Short getEventNotifier() { return _eventNotifier; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ObjectNode objectNode, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", objectNode._nodeId);
        encoder.encodeSerializable("NodeClass", objectNode._nodeClass);
        encoder.encodeQualifiedName("BrowseName", objectNode._browseName);
        encoder.encodeLocalizedText("DisplayName", objectNode._displayName);
        encoder.encodeLocalizedText("Description", objectNode._description);
        encoder.encodeUInt32("WriteMask", objectNode._writeMask);
        encoder.encodeUInt32("UserWriteMask", objectNode._userWriteMask);
        encoder.encodeArray("References", objectNode._references, encoder::encodeSerializable);
        encoder.encodeByte("EventNotifier", objectNode._eventNotifier);
    }

    public static ObjectNode decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeSerializable("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);
        Short _eventNotifier = decoder.decodeByte("EventNotifier");

        return new ObjectNode(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references, _eventNotifier);
    }

    static {
        DelegateRegistry.registerEncoder(ObjectNode::encode, ObjectNode.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ObjectNode::decode, ObjectNode.class, BinaryEncodingId, XmlEncodingId);
    }

}
