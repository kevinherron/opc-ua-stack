package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

public class AddNodesResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.AddNodesResult;
    public static final NodeId BinaryEncodingId = Identifiers.AddNodesResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AddNodesResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final NodeId _addedNodeId;

    public AddNodesResult(StatusCode _statusCode, NodeId _addedNodeId) {
        this._statusCode = _statusCode;
        this._addedNodeId = _addedNodeId;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public NodeId getAddedNodeId() {
        return _addedNodeId;
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


    public static void encode(AddNodesResult addNodesResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", addNodesResult._statusCode);
        encoder.encodeNodeId("AddedNodeId", addNodesResult._addedNodeId);
    }

    public static AddNodesResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        NodeId _addedNodeId = decoder.decodeNodeId("AddedNodeId");

        return new AddNodesResult(_statusCode, _addedNodeId);
    }

    static {
        DelegateRegistry.registerEncoder(AddNodesResult::encode, AddNodesResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AddNodesResult::decode, AddNodesResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
