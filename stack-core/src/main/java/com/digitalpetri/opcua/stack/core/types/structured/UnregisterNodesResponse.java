package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class UnregisterNodesResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.UnregisterNodesResponse;
    public static final NodeId BinaryEncodingId = Identifiers.UnregisterNodesResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UnregisterNodesResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;

    public UnregisterNodesResponse(ResponseHeader _responseHeader) {
        this._responseHeader = _responseHeader;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(UnregisterNodesResponse unregisterNodesResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", unregisterNodesResponse._responseHeader);
    }

    public static UnregisterNodesResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);

        return new UnregisterNodesResponse(_responseHeader);
    }

    static {
        DelegateRegistry.registerEncoder(UnregisterNodesResponse::encode, UnregisterNodesResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UnregisterNodesResponse::decode, UnregisterNodesResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
