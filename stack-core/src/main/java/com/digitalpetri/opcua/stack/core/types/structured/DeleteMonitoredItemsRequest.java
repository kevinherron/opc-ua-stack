package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class DeleteMonitoredItemsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.DeleteMonitoredItemsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteMonitoredItemsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteMonitoredItemsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long _subscriptionId;
    protected final Long[] _monitoredItemIds;

    public DeleteMonitoredItemsRequest(RequestHeader _requestHeader, Long _subscriptionId, Long[] _monitoredItemIds) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._monitoredItemIds = _monitoredItemIds;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public Long[] getMonitoredItemIds() { return _monitoredItemIds; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(DeleteMonitoredItemsRequest deleteMonitoredItemsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", deleteMonitoredItemsRequest._requestHeader);
        encoder.encodeUInt32("SubscriptionId", deleteMonitoredItemsRequest._subscriptionId);
        encoder.encodeArray("MonitoredItemIds", deleteMonitoredItemsRequest._monitoredItemIds, encoder::encodeUInt32);
    }

    public static DeleteMonitoredItemsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Long[] _monitoredItemIds = decoder.decodeArray("MonitoredItemIds", decoder::decodeUInt32, Long.class);

        return new DeleteMonitoredItemsRequest(_requestHeader, _subscriptionId, _monitoredItemIds);
    }

    static {
        DelegateRegistry.registerEncoder(DeleteMonitoredItemsRequest::encode, DeleteMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DeleteMonitoredItemsRequest::decode, DeleteMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
