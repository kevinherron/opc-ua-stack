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
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("HistoryEvent")
public class HistoryEvent implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryEvent;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryEvent_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryEvent_Encoding_DefaultXml;

    protected final HistoryEventFieldList[] _events;

    public HistoryEvent() {
        this._events = null;
    }

    public HistoryEvent(HistoryEventFieldList[] _events) {
        this._events = _events;
    }

    public HistoryEventFieldList[] getEvents() {
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
