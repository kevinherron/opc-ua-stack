package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class UnregisterNodesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.UnregisterNodesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.UnregisterNodesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UnregisterNodesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final NodeId[] _nodesToUnregister;

    public UnregisterNodesRequest(RequestHeader _requestHeader, NodeId[] _nodesToUnregister) {
        this._requestHeader = _requestHeader;
        this._nodesToUnregister = _nodesToUnregister;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public NodeId[] getNodesToUnregister() {
        return _nodesToUnregister;
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


    public static void encode(UnregisterNodesRequest unregisterNodesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", unregisterNodesRequest._requestHeader);
        encoder.encodeArray("NodesToUnregister", unregisterNodesRequest._nodesToUnregister, encoder::encodeNodeId);
    }

    public static UnregisterNodesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        NodeId[] _nodesToUnregister = decoder.decodeArray("NodesToUnregister", decoder::decodeNodeId, NodeId.class);

        return new UnregisterNodesRequest(_requestHeader, _nodesToUnregister);
    }

    static {
        DelegateRegistry.registerEncoder(UnregisterNodesRequest::encode, UnregisterNodesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UnregisterNodesRequest::decode, UnregisterNodesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
