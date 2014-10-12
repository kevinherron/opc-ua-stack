package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class ModifyMonitoredItemsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.ModifyMonitoredItemsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.ModifyMonitoredItemsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModifyMonitoredItemsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long _subscriptionId;
    protected final TimestampsToReturn _timestampsToReturn;
    protected final MonitoredItemModifyRequest[] _itemsToModify;

    public ModifyMonitoredItemsRequest(RequestHeader _requestHeader, Long _subscriptionId, TimestampsToReturn _timestampsToReturn, MonitoredItemModifyRequest[] _itemsToModify) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._timestampsToReturn = _timestampsToReturn;
        this._itemsToModify = _itemsToModify;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public TimestampsToReturn getTimestampsToReturn() { return _timestampsToReturn; }

    public MonitoredItemModifyRequest[] getItemsToModify() { return _itemsToModify; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ModifyMonitoredItemsRequest modifyMonitoredItemsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", modifyMonitoredItemsRequest._requestHeader);
        encoder.encodeUInt32("SubscriptionId", modifyMonitoredItemsRequest._subscriptionId);
        encoder.encodeSerializable("TimestampsToReturn", modifyMonitoredItemsRequest._timestampsToReturn);
        encoder.encodeArray("ItemsToModify", modifyMonitoredItemsRequest._itemsToModify, encoder::encodeSerializable);
    }

    public static ModifyMonitoredItemsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        TimestampsToReturn _timestampsToReturn = decoder.decodeSerializable("TimestampsToReturn", TimestampsToReturn.class);
        MonitoredItemModifyRequest[] _itemsToModify = decoder.decodeArray("ItemsToModify", decoder::decodeSerializable, MonitoredItemModifyRequest.class);

        return new ModifyMonitoredItemsRequest(_requestHeader, _subscriptionId, _timestampsToReturn, _itemsToModify);
    }

    static {
        DelegateRegistry.registerEncoder(ModifyMonitoredItemsRequest::encode, ModifyMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModifyMonitoredItemsRequest::decode, ModifyMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
