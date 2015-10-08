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

@UaDataType("EventFilter")
public class EventFilter extends MonitoringFilter {

    public static final NodeId TypeId = Identifiers.EventFilter;
    public static final NodeId BinaryEncodingId = Identifiers.EventFilter_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EventFilter_Encoding_DefaultXml;

    protected final SimpleAttributeOperand[] _selectClauses;
    protected final ContentFilter _whereClause;

    public EventFilter() {
        super();
        this._selectClauses = null;
        this._whereClause = null;
    }

    public EventFilter(SimpleAttributeOperand[] _selectClauses, ContentFilter _whereClause) {
        super();
        this._selectClauses = _selectClauses;
        this._whereClause = _whereClause;
    }

    public SimpleAttributeOperand[] getSelectClauses() {
        return _selectClauses;
    }

    public ContentFilter getWhereClause() {
        return _whereClause;
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


    public static void encode(EventFilter eventFilter, UaEncoder encoder) {
        encoder.encodeArray("SelectClauses", eventFilter._selectClauses, encoder::encodeSerializable);
        encoder.encodeSerializable("WhereClause", eventFilter._whereClause != null ? eventFilter._whereClause : new ContentFilter());
    }

    public static EventFilter decode(UaDecoder decoder) {
        SimpleAttributeOperand[] _selectClauses = decoder.decodeArray("SelectClauses", decoder::decodeSerializable, SimpleAttributeOperand.class);
        ContentFilter _whereClause = decoder.decodeSerializable("WhereClause", ContentFilter.class);

        return new EventFilter(_selectClauses, _whereClause);
    }

    static {
        DelegateRegistry.registerEncoder(EventFilter::encode, EventFilter.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(EventFilter::decode, EventFilter.class, BinaryEncodingId, XmlEncodingId);
    }

}
