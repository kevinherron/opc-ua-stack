package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class DeleteAtTimeDetails extends HistoryUpdateDetails {

    public static final NodeId TypeId = Identifiers.DeleteAtTimeDetails;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteAtTimeDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteAtTimeDetails_Encoding_DefaultXml;

    protected final DateTime[] _reqTimes;

    public DeleteAtTimeDetails(NodeId _nodeId, DateTime[] _reqTimes) {
        super(_nodeId);
        this._reqTimes = _reqTimes;
    }

    public DateTime[] getReqTimes() {
        return _reqTimes;
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


    public static void encode(DeleteAtTimeDetails deleteAtTimeDetails, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", deleteAtTimeDetails._nodeId);
        encoder.encodeArray("ReqTimes", deleteAtTimeDetails._reqTimes, encoder::encodeDateTime);
    }

    public static DeleteAtTimeDetails decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        DateTime[] _reqTimes = decoder.decodeArray("ReqTimes", decoder::decodeDateTime, DateTime.class);

        return new DeleteAtTimeDetails(_nodeId, _reqTimes);
    }

    static {
        DelegateRegistry.registerEncoder(DeleteAtTimeDetails::encode, DeleteAtTimeDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DeleteAtTimeDetails::decode, DeleteAtTimeDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
