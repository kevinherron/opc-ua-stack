package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class DoubleComplexNumberType implements UaStructure {

    public static final NodeId TypeId = Identifiers.DoubleComplexNumberType;
    public static final NodeId BinaryEncodingId = Identifiers.DoubleComplexNumberType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DoubleComplexNumberType_Encoding_DefaultXml;

    protected final Double _real;
    protected final Double _imaginary;

    public DoubleComplexNumberType(Double _real, Double _imaginary) {
        this._real = _real;
        this._imaginary = _imaginary;
    }

    public Double getReal() {
        return _real;
    }

    public Double getImaginary() {
        return _imaginary;
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


    public static void encode(DoubleComplexNumberType doubleComplexNumberType, UaEncoder encoder) {
        encoder.encodeDouble("Real", doubleComplexNumberType._real);
        encoder.encodeDouble("Imaginary", doubleComplexNumberType._imaginary);
    }

    public static DoubleComplexNumberType decode(UaDecoder decoder) {
        Double _real = decoder.decodeDouble("Real");
        Double _imaginary = decoder.decodeDouble("Imaginary");

        return new DoubleComplexNumberType(_real, _imaginary);
    }

    static {
        DelegateRegistry.registerEncoder(DoubleComplexNumberType::encode, DoubleComplexNumberType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DoubleComplexNumberType::decode, DoubleComplexNumberType.class, BinaryEncodingId, XmlEncodingId);
    }

}
