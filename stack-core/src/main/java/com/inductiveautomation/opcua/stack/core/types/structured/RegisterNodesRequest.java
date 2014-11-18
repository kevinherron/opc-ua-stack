package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

@UaDataType("RegisterNodesRequest")
public class RegisterNodesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RegisterNodesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterNodesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterNodesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final NodeId[] _nodesToRegister;

    public RegisterNodesRequest() {
        this._requestHeader = null;
        this._nodesToRegister = null;
    }

    public RegisterNodesRequest(RequestHeader _requestHeader, NodeId[] _nodesToRegister) {
        this._requestHeader = _requestHeader;
        this._nodesToRegister = _nodesToRegister;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public NodeId[] getNodesToRegister() {
        return _nodesToRegister;
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


    public static void encode(RegisterNodesRequest registerNodesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", registerNodesRequest._requestHeader != null ? registerNodesRequest._requestHeader : new RequestHeader());
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
