package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class DataTypeNode extends TypeNode {

    public static final NodeId TypeId = Identifiers.DataTypeNode;
    public static final NodeId BinaryEncodingId = Identifiers.DataTypeNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DataTypeNode_Encoding_DefaultXml;

    protected final Boolean _isAbstract;

    public DataTypeNode(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, ReferenceNode[] _references, Boolean _isAbstract) {
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


    public static void encode(DataTypeNode dataTypeNode, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", dataTypeNode._nodeId);
        encoder.encodeSerializable("NodeClass", dataTypeNode._nodeClass);
        encoder.encodeQualifiedName("BrowseName", dataTypeNode._browseName);
        encoder.encodeLocalizedText("DisplayName", dataTypeNode._displayName);
        encoder.encodeLocalizedText("Description", dataTypeNode._description);
        encoder.encodeUInt32("WriteMask", dataTypeNode._writeMask);
        encoder.encodeUInt32("UserWriteMask", dataTypeNode._userWriteMask);
        encoder.encodeArray("References", dataTypeNode._references, encoder::encodeSerializable);
        encoder.encodeBoolean("IsAbstract", dataTypeNode._isAbstract);
    }

    public static DataTypeNode decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeSerializable("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);
        Boolean _isAbstract = decoder.decodeBoolean("IsAbstract");

        return new DataTypeNode(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references, _isAbstract);
    }

    static {
        DelegateRegistry.registerEncoder(DataTypeNode::encode, DataTypeNode.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DataTypeNode::decode, DataTypeNode.class, BinaryEncodingId, XmlEncodingId);
    }

}
