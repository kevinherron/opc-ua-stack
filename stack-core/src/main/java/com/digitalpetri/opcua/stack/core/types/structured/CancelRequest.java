package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class CancelRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.CancelRequest;
    public static final NodeId BinaryEncodingId = Identifiers.CancelRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CancelRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Long _requestHandle;

    public CancelRequest(RequestHeader _requestHeader, Long _requestHandle) {
        this._requestHeader = _requestHeader;
        this._requestHandle = _requestHandle;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Long getRequestHandle() { return _requestHandle; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CancelRequest cancelRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", cancelRequest._requestHeader);
        encoder.encodeUInt32("RequestHandle", cancelRequest._requestHandle);
    }

    public static CancelRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _requestHandle = decoder.decodeUInt32("RequestHandle");

        return new CancelRequest(_requestHeader, _requestHandle);
    }

    static {
        DelegateRegistry.registerEncoder(CancelRequest::encode, CancelRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CancelRequest::decode, CancelRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
