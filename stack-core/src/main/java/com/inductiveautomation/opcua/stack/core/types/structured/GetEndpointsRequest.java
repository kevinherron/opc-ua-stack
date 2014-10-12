package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class GetEndpointsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.GetEndpointsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.GetEndpointsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.GetEndpointsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final String _endpointUrl;
    protected final String[] _localeIds;
    protected final String[] _profileUris;

    public GetEndpointsRequest(RequestHeader _requestHeader, String _endpointUrl, String[] _localeIds, String[] _profileUris) {
        this._requestHeader = _requestHeader;
        this._endpointUrl = _endpointUrl;
        this._localeIds = _localeIds;
        this._profileUris = _profileUris;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public String getEndpointUrl() { return _endpointUrl; }

    public String[] getLocaleIds() { return _localeIds; }

    public String[] getProfileUris() { return _profileUris; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(GetEndpointsRequest getEndpointsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", getEndpointsRequest._requestHeader);
        encoder.encodeString("EndpointUrl", getEndpointsRequest._endpointUrl);
        encoder.encodeArray("LocaleIds", getEndpointsRequest._localeIds, encoder::encodeString);
        encoder.encodeArray("ProfileUris", getEndpointsRequest._profileUris, encoder::encodeString);
    }

    public static GetEndpointsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        String _endpointUrl = decoder.decodeString("EndpointUrl");
        String[] _localeIds = decoder.decodeArray("LocaleIds", decoder::decodeString, String.class);
        String[] _profileUris = decoder.decodeArray("ProfileUris", decoder::decodeString, String.class);

        return new GetEndpointsRequest(_requestHeader, _endpointUrl, _localeIds, _profileUris);
    }

    static {
        DelegateRegistry.registerEncoder(GetEndpointsRequest::encode, GetEndpointsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(GetEndpointsRequest::decode, GetEndpointsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
