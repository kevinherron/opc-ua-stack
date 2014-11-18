package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;

@UaDataType("SetMonitoringModeRequest")
public class SetMonitoringModeRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.SetMonitoringModeRequest;
    public static final NodeId BinaryEncodingId = Identifiers.SetMonitoringModeRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SetMonitoringModeRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _subscriptionId;
    protected final MonitoringMode _monitoringMode;
    protected final UInteger[] _monitoredItemIds;

    public SetMonitoringModeRequest() {
        this._requestHeader = null;
        this._subscriptionId = null;
        this._monitoringMode = null;
        this._monitoredItemIds = null;
    }

    public SetMonitoringModeRequest(RequestHeader _requestHeader, UInteger _subscriptionId, MonitoringMode _monitoringMode, UInteger[] _monitoredItemIds) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._monitoringMode = _monitoringMode;
        this._monitoredItemIds = _monitoredItemIds;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public UInteger getSubscriptionId() {
        return _subscriptionId;
    }

    public MonitoringMode getMonitoringMode() {
        return _monitoringMode;
    }

    public UInteger[] getMonitoredItemIds() {
        return _monitoredItemIds;
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


    public static void encode(SetMonitoringModeRequest setMonitoringModeRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", setMonitoringModeRequest._requestHeader != null ? setMonitoringModeRequest._requestHeader : new RequestHeader());
        encoder.encodeUInt32("SubscriptionId", setMonitoringModeRequest._subscriptionId);
        encoder.encodeEnumeration("MonitoringMode", setMonitoringModeRequest._monitoringMode);
        encoder.encodeArray("MonitoredItemIds", setMonitoringModeRequest._monitoredItemIds, encoder::encodeUInt32);
    }

    public static SetMonitoringModeRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        MonitoringMode _monitoringMode = decoder.decodeEnumeration("MonitoringMode", MonitoringMode.class);
        UInteger[] _monitoredItemIds = decoder.decodeArray("MonitoredItemIds", decoder::decodeUInt32, UInteger.class);

        return new SetMonitoringModeRequest(_requestHeader, _subscriptionId, _monitoringMode, _monitoredItemIds);
    }

    static {
        DelegateRegistry.registerEncoder(SetMonitoringModeRequest::encode, SetMonitoringModeRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SetMonitoringModeRequest::decode, SetMonitoringModeRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
