package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class HistoryEvent implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryEvent;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryEvent_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryEvent_Encoding_DefaultXml;

    protected final HistoryEventFieldList[] _events;

    public HistoryEvent(HistoryEventFieldList[] _events) {
        this._events = _events;
    }

    public HistoryEventFieldList[] getEvents() { return _events; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(HistoryEvent historyEvent, UaEncoder encoder) {
        encoder.encodeArray("Events", historyEvent._events, encoder::encodeSerializable);
    }

    public static HistoryEvent decode(UaDecoder decoder) {
        HistoryEventFieldList[] _events = decoder.decodeArray("Events", decoder::decodeSerializable, HistoryEventFieldList.class);

        return new HistoryEvent(_events);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryEvent::encode, HistoryEvent.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryEvent::decode, HistoryEvent.class, BinaryEncodingId, XmlEncodingId);
    }

}
