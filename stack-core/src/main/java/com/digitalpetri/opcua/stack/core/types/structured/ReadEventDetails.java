package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ReadEventDetails")
public class ReadEventDetails extends HistoryReadDetails {

    public static final NodeId TypeId = Identifiers.ReadEventDetails;
    public static final NodeId BinaryEncodingId = Identifiers.ReadEventDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ReadEventDetails_Encoding_DefaultXml;

    protected final UInteger _numValuesPerNode;
    protected final DateTime _startTime;
    protected final DateTime _endTime;
    protected final EventFilter _filter;

    public ReadEventDetails() {
        super();
        this._numValuesPerNode = null;
        this._startTime = null;
        this._endTime = null;
        this._filter = null;
    }

    public ReadEventDetails(UInteger _numValuesPerNode, DateTime _startTime, DateTime _endTime, EventFilter _filter) {
        super();
        this._numValuesPerNode = _numValuesPerNode;
        this._startTime = _startTime;
        this._endTime = _endTime;
        this._filter = _filter;
    }

    public UInteger getNumValuesPerNode() {
        return _numValuesPerNode;
    }

    public DateTime getStartTime() {
        return _startTime;
    }

    public DateTime getEndTime() {
        return _endTime;
    }

    public EventFilter getFilter() {
        return _filter;
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


    public static void encode(ReadEventDetails readEventDetails, UaEncoder encoder) {
        encoder.encodeUInt32("NumValuesPerNode", readEventDetails._numValuesPerNode);
        encoder.encodeDateTime("StartTime", readEventDetails._startTime);
        encoder.encodeDateTime("EndTime", readEventDetails._endTime);
        encoder.encodeSerializable("Filter", readEventDetails._filter != null ? readEventDetails._filter : new EventFilter());
    }

    public static ReadEventDetails decode(UaDecoder decoder) {
        UInteger _numValuesPerNode = decoder.decodeUInt32("NumValuesPerNode");
        DateTime _startTime = decoder.decodeDateTime("StartTime");
        DateTime _endTime = decoder.decodeDateTime("EndTime");
        EventFilter _filter = decoder.decodeSerializable("Filter", EventFilter.class);

        return new ReadEventDetails(_numValuesPerNode, _startTime, _endTime, _filter);
    }

    static {
        DelegateRegistry.registerEncoder(ReadEventDetails::encode, ReadEventDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ReadEventDetails::decode, ReadEventDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
