package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;

public class VariableTypeAttributes extends NodeAttributes {

    public static final NodeId TypeId = Identifiers.VariableTypeAttributes;
    public static final NodeId BinaryEncodingId = Identifiers.VariableTypeAttributes_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.VariableTypeAttributes_Encoding_DefaultXml;

    protected final Variant _value;
    protected final NodeId _dataType;
    protected final Integer _valueRank;
    protected final Long[] _arrayDimensions;
    protected final Boolean _isAbstract;

    public VariableTypeAttributes(Long _specifiedAttributes, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, Variant _value, NodeId _dataType, Integer _valueRank, Long[] _arrayDimensions, Boolean _isAbstract) {
        super(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask);
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


    public static void encode(VariableTypeAttributes variableTypeAttributes, UaEncoder encoder) {
        encoder.encodeUInt32("SpecifiedAttributes", variableTypeAttributes._specifiedAttributes);
        encoder.encodeLocalizedText("DisplayName", variableTypeAttributes._displayName);
        encoder.encodeLocalizedText("Description", variableTypeAttributes._description);
        encoder.encodeUInt32("WriteMask", variableTypeAttributes._writeMask);
        encoder.encodeUInt32("UserWriteMask", variableTypeAttributes._userWriteMask);
        encoder.encodeVariant("Value", variableTypeAttributes._value);
        encoder.encodeNodeId("DataType", variableTypeAttributes._dataType);
        encoder.encodeInt32("ValueRank", variableTypeAttributes._valueRank);
        encoder.encodeArray("ArrayDimensions", variableTypeAttributes._arrayDimensions, encoder::encodeUInt32);
        encoder.encodeBoolean("IsAbstract", variableTypeAttributes._isAbstract);
    }

    public static VariableTypeAttributes decode(UaDecoder decoder) {
        Long _specifiedAttributes = decoder.decodeUInt32("SpecifiedAttributes");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        Variant _value = decoder.decodeVariant("Value");
        NodeId _dataType = decoder.decodeNodeId("DataType");
        Integer _valueRank = decoder.decodeInt32("ValueRank");
        Long[] _arrayDimensions = decoder.decodeArray("ArrayDimensions", decoder::decodeUInt32, Long.class);
        Boolean _isAbstract = decoder.decodeBoolean("IsAbstract");

        return new VariableTypeAttributes(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask, _value, _dataType, _valueRank, _arrayDimensions, _isAbstract);
    }

    static {
        DelegateRegistry.registerEncoder(VariableTypeAttributes::encode, VariableTypeAttributes.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(VariableTypeAttributes::decode, VariableTypeAttributes.class, BinaryEncodingId, XmlEncodingId);
    }

}
