package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class HistoryReadDetails implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryReadDetails;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryReadDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryReadDetails_Encoding_DefaultXml;


    public HistoryReadDetails() {
    }


    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(HistoryReadDetails historyReadDetails, UaEncoder encoder) {
    }

    public static HistoryReadDetails decode(UaDecoder decoder) {

        return new HistoryReadDetails();
    }

    static {
        DelegateRegistry.registerEncoder(HistoryReadDetails::encode, HistoryReadDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryReadDetails::decode, HistoryReadDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
