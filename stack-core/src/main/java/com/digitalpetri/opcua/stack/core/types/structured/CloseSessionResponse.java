package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class CloseSessionResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.CloseSessionResponse;
    public static final NodeId BinaryEncodingId = Identifiers.CloseSessionResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CloseSessionResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;

    public CloseSessionResponse(ResponseHeader _responseHeader) {
        this._responseHeader = _responseHeader;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CloseSessionResponse closeSessionResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", closeSessionResponse._responseHeader);
    }

    public static CloseSessionResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);

        return new CloseSessionResponse(_responseHeader);
    }

    static {
        DelegateRegistry.registerEncoder(CloseSessionResponse::encode, CloseSessionResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CloseSessionResponse::decode, CloseSessionResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
