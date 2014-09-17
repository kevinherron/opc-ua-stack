package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class DeleteNodesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.DeleteNodesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteNodesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteNodesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final DeleteNodesItem[] _nodesToDelete;

    public DeleteNodesRequest(RequestHeader _requestHeader, DeleteNodesItem[] _nodesToDelete) {
        this._requestHeader = _requestHeader;
        this._nodesToDelete = _nodesToDelete;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public DeleteNodesItem[] getNodesToDelete() { return _nodesToDelete; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(DeleteNodesRequest deleteNodesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", deleteNodesRequest._requestHeader);
        encoder.encodeArray("NodesToDelete", deleteNodesRequest._nodesToDelete, encoder::encodeSerializable);
    }

    public static DeleteNodesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        DeleteNodesItem[] _nodesToDelete = decoder.decodeArray("NodesToDelete", decoder::decodeSerializable, DeleteNodesItem.class);

        return new DeleteNodesRequest(_requestHeader, _nodesToDelete);
    }

    static {
        DelegateRegistry.registerEncoder(DeleteNodesRequest::encode, DeleteNodesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DeleteNodesRequest::decode, DeleteNodesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
