package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("HistoryUpdateDetails")
public class HistoryUpdateDetails implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryUpdateDetails;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryUpdateDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryUpdateDetails_Encoding_DefaultXml;

    protected final NodeId _nodeId;

    public HistoryUpdateDetails() {
        this._nodeId = null;
    }

    public HistoryUpdateDetails(NodeId _nodeId) {
        this._nodeId = _nodeId;
    }

    public NodeId getNodeId() {
        return _nodeId;
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


    public static void encode(HistoryUpdateDetails historyUpdateDetails, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", historyUpdateDetails._nodeId);
    }

    public static HistoryUpdateDetails decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");

        return new HistoryUpdateDetails(_nodeId);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryUpdateDetails::encode, HistoryUpdateDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryUpdateDetails::decode, HistoryUpdateDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
