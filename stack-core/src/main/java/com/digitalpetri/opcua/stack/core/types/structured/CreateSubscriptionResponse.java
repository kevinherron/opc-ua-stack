package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("CreateSubscriptionResponse")
public class CreateSubscriptionResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.CreateSubscriptionResponse;
    public static final NodeId BinaryEncodingId = Identifiers.CreateSubscriptionResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CreateSubscriptionResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final UInteger _subscriptionId;
    protected final Double _revisedPublishingInterval;
    protected final UInteger _revisedLifetimeCount;
    protected final UInteger _revisedMaxKeepAliveCount;

    public CreateSubscriptionResponse() {
        this._responseHeader = null;
        this._subscriptionId = null;
        this._revisedPublishingInterval = null;
        this._revisedLifetimeCount = null;
        this._revisedMaxKeepAliveCount = null;
    }

    public CreateSubscriptionResponse(ResponseHeader _responseHeader, UInteger _subscriptionId, Double _revisedPublishingInterval, UInteger _revisedLifetimeCount, UInteger _revisedMaxKeepAliveCount) {
        this._responseHeader = _responseHeader;
        this._subscriptionId = _subscriptionId;
        this._revisedPublishingInterval = _revisedPublishingInterval;
        this._revisedLifetimeCount = _revisedLifetimeCount;
        this._revisedMaxKeepAliveCount = _revisedMaxKeepAliveCount;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public UInteger getSubscriptionId() {
        return _subscriptionId;
    }

    public Double getRevisedPublishingInterval() {
        return _revisedPublishingInterval;
    }

    public UInteger getRevisedLifetimeCount() {
        return _revisedLifetimeCount;
    }

    public UInteger getRevisedMaxKeepAliveCount() {
        return _revisedMaxKeepAliveCount;
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


    public static void encode(CreateSubscriptionResponse createSubscriptionResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", createSubscriptionResponse._responseHeader != null ? createSubscriptionResponse._responseHeader : new ResponseHeader());
        encoder.encodeUInt32("SubscriptionId", createSubscriptionResponse._subscriptionId);
        encoder.encodeDouble("RevisedPublishingInterval", createSubscriptionResponse._revisedPublishingInterval);
        encoder.encodeUInt32("RevisedLifetimeCount", createSubscriptionResponse._revisedLifetimeCount);
        encoder.encodeUInt32("RevisedMaxKeepAliveCount", createSubscriptionResponse._revisedMaxKeepAliveCount);
    }

    public static CreateSubscriptionResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Double _revisedPublishingInterval = decoder.decodeDouble("RevisedPublishingInterval");
        UInteger _revisedLifetimeCount = decoder.decodeUInt32("RevisedLifetimeCount");
        UInteger _revisedMaxKeepAliveCount = decoder.decodeUInt32("RevisedMaxKeepAliveCount");

        return new CreateSubscriptionResponse(_responseHeader, _subscriptionId, _revisedPublishingInterval, _revisedLifetimeCount, _revisedMaxKeepAliveCount);
    }

    static {
        DelegateRegistry.registerEncoder(CreateSubscriptionResponse::encode, CreateSubscriptionResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CreateSubscriptionResponse::decode, CreateSubscriptionResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
