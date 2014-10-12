package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class SetTriggeringRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.SetTriggeringRequest;
    public static final NodeId BinaryEncodingId = Identifiers.SetTriggeringRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SetTriggeringRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long _subscriptionId;
    protected final Long _triggeringItemId;
    protected final Long[] _linksToAdd;
    protected final Long[] _linksToRemove;

    public SetTriggeringRequest(RequestHeader _requestHeader, Long _subscriptionId, Long _triggeringItemId, Long[] _linksToAdd, Long[] _linksToRemove) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._triggeringItemId = _triggeringItemId;
        this._linksToAdd = _linksToAdd;
        this._linksToRemove = _linksToRemove;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public Long getTriggeringItemId() { return _triggeringItemId; }

    public Long[] getLinksToAdd() { return _linksToAdd; }

    public Long[] getLinksToRemove() { return _linksToRemove; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SetTriggeringRequest setTriggeringRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", setTriggeringRequest._requestHeader);
        encoder.encodeUInt32("SubscriptionId", setTriggeringRequest._subscriptionId);
        encoder.encodeUInt32("TriggeringItemId", setTriggeringRequest._triggeringItemId);
        encoder.encodeArray("LinksToAdd", setTriggeringRequest._linksToAdd, encoder::encodeUInt32);
        encoder.encodeArray("LinksToRemove", setTriggeringRequest._linksToRemove, encoder::encodeUInt32);
    }

    public static SetTriggeringRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Long _triggeringItemId = decoder.decodeUInt32("TriggeringItemId");
        Long[] _linksToAdd = decoder.decodeArray("LinksToAdd", decoder::decodeUInt32, Long.class);
        Long[] _linksToRemove = decoder.decodeArray("LinksToRemove", decoder::decodeUInt32, Long.class);

        return new SetTriggeringRequest(_requestHeader, _subscriptionId, _triggeringItemId, _linksToAdd, _linksToRemove);
    }

    static {
        DelegateRegistry.registerEncoder(SetTriggeringRequest::encode, SetTriggeringRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SetTriggeringRequest::decode, SetTriggeringRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
