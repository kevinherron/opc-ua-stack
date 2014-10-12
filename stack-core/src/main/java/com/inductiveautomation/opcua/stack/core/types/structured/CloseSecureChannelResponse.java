package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class CloseSecureChannelResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.CloseSecureChannelResponse;
    public static final NodeId BinaryEncodingId = Identifiers.CloseSecureChannelResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CloseSecureChannelResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;

    public CloseSecureChannelResponse(ResponseHeader _responseHeader) {
        this._responseHeader = _responseHeader;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CloseSecureChannelResponse closeSecureChannelResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", closeSecureChannelResponse._responseHeader);
    }

    public static CloseSecureChannelResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);

        return new CloseSecureChannelResponse(_responseHeader);
    }

    static {
        DelegateRegistry.registerEncoder(CloseSecureChannelResponse::encode, CloseSecureChannelResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CloseSecureChannelResponse::decode, CloseSecureChannelResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
