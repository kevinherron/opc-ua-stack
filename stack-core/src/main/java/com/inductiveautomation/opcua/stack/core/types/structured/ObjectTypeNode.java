package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class ObjectTypeNode extends TypeNode {

    public static final NodeId TypeId = Identifiers.ObjectTypeNode;
    public static final NodeId BinaryEncodingId = Identifiers.ObjectTypeNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ObjectTypeNode_Encoding_DefaultXml;

    protected final Boolean _isAbstract;

    public ObjectTypeNode(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, ReferenceNode[] _references, Boolean _isAbstract) {
        super(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);
        this._isAbstract = _isAbstract;
    }

    public Boolean getIsAbstract() { return _isAbstract; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ObjectTypeNode objectTypeNode, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", objectTypeNode._nodeId);
        encoder.encodeSerializable("NodeClass", objectTypeNode._nodeClass);
        encoder.encodeQualifiedName("BrowseName", objectTypeNode._browseName);
        encoder.encodeLocalizedText("DisplayName", objectTypeNode._displayName);
        encoder.encodeLocalizedText("Description", objectTypeNode._description);
        encoder.encodeUInt32("WriteMask", objectTypeNode._writeMask);
        encoder.encodeUInt32("UserWriteMask", objectTypeNode._userWriteMask);
        encoder.encodeArray("References", objectTypeNode._references, encoder::encodeSerializable);
        encoder.encodeBoolean("IsAbstract", objectTypeNode._isAbstract);
    }

    public static ObjectTypeNode decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeSerializable("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);
        Boolean _isAbstract = decoder.decodeBoolean("IsAbstract");

        return new ObjectTypeNode(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references, _isAbstract);
    }

    static {
        DelegateRegistry.registerEncoder(ObjectTypeNode::encode, ObjectTypeNode.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ObjectTypeNode::decode, ObjectTypeNode.class, BinaryEncodingId, XmlEncodingId);
    }

}
