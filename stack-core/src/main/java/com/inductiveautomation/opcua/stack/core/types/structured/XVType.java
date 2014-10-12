package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class XVType implements UaStructure {

    public static final NodeId TypeId = Identifiers.XVType;
    public static final NodeId BinaryEncodingId = Identifiers.XVType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.XVType_Encoding_DefaultXml;

    protected final Double _x;
    protected final Float _value;

    public XVType(Double _x, Float _value) {
        this._x = _x;
        this._value = _value;
    }

    public Double getX() { return _x; }

    public Float getValue() { return _value; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(XVType xVType, UaEncoder encoder) {
        encoder.encodeDouble("X", xVType._x);
        encoder.encodeFloat("Value", xVType._value);
    }

    public static XVType decode(UaDecoder decoder) {
        Double _x = decoder.decodeDouble("X");
        Float _value = decoder.decodeFloat("Value");

        return new XVType(_x, _value);
    }

    static {
        DelegateRegistry.registerEncoder(XVType::encode, XVType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(XVType::decode, XVType.class, BinaryEncodingId, XmlEncodingId);
    }

}
