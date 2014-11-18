package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

@UaDataType("UnregisterNodesResponse")
public class UnregisterNodesResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.UnregisterNodesResponse;
    public static final NodeId BinaryEncodingId = Identifiers.UnregisterNodesResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UnregisterNodesResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;

    public UnregisterNodesResponse() {
        this._responseHeader = null;
    }

    public UnregisterNodesResponse(ResponseHeader _responseHeader) {
        this._responseHeader = _responseHeader;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
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


    public static void encode(UnregisterNodesResponse unregisterNodesResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", unregisterNodesResponse._responseHeader != null ? unregisterNodesResponse._responseHeader : new ResponseHeader());
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
