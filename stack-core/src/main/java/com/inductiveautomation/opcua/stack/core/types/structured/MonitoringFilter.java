package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class MonitoringFilter implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoringFilter;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoringFilter_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoringFilter_Encoding_DefaultXml;


    public MonitoringFilter() {
    }


    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(MonitoringFilter monitoringFilter, UaEncoder encoder) {
    }

    public static MonitoringFilter decode(UaDecoder decoder) {

        return new MonitoringFilter();
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringFilter::encode, MonitoringFilter.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoringFilter::decode, MonitoringFilter.class, BinaryEncodingId, XmlEncodingId);
    }

}
