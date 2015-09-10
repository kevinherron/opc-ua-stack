package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("CloseSecureChannelRequest")
public class CloseSecureChannelRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.CloseSecureChannelRequest;
    public static final NodeId BinaryEncodingId = Identifiers.CloseSecureChannelRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CloseSecureChannelRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;

    public CloseSecureChannelRequest() {
        this._requestHeader = null;
    }

    public CloseSecureChannelRequest(RequestHeader _requestHeader) {
        this._requestHeader = _requestHeader;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
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


    public static void encode(CloseSecureChannelRequest closeSecureChannelRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", closeSecureChannelRequest._requestHeader != null ? closeSecureChannelRequest._requestHeader : new RequestHeader());
    }

    public static CloseSecureChannelRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);

        return new CloseSecureChannelRequest(_requestHeader);
    }

    static {
        DelegateRegistry.registerEncoder(CloseSecureChannelRequest::encode, CloseSecureChannelRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CloseSecureChannelRequest::decode, CloseSecureChannelRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
