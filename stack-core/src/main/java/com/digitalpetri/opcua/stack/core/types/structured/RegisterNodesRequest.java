package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class RegisterNodesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RegisterNodesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterNodesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterNodesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final NodeId[] _nodesToRegister;

    public RegisterNodesRequest(RequestHeader _requestHeader, NodeId[] _nodesToRegister) {
        this._requestHeader = _requestHeader;
        this._nodesToRegister = _nodesToRegister;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public NodeId[] getNodesToRegister() { return _nodesToRegister; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RegisterNodesRequest registerNodesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", registerNodesRequest._requestHeader);
        encoder.encodeArray("NodesToRegister", registerNodesRequest._nodesToRegister, encoder::encodeNodeId);
    }

    public static RegisterNodesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        NodeId[] _nodesToRegister = decoder.decodeArray("NodesToRegister", decoder::decodeNodeId, NodeId.class);

        return new RegisterNodesRequest(_requestHeader, _nodesToRegister);
    }

    static {
        DelegateRegistry.registerEncoder(RegisterNodesRequest::encode, RegisterNodesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RegisterNodesRequest::decode, RegisterNodesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
