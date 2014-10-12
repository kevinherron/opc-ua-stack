package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class MonitoredItemModifyRequest implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoredItemModifyRequest;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoredItemModifyRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoredItemModifyRequest_Encoding_DefaultXml;

    protected final Long _monitoredItemId;
    protected final MonitoringParameters _requestedParameters;

    public MonitoredItemModifyRequest(Long _monitoredItemId, MonitoringParameters _requestedParameters) {
        this._monitoredItemId = _monitoredItemId;
        this._requestedParameters = _requestedParameters;
    }

    public Long getMonitoredItemId() { return _monitoredItemId; }

    public MonitoringParameters getRequestedParameters() { return _requestedParameters; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(MonitoredItemModifyRequest monitoredItemModifyRequest, UaEncoder encoder) {
        encoder.encodeUInt32("MonitoredItemId", monitoredItemModifyRequest._monitoredItemId);
        encoder.encodeSerializable("RequestedParameters", monitoredItemModifyRequest._requestedParameters);
    }

    public static MonitoredItemModifyRequest decode(UaDecoder decoder) {
        Long _monitoredItemId = decoder.decodeUInt32("MonitoredItemId");
        MonitoringParameters _requestedParameters = decoder.decodeSerializable("RequestedParameters", MonitoringParameters.class);

        return new MonitoredItemModifyRequest(_monitoredItemId, _requestedParameters);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoredItemModifyRequest::encode, MonitoredItemModifyRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoredItemModifyRequest::decode, MonitoredItemModifyRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
