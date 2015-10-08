/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("EventNotificationList")
public class EventNotificationList extends NotificationData {

    public static final NodeId TypeId = Identifiers.EventNotificationList;
    public static final NodeId BinaryEncodingId = Identifiers.EventNotificationList_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EventNotificationList_Encoding_DefaultXml;

    protected final EventFieldList[] _events;

    public EventNotificationList() {
        super();
        this._events = null;
    }

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
