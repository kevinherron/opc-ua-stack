package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class FindServersRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.FindServersRequest;
    public static final NodeId BinaryEncodingId = Identifiers.FindServersRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.FindServersRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final String _endpointUrl;
    protected final String[] _localeIds;
    protected final String[] _serverUris;

    public FindServersRequest(RequestHeader _requestHeader, String _endpointUrl, String[] _localeIds, String[] _serverUris) {
        this._requestHeader = _requestHeader;
        this._endpointUrl = _endpointUrl;
        this._localeIds = _localeIds;
        this._serverUris = _serverUris;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public String getEndpointUrl() { return _endpointUrl; }

    public String[] getLocaleIds() { return _localeIds; }

    public String[] getServerUris() { return _serverUris; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(FindServersRequest findServersRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", findServersRequest._requestHeader);
        encoder.encodeString("EndpointUrl", findServersRequest._endpointUrl);
        encoder.encodeArray("LocaleIds", findServersRequest._localeIds, encoder::encodeString);
        encoder.encodeArray("ServerUris", findServersRequest._serverUris, encoder::encodeString);
    }

    public static FindServersRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        String _endpointUrl = decoder.decodeString("EndpointUrl");
        String[] _localeIds = decoder.decodeArray("LocaleIds", decoder::decodeString, String.class);
        String[] _serverUris = decoder.decodeArray("ServerUris", decoder::decodeString, String.class);

        return new FindServersRequest(_requestHeader, _endpointUrl, _localeIds, _serverUris);
    }

    static {
        DelegateRegistry.registerEncoder(FindServersRequest::encode, FindServersRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(FindServersRequest::decode, FindServersRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
