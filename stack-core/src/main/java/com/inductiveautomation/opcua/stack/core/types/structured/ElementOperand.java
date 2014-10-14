package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public class ElementOperand extends FilterOperand {

    public static final NodeId TypeId = Identifiers.ElementOperand;
    public static final NodeId BinaryEncodingId = Identifiers.ElementOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ElementOperand_Encoding_DefaultXml;

    protected final UInteger _index;

    public ElementOperand(UInteger _index) {
        super();
        this._index = _index;
    }

    public UInteger getIndex() {
        return _index;
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


    public static void encode(ElementOperand elementOperand, UaEncoder encoder) {
        encoder.encodeUInt32("Index", elementOperand._index);
    }

    public static ElementOperand decode(UaDecoder decoder) {
        UInteger _index = decoder.decodeUInt32("Index");

        return new ElementOperand(_index);
    }

    static {
        DelegateRegistry.registerEncoder(ElementOperand::encode, ElementOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ElementOperand::decode, ElementOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
