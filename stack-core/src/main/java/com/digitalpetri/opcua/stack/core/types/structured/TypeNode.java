package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("TypeNode")
public class TypeNode extends Node {

    public static final NodeId TypeId = Identifiers.TypeNode;
    public static final NodeId BinaryEncodingId = Identifiers.TypeNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TypeNode_Encoding_DefaultXml;


    public TypeNode() {
        super(null, null, null, null, null, null, null, null);
    }

    public TypeNode(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, UInteger _writeMask, UInteger _userWriteMask, ReferenceNode[] _references) {
        super(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);
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


    public static void encode(TypeNode typeNode, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", typeNode._nodeId);
        encoder.encodeEnumeration("NodeClass", typeNode._nodeClass);
        encoder.encodeQualifiedName("BrowseName", typeNode._browseName);
        encoder.encodeLocalizedText("DisplayName", typeNode._displayName);
        encoder.encodeLocalizedText("Description", typeNode._description);
        encoder.encodeUInt32("WriteMask", typeNode._writeMask);
        encoder.encodeUInt32("UserWriteMask", typeNode._userWriteMask);
        encoder.encodeArray("References", typeNode._references, encoder::encodeSerializable);
    }

    public static TypeNode decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeEnumeration("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        UInteger _writeMask = decoder.decodeUInt32("WriteMask");
        UInteger _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);

        return new TypeNode(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);
    }

    static {
        DelegateRegistry.registerEncoder(TypeNode::encode, TypeNode.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TypeNode::decode, TypeNode.class, BinaryEncodingId, XmlEncodingId);
    }

}
