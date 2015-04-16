package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("RegisterServerRequest")
public class RegisterServerRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RegisterServerRequest;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterServerRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterServerRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final RegisteredServer _server;

    public RegisterServerRequest() {
        this._requestHeader = null;
        this._server = null;
    }

    public RegisterServerRequest(RequestHeader _requestHeader, RegisteredServer _server) {
        this._requestHeader = _requestHeader;
        this._server = _server;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public RegisteredServer getServer() {
        return _server;
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


    public static void encode(RegisterServerRequest registerServerRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", registerServerRequest._requestHeader != null ? registerServerRequest._requestHeader : new RequestHeader());
        encoder.encodeSerializable("Server", registerServerRequest._server != null ? registerServerRequest._server : new RegisteredServer());
    }

    public static RegisterServerRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        RegisteredServer _server = decoder.decodeSerializable("Server", RegisteredServer.class);

        return new RegisterServerRequest(_requestHeader, _server);
    }

    static {
        DelegateRegistry.registerEncoder(RegisterServerRequest::encode, RegisterServerRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RegisterServerRequest::decode, RegisterServerRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
