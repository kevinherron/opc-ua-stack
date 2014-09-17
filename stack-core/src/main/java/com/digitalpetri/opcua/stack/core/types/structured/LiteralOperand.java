package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

public class LiteralOperand extends FilterOperand {

    public static final NodeId TypeId = Identifiers.LiteralOperand;
    public static final NodeId BinaryEncodingId = Identifiers.LiteralOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.LiteralOperand_Encoding_DefaultXml;

    protected final Variant _value;

    public LiteralOperand(Variant _value) {
        super();
        this._value = _value;
    }

    public Variant getValue() { return _value; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(LiteralOperand literalOperand, UaEncoder encoder) {
        encoder.encodeVariant("Value", literalOperand._value);
    }

    public static LiteralOperand decode(UaDecoder decoder) {
        Variant _value = decoder.decodeVariant("Value");

        return new LiteralOperand(_value);
    }

    static {
        DelegateRegistry.registerEncoder(LiteralOperand::encode, LiteralOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(LiteralOperand::decode, LiteralOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
