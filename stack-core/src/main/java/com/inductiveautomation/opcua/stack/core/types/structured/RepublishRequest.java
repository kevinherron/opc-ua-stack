package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public class RepublishRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RepublishRequest;
    public static final NodeId BinaryEncodingId = Identifiers.RepublishRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RepublishRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _subscriptionId;
    protected final UInteger _retransmitSequenceNumber;

    public RepublishRequest(RequestHeader _requestHeader, UInteger _subscriptionId, UInteger _retransmitSequenceNumber) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._retransmitSequenceNumber = _retransmitSequenceNumber;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public UInteger getSubscriptionId() {
        return _subscriptionId;
    }

    public UInteger getRetransmitSequenceNumber() {
        return _retransmitSequenceNumber;
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


    public static void encode(RepublishRequest republishRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", republishRequest._requestHeader);
        encoder.encodeUInt32("SubscriptionId", republishRequest._subscriptionId);
        encoder.encodeUInt32("RetransmitSequenceNumber", republishRequest._retransmitSequenceNumber);
    }

    public static RepublishRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        UInteger _retransmitSequenceNumber = decoder.decodeUInt32("RetransmitSequenceNumber");

        return new RepublishRequest(_requestHeader, _subscriptionId, _retransmitSequenceNumber);
    }

    static {
        DelegateRegistry.registerEncoder(RepublishRequest::encode, RepublishRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RepublishRequest::decode, RepublishRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
