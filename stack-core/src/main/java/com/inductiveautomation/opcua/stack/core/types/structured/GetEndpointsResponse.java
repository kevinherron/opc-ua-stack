package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class GetEndpointsResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.GetEndpointsResponse;
    public static final NodeId BinaryEncodingId = Identifiers.GetEndpointsResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.GetEndpointsResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final EndpointDescription[] _endpoints;

    public GetEndpointsResponse(ResponseHeader _responseHeader, EndpointDescription[] _endpoints) {
        this._responseHeader = _responseHeader;
        this._endpoints = _endpoints;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public EndpointDescription[] getEndpoints() { return _endpoints; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(GetEndpointsResponse getEndpointsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", getEndpointsResponse._responseHeader);
        encoder.encodeArray("Endpoints", getEndpointsResponse._endpoints, encoder::encodeSerializable);
    }

    public static GetEndpointsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        EndpointDescription[] _endpoints = decoder.decodeArray("Endpoints", decoder::decodeSerializable, EndpointDescription.class);

        return new GetEndpointsResponse(_responseHeader, _endpoints);
    }

    static {
        DelegateRegistry.registerEncoder(GetEndpointsResponse::encode, GetEndpointsResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(GetEndpointsResponse::decode, GetEndpointsResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
