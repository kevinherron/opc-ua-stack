package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class VariableTypeNode extends TypeNode {

    public static final NodeId TypeId = Identifiers.VariableTypeNode;
    public static final NodeId BinaryEncodingId = Identifiers.VariableTypeNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.VariableTypeNode_Encoding_DefaultXml;

    protected final Variant _value;
    protected final NodeId _dataType;
    protected final Integer _valueRank;
    protected final Long[] _arrayDimensions;
    protected final Boolean _isAbstract;

    public VariableTypeNode(NodeId _nodeId, NodeClass _nodeClass, QualifiedName _browseName, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, ReferenceNode[] _references, Variant _value, NodeId _dataType, Integer _valueRank, Long[] _arrayDimensions, Boolean _isAbstract) {
        super(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references);
        this._value = _value;
        this._dataType = _dataType;
        this._valueRank = _valueRank;
        this._arrayDimensions = _arrayDimensions;
        this._isAbstract = _isAbstract;
    }

    public Variant getValue() { return _value; }

    public NodeId getDataType() { return _dataType; }

    public Integer getValueRank() { return _valueRank; }

    public Long[] getArrayDimensions() { return _arrayDimensions; }

    public Boolean getIsAbstract() { return _isAbstract; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(VariableTypeNode variableTypeNode, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", variableTypeNode._nodeId);
        encoder.encodeSerializable("NodeClass", variableTypeNode._nodeClass);
        encoder.encodeQualifiedName("BrowseName", variableTypeNode._browseName);
        encoder.encodeLocalizedText("DisplayName", variableTypeNode._displayName);
        encoder.encodeLocalizedText("Description", variableTypeNode._description);
        encoder.encodeUInt32("WriteMask", variableTypeNode._writeMask);
        encoder.encodeUInt32("UserWriteMask", variableTypeNode._userWriteMask);
        encoder.encodeArray("References", variableTypeNode._references, encoder::encodeSerializable);
        encoder.encodeVariant("Value", variableTypeNode._value);
        encoder.encodeNodeId("DataType", variableTypeNode._dataType);
        encoder.encodeInt32("ValueRank", variableTypeNode._valueRank);
        encoder.encodeArray("ArrayDimensions", variableTypeNode._arrayDimensions, encoder::encodeUInt32);
        encoder.encodeBoolean("IsAbstract", variableTypeNode._isAbstract);
    }

    public static VariableTypeNode decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        NodeClass _nodeClass = decoder.decodeSerializable("NodeClass", NodeClass.class);
        QualifiedName _browseName = decoder.decodeQualifiedName("BrowseName");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        ReferenceNode[] _references = decoder.decodeArray("References", decoder::decodeSerializable, ReferenceNode.class);
        Variant _value = decoder.decodeVariant("Value");
        NodeId _dataType = decoder.decodeNodeId("DataType");
        Integer _valueRank = decoder.decodeInt32("ValueRank");
        Long[] _arrayDimensions = decoder.decodeArray("ArrayDimensions", decoder::decodeUInt32, Long.class);
        Boolean _isAbstract = decoder.decodeBoolean("IsAbstract");

        return new VariableTypeNode(_nodeId, _nodeClass, _browseName, _displayName, _description, _writeMask, _userWriteMask, _references, _value, _dataType, _valueRank, _arrayDimensions, _isAbstract);
    }

    static {
        DelegateRegistry.registerEncoder(VariableTypeNode::encode, VariableTypeNode.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(VariableTypeNode::decode, VariableTypeNode.class, BinaryEncodingId, XmlEncodingId);
    }

}
