package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class ModifySubscriptionRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.ModifySubscriptionRequest;
    public static final NodeId BinaryEncodingId = Identifiers.ModifySubscriptionRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModifySubscriptionRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long _subscriptionId;
    protected final Double _requestedPublishingInterval;
    protected final Long _requestedLifetimeCount;
    protected final Long _requestedMaxKeepAliveCount;
    protected final Long _maxNotificationsPerPublish;
    protected final Short _priority;

    public ModifySubscriptionRequest(RequestHeader _requestHeader, Long _subscriptionId, Double _requestedPublishingInterval, Long _requestedLifetimeCount, Long _requestedMaxKeepAliveCount, Long _maxNotificationsPerPublish, Short _priority) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._requestedPublishingInterval = _requestedPublishingInterval;
        this._requestedLifetimeCount = _requestedLifetimeCount;
        this._requestedMaxKeepAliveCount = _requestedMaxKeepAliveCount;
        this._maxNotificationsPerPublish = _maxNotificationsPerPublish;
        this._priority = _priority;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public Double getRequestedPublishingInterval() { return _requestedPublishingInterval; }

    public Long getRequestedLifetimeCount() { return _requestedLifetimeCount; }

    public Long getRequestedMaxKeepAliveCount() { return _requestedMaxKeepAliveCount; }

    public Long getMaxNotificationsPerPublish() { return _maxNotificationsPerPublish; }

    public Short getPriority() { return _priority; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ModifySubscriptionRequest modifySubscriptionRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", modifySubscriptionRequest._requestHeader);
        encoder.encodeUInt32("SubscriptionId", modifySubscriptionRequest._subscriptionId);
        encoder.encodeDouble("RequestedPublishingInterval", modifySubscriptionRequest._requestedPublishingInterval);
        encoder.encodeUInt32("RequestedLifetimeCount", modifySubscriptionRequest._requestedLifetimeCount);
        encoder.encodeUInt32("RequestedMaxKeepAliveCount", modifySubscriptionRequest._requestedMaxKeepAliveCount);
        encoder.encodeUInt32("MaxNotificationsPerPublish", modifySubscriptionRequest._maxNotificationsPerPublish);
        encoder.encodeByte("Priority", modifySubscriptionRequest._priority);
    }

    public static ModifySubscriptionRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Double _requestedPublishingInterval = decoder.decodeDouble("RequestedPublishingInterval");
        Long _requestedLifetimeCount = decoder.decodeUInt32("RequestedLifetimeCount");
        Long _requestedMaxKeepAliveCount = decoder.decodeUInt32("RequestedMaxKeepAliveCount");
        Long _maxNotificationsPerPublish = decoder.decodeUInt32("MaxNotificationsPerPublish");
        Short _priority = decoder.decodeByte("Priority");

        return new ModifySubscriptionRequest(_requestHeader, _subscriptionId, _requestedPublishingInterval, _requestedLifetimeCount, _requestedMaxKeepAliveCount, _maxNotificationsPerPublish, _priority);
    }

    static {
        DelegateRegistry.registerEncoder(ModifySubscriptionRequest::encode, ModifySubscriptionRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModifySubscriptionRequest::decode, ModifySubscriptionRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
