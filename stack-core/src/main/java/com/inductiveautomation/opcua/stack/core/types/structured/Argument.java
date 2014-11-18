package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("Argument")
public class Argument implements UaStructure {

    public static final NodeId TypeId = Identifiers.Argument;
    public static final NodeId BinaryEncodingId = Identifiers.Argument_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.Argument_Encoding_DefaultXml;

    protected final String _name;
    protected final NodeId _dataType;
    protected final Integer _valueRank;
    protected final UInteger[] _arrayDimensions;
    protected final LocalizedText _description;

    public Argument() {
        this._name = null;
        this._dataType = null;
        this._valueRank = null;
        this._arrayDimensions = null;
        this._description = null;
    }

    public Argument(String _name, NodeId _dataType, Integer _valueRank, UInteger[] _arrayDimensions, LocalizedText _description) {
        this._name = _name;
        this._dataType = _dataType;
        this._valueRank = _valueRank;
        this._arrayDimensions = _arrayDimensions;
        this._description = _description;
    }

    public String getName() {
        return _name;
    }

    public NodeId getDataType() {
        return _dataType;
    }

    public Integer getValueRank() {
        return _valueRank;
    }

    public UInteger[] getArrayDimensions() {
        return _arrayDimensions;
    }

    public LocalizedText getDescription() {
        return _description;
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


    public static void encode(Argument argument, UaEncoder encoder) {
        encoder.encodeString("Name", argument._name);
        encoder.encodeNodeId("DataType", argument._dataType);
        encoder.encodeInt32("ValueRank", argument._valueRank);
        encoder.encodeArray("ArrayDimensions", argument._arrayDimensions, encoder::encodeUInt32);
        encoder.encodeLocalizedText("Description", argument._description);
    }

    public static Argument decode(UaDecoder decoder) {
        String _name = decoder.decodeString("Name");
        NodeId _dataType = decoder.decodeNodeId("DataType");
        Integer _valueRank = decoder.decodeInt32("ValueRank");
        UInteger[] _arrayDimensions = decoder.decodeArray("ArrayDimensions", decoder::decodeUInt32, UInteger.class);
        LocalizedText _description = decoder.decodeLocalizedText("Description");

        return new Argument(_name, _dataType, _valueRank, _arrayDimensions, _description);
    }

    static {
        DelegateRegistry.registerEncoder(Argument::encode, Argument.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(Argument::decode, Argument.class, BinaryEncodingId, XmlEncodingId);
    }

}
