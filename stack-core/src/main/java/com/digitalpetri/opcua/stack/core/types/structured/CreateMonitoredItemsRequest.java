package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class CreateMonitoredItemsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.CreateMonitoredItemsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.CreateMonitoredItemsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CreateMonitoredItemsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long _subscriptionId;
    protected final TimestampsToReturn _timestampsToReturn;
    protected final MonitoredItemCreateRequest[] _itemsToCreate;

    public CreateMonitoredItemsRequest(RequestHeader _requestHeader, Long _subscriptionId, TimestampsToReturn _timestampsToReturn, MonitoredItemCreateRequest[] _itemsToCreate) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._timestampsToReturn = _timestampsToReturn;
        this._itemsToCreate = _itemsToCreate;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public TimestampsToReturn getTimestampsToReturn() { return _timestampsToReturn; }

    public MonitoredItemCreateRequest[] getItemsToCreate() { return _itemsToCreate; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CreateMonitoredItemsRequest createMonitoredItemsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", createMonitoredItemsRequest._requestHeader);
        encoder.encodeUInt32("SubscriptionId", createMonitoredItemsRequest._subscriptionId);
        encoder.encodeSerializable("TimestampsToReturn", createMonitoredItemsRequest._timestampsToReturn);
        encoder.encodeArray("ItemsToCreate", createMonitoredItemsRequest._itemsToCreate, encoder::encodeSerializable);
    }

    public static CreateMonitoredItemsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        TimestampsToReturn _timestampsToReturn = decoder.decodeSerializable("TimestampsToReturn", TimestampsToReturn.class);
        MonitoredItemCreateRequest[] _itemsToCreate = decoder.decodeArray("ItemsToCreate", decoder::decodeSerializable, MonitoredItemCreateRequest.class);

        return new CreateMonitoredItemsRequest(_requestHeader, _subscriptionId, _timestampsToReturn, _itemsToCreate);
    }

    static {
        DelegateRegistry.registerEncoder(CreateMonitoredItemsRequest::encode, CreateMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CreateMonitoredItemsRequest::decode, CreateMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
