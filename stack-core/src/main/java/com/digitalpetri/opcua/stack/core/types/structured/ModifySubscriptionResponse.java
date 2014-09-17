package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class ModifySubscriptionResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.ModifySubscriptionResponse;
    public static final NodeId BinaryEncodingId = Identifiers.ModifySubscriptionResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModifySubscriptionResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final Double _revisedPublishingInterval;
    protected final Long _revisedLifetimeCount;
    protected final Long _revisedMaxKeepAliveCount;

    public ModifySubscriptionResponse(ResponseHeader _responseHeader, Double _revisedPublishingInterval, Long _revisedLifetimeCount, Long _revisedMaxKeepAliveCount) {
        this._responseHeader = _responseHeader;
        this._revisedPublishingInterval = _revisedPublishingInterval;
        this._revisedLifetimeCount = _revisedLifetimeCount;
        this._revisedMaxKeepAliveCount = _revisedMaxKeepAliveCount;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public Double getRevisedPublishingInterval() { return _revisedPublishingInterval; }

    public Long getRevisedLifetimeCount() { return _revisedLifetimeCount; }

    public Long getRevisedMaxKeepAliveCount() { return _revisedMaxKeepAliveCount; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ModifySubscriptionResponse modifySubscriptionResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", modifySubscriptionResponse._responseHeader);
        encoder.encodeDouble("RevisedPublishingInterval", modifySubscriptionResponse._revisedPublishingInterval);
        encoder.encodeUInt32("RevisedLifetimeCount", modifySubscriptionResponse._revisedLifetimeCount);
        encoder.encodeUInt32("RevisedMaxKeepAliveCount", modifySubscriptionResponse._revisedMaxKeepAliveCount);
    }

    public static ModifySubscriptionResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        Double _revisedPublishingInterval = decoder.decodeDouble("RevisedPublishingInterval");
        Long _revisedLifetimeCount = decoder.decodeUInt32("RevisedLifetimeCount");
        Long _revisedMaxKeepAliveCount = decoder.decodeUInt32("RevisedMaxKeepAliveCount");

        return new ModifySubscriptionResponse(_responseHeader, _revisedPublishingInterval, _revisedLifetimeCount, _revisedMaxKeepAliveCount);
    }

    static {
        DelegateRegistry.registerEncoder(ModifySubscriptionResponse::encode, ModifySubscriptionResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModifySubscriptionResponse::decode, ModifySubscriptionResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
