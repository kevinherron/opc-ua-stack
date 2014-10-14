package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;

public class MonitoredItemCreateRequest implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoredItemCreateRequest;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoredItemCreateRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoredItemCreateRequest_Encoding_DefaultXml;

    protected final ReadValueId _itemToMonitor;
    protected final MonitoringMode _monitoringMode;
    protected final MonitoringParameters _requestedParameters;

    public MonitoredItemCreateRequest(ReadValueId _itemToMonitor, MonitoringMode _monitoringMode, MonitoringParameters _requestedParameters) {
        this._itemToMonitor = _itemToMonitor;
        this._monitoringMode = _monitoringMode;
        this._requestedParameters = _requestedParameters;
    }

    public ReadValueId getItemToMonitor() {
        return _itemToMonitor;
    }

    public MonitoringMode getMonitoringMode() {
        return _monitoringMode;
    }

    public MonitoringParameters getRequestedParameters() {
        return _requestedParameters;
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


    public static void encode(MonitoredItemCreateRequest monitoredItemCreateRequest, UaEncoder encoder) {
        encoder.encodeSerializable("ItemToMonitor", monitoredItemCreateRequest._itemToMonitor);
        encoder.encodeSerializable("MonitoringMode", monitoredItemCreateRequest._monitoringMode);
        encoder.encodeSerializable("RequestedParameters", monitoredItemCreateRequest._requestedParameters);
    }

    public static MonitoredItemCreateRequest decode(UaDecoder decoder) {
        ReadValueId _itemToMonitor = decoder.decodeSerializable("ItemToMonitor", ReadValueId.class);
        MonitoringMode _monitoringMode = decoder.decodeSerializable("MonitoringMode", MonitoringMode.class);
        MonitoringParameters _requestedParameters = decoder.decodeSerializable("RequestedParameters", MonitoringParameters.class);

        return new MonitoredItemCreateRequest(_itemToMonitor, _monitoringMode, _requestedParameters);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoredItemCreateRequest::encode, MonitoredItemCreateRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoredItemCreateRequest::decode, MonitoredItemCreateRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
