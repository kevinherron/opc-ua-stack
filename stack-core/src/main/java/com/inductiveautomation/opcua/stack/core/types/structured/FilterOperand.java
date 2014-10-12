package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class FilterOperand implements UaStructure {

    public static final NodeId TypeId = Identifiers.FilterOperand;
    public static final NodeId BinaryEncodingId = Identifiers.FilterOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.FilterOperand_Encoding_DefaultXml;


    public FilterOperand() {
    }


    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(FilterOperand filterOperand, UaEncoder encoder) {
    }

    public static FilterOperand decode(UaDecoder decoder) {

        return new FilterOperand();
    }

    static {
        DelegateRegistry.registerEncoder(FilterOperand::encode, FilterOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(FilterOperand::decode, FilterOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
