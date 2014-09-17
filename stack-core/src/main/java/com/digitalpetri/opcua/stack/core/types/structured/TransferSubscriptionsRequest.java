package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class TransferSubscriptionsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.TransferSubscriptionsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.TransferSubscriptionsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TransferSubscriptionsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long[] _subscriptionIds;
    protected final Boolean _sendInitialValues;

    public TransferSubscriptionsRequest(RequestHeader _requestHeader, Long[] _subscriptionIds, Boolean _sendInitialValues) {
        this._requestHeader = _requestHeader;
        this._subscriptionIds = _subscriptionIds;
        this._sendInitialValues = _sendInitialValues;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long[] getSubscriptionIds() { return _subscriptionIds; }

    public Boolean getSendInitialValues() { return _sendInitialValues; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(TransferSubscriptionsRequest transferSubscriptionsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", transferSubscriptionsRequest._requestHeader);
        encoder.encodeArray("SubscriptionIds", transferSubscriptionsRequest._subscriptionIds, encoder::encodeUInt32);
        encoder.encodeBoolean("SendInitialValues", transferSubscriptionsRequest._sendInitialValues);
    }

    public static TransferSubscriptionsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long[] _subscriptionIds = decoder.decodeArray("SubscriptionIds", decoder::decodeUInt32, Long.class);
        Boolean _sendInitialValues = decoder.decodeBoolean("SendInitialValues");

        return new TransferSubscriptionsRequest(_requestHeader, _subscriptionIds, _sendInitialValues);
    }

    static {
        DelegateRegistry.registerEncoder(TransferSubscriptionsRequest::encode, TransferSubscriptionsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TransferSubscriptionsRequest::decode, TransferSubscriptionsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
