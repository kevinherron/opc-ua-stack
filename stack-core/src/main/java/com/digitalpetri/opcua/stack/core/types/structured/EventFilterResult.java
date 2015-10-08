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
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("EventFilterResult")
public class EventFilterResult extends MonitoringFilterResult {

    public static final NodeId TypeId = Identifiers.EventFilterResult;
    public static final NodeId BinaryEncodingId = Identifiers.EventFilterResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EventFilterResult_Encoding_DefaultXml;

    protected final StatusCode[] _selectClauseResults;
    protected final DiagnosticInfo[] _selectClauseDiagnosticInfos;
    protected final ContentFilterResult _whereClauseResult;

    public EventFilterResult() {
        super();
        this._selectClauseResults = null;
        this._selectClauseDiagnosticInfos = null;
        this._whereClauseResult = null;
    }

    public EventFilterResult(StatusCode[] _selectClauseResults, DiagnosticInfo[] _selectClauseDiagnosticInfos, ContentFilterResult _whereClauseResult) {
        super();
        this._selectClauseResults = _selectClauseResults;
        this._selectClauseDiagnosticInfos = _selectClauseDiagnosticInfos;
        this._whereClauseResult = _whereClauseResult;
    }

    public StatusCode[] getSelectClauseResults() {
        return _selectClauseResults;
    }

    public DiagnosticInfo[] getSelectClauseDiagnosticInfos() {
        return _selectClauseDiagnosticInfos;
    }

    public ContentFilterResult getWhereClauseResult() {
        return _whereClauseResult;
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


    public static void encode(EventFilterResult eventFilterResult, UaEncoder encoder) {
        encoder.encodeArray("SelectClauseResults", eventFilterResult._selectClauseResults, encoder::encodeStatusCode);
        encoder.encodeArray("SelectClauseDiagnosticInfos", eventFilterResult._selectClauseDiagnosticInfos, encoder::encodeDiagnosticInfo);
        encoder.encodeSerializable("WhereClauseResult", eventFilterResult._whereClauseResult != null ? eventFilterResult._whereClauseResult : new ContentFilterResult());
    }

    public static EventFilterResult decode(UaDecoder decoder) {
        StatusCode[] _selectClauseResults = decoder.decodeArray("SelectClauseResults", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _selectClauseDiagnosticInfos = decoder.decodeArray("SelectClauseDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);
        ContentFilterResult _whereClauseResult = decoder.decodeSerializable("WhereClauseResult", ContentFilterResult.class);

        return new EventFilterResult(_selectClauseResults, _selectClauseDiagnosticInfos, _whereClauseResult);
    }

    static {
        DelegateRegistry.registerEncoder(EventFilterResult::encode, EventFilterResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(EventFilterResult::decode, EventFilterResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
