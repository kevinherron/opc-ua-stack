package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.PerformUpdateType;

public class UpdateEventDetails extends HistoryUpdateDetails {

    public static final NodeId TypeId = Identifiers.UpdateEventDetails;
    public static final NodeId BinaryEncodingId = Identifiers.UpdateEventDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UpdateEventDetails_Encoding_DefaultXml;

    protected final PerformUpdateType _performInsertReplace;
    protected final EventFilter _filter;
    protected final HistoryEventFieldList[] _eventData;

    public UpdateEventDetails(NodeId _nodeId, PerformUpdateType _performInsertReplace, EventFilter _filter, HistoryEventFieldList[] _eventData) {
        super(_nodeId);
        this._performInsertReplace = _performInsertReplace;
        this._filter = _filter;
        this._eventData = _eventData;
    }

    public PerformUpdateType getPerformInsertReplace() { return _performInsertReplace; }

    public EventFilter getFilter() { return _filter; }

    public HistoryEventFieldList[] getEventData() { return _eventData; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(UpdateEventDetails updateEventDetails, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", updateEventDetails._nodeId);
        encoder.encodeSerializable("PerformInsertReplace", updateEventDetails._performInsertReplace);
        encoder.encodeSerializable("Filter", updateEventDetails._filter);
        encoder.encodeArray("EventData", updateEventDetails._eventData, encoder::encodeSerializable);
    }

    public static UpdateEventDetails decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        PerformUpdateType _performInsertReplace = decoder.decodeSerializable("PerformInsertReplace", PerformUpdateType.class);
        EventFilter _filter = decoder.decodeSerializable("Filter", EventFilter.class);
        HistoryEventFieldList[] _eventData = decoder.decodeArray("EventData", decoder::decodeSerializable, HistoryEventFieldList.class);

        return new UpdateEventDetails(_nodeId, _performInsertReplace, _filter, _eventData);
    }

    static {
        DelegateRegistry.registerEncoder(UpdateEventDetails::encode, UpdateEventDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UpdateEventDetails::decode, UpdateEventDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
