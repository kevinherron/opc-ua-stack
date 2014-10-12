package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class MonitoringFilterResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoringFilterResult;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoringFilterResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoringFilterResult_Encoding_DefaultXml;


    public MonitoringFilterResult() {
    }


    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(MonitoringFilterResult monitoringFilterResult, UaEncoder encoder) {
    }

    public static MonitoringFilterResult decode(UaDecoder decoder) {

        return new MonitoringFilterResult();
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringFilterResult::encode, MonitoringFilterResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoringFilterResult::decode, MonitoringFilterResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
