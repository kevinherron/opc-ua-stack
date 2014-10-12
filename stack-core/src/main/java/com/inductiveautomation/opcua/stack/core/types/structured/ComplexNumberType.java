package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class ComplexNumberType implements UaStructure {

    public static final NodeId TypeId = Identifiers.ComplexNumberType;
    public static final NodeId BinaryEncodingId = Identifiers.ComplexNumberType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ComplexNumberType_Encoding_DefaultXml;

    protected final Float _real;
    protected final Float _imaginary;

    public ComplexNumberType(Float _real, Float _imaginary) {
        this._real = _real;
        this._imaginary = _imaginary;
    }

    public Float getReal() { return _real; }

    public Float getImaginary() { return _imaginary; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ComplexNumberType complexNumberType, UaEncoder encoder) {
        encoder.encodeFloat("Real", complexNumberType._real);
        encoder.encodeFloat("Imaginary", complexNumberType._imaginary);
    }

    public static ComplexNumberType decode(UaDecoder decoder) {
        Float _real = decoder.decodeFloat("Real");
        Float _imaginary = decoder.decodeFloat("Imaginary");

        return new ComplexNumberType(_real, _imaginary);
    }

    static {
        DelegateRegistry.registerEncoder(ComplexNumberType::encode, ComplexNumberType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ComplexNumberType::decode, ComplexNumberType.class, BinaryEncodingId, XmlEncodingId);
    }

}
