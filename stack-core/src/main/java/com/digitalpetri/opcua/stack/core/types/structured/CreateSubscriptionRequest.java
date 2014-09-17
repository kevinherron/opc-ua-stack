package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class CreateSubscriptionRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.CreateSubscriptionRequest;
    public static final NodeId BinaryEncodingId = Identifiers.CreateSubscriptionRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CreateSubscriptionRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Double _requestedPublishingInterval;
    protected final Long _requestedLifetimeCount;
    protected final Long _requestedMaxKeepAliveCount;
    protected final Long _maxNotificationsPerPublish;
    protected final Boolean _publishingEnabled;
    protected final Short _priority;

    public CreateSubscriptionRequest(RequestHeader _requestHeader, Double _requestedPublishingInterval, Long _requestedLifetimeCount, Long _requestedMaxKeepAliveCount, Long _maxNotificationsPerPublish, Boolean _publishingEnabled, Short _priority) {
        this._requestHeader = _requestHeader;
        this._requestedPublishingInterval = _requestedPublishingInterval;
        this._requestedLifetimeCount = _requestedLifetimeCount;
        this._requestedMaxKeepAliveCount = _requestedMaxKeepAliveCount;
        this._maxNotificationsPerPublish = _maxNotificationsPerPublish;
        this._publishingEnabled = _publishingEnabled;
        this._priority = _priority;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Double getRequestedPublishingInterval() { return _requestedPublishingInterval; }

    public Long getRequestedLifetimeCount() { return _requestedLifetimeCount; }

    public Long getRequestedMaxKeepAliveCount() { return _requestedMaxKeepAliveCount; }

    public Long getMaxNotificationsPerPublish() { return _maxNotificationsPerPublish; }

    public Boolean getPublishingEnabled() { return _publishingEnabled; }

    public Short getPriority() { return _priority; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CreateSubscriptionRequest createSubscriptionRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", createSubscriptionRequest._requestHeader);
        encoder.encodeDouble("RequestedPublishingInterval", createSubscriptionRequest._requestedPublishingInterval);
        encoder.encodeUInt32("RequestedLifetimeCount", createSubscriptionRequest._requestedLifetimeCount);
        encoder.encodeUInt32("RequestedMaxKeepAliveCount", createSubscriptionRequest._requestedMaxKeepAliveCount);
        encoder.encodeUInt32("MaxNotificationsPerPublish", createSubscriptionRequest._maxNotificationsPerPublish);
        encoder.encodeBoolean("PublishingEnabled", createSubscriptionRequest._publishingEnabled);
        encoder.encodeByte("Priority", createSubscriptionRequest._priority);
    }

    public static CreateSubscriptionRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Double _requestedPublishingInterval = decoder.decodeDouble("RequestedPublishingInterval");
        Long _requestedLifetimeCount = decoder.decodeUInt32("RequestedLifetimeCount");
        Long _requestedMaxKeepAliveCount = decoder.decodeUInt32("RequestedMaxKeepAliveCount");
        Long _maxNotificationsPerPublish = decoder.decodeUInt32("MaxNotificationsPerPublish");
        Boolean _publishingEnabled = decoder.decodeBoolean("PublishingEnabled");
        Short _priority = decoder.decodeByte("Priority");

        return new CreateSubscriptionRequest(_requestHeader, _requestedPublishingInterval, _requestedLifetimeCount, _requestedMaxKeepAliveCount, _maxNotificationsPerPublish, _publishingEnabled, _priority);
    }

    static {
        DelegateRegistry.registerEncoder(CreateSubscriptionRequest::encode, CreateSubscriptionRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CreateSubscriptionRequest::decode, CreateSubscriptionRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
