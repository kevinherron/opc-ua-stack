package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class EventNotificationList extends NotificationData {

    public static final NodeId TypeId = Identifiers.EventNotificationList;
    public static final NodeId BinaryEncodingId = Identifiers.EventNotificationList_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EventNotificationList_Encoding_DefaultXml;

    protected final EventFieldList[] _events;

    public EventNotificationList(EventFieldList[] _events) {
        super();
        this._events = _events;
    }

    public EventFieldList[] getEvents() {
        return _events;
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


    public static void encode(EventNotificationList eventNotificationList, UaEncoder encoder) {
        encoder.encodeArray("Events", eventNotificationList._events, encoder::encodeSerializable);
    }

    public static EventNotificationList decode(UaDecoder decoder) {
        EventFieldList[] _events = decoder.decodeArray("Events", decoder::decodeSerializable, EventFieldList.class);

        return new EventNotificationList(_events);
    }

    static {
        DelegateRegistry.registerEncoder(EventNotificationList::encode, EventNotificationList.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(EventNotificationList::decode, EventNotificationList.class, BinaryEncodingId, XmlEncodingId);
    }

}
