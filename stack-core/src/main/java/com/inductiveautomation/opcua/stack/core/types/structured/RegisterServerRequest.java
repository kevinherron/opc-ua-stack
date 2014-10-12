package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class RegisterServerRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RegisterServerRequest;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterServerRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterServerRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final RegisteredServer _server;

    public RegisterServerRequest(RequestHeader _requestHeader, RegisteredServer _server) {
        this._requestHeader = _requestHeader;
        this._server = _server;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public RegisteredServer getServer() { return _server; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RegisterServerRequest registerServerRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", registerServerRequest._requestHeader);
        encoder.encodeSerializable("Server", registerServerRequest._server);
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
