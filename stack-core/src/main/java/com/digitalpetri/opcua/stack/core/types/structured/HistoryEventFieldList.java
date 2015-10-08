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
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

@UaDataType("HistoryEventFieldList")
public class HistoryEventFieldList implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryEventFieldList;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryEventFieldList_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryEventFieldList_Encoding_DefaultXml;

    protected final Variant[] _eventFields;

    public HistoryEventFieldList() {
        this._eventFields = null;
    }

    public HistoryEventFieldList(Variant[] _eventFields) {
        this._eventFields = _eventFields;
    }

    public Variant[] getEventFields() {
        return _eventFields;
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


    public static void encode(HistoryEventFieldList historyEventFieldList, UaEncoder encoder) {
        encoder.encodeArray("EventFields", historyEventFieldList._eventFields, encoder::encodeVariant);
    }

    public static HistoryEventFieldList decode(UaDecoder decoder) {
        Variant[] _eventFields = decoder.decodeArray("EventFields", decoder::decodeVariant, Variant.class);

        return new HistoryEventFieldList(_eventFields);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryEventFieldList::encode, HistoryEventFieldList.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryEventFieldList::decode, HistoryEventFieldList.class, BinaryEncodingId, XmlEncodingId);
    }

}
