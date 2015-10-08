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
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("QueryFirstResponse")
public class QueryFirstResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.QueryFirstResponse;
    public static final NodeId BinaryEncodingId = Identifiers.QueryFirstResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.QueryFirstResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final QueryDataSet[] _queryDataSets;
    protected final ByteString _continuationPoint;
    protected final ParsingResult[] _parsingResults;
    protected final DiagnosticInfo[] _diagnosticInfos;
    protected final ContentFilterResult _filterResult;

    public QueryFirstResponse() {
        this._responseHeader = null;
        this._queryDataSets = null;
        this._continuationPoint = null;
        this._parsingResults = null;
        this._diagnosticInfos = null;
        this._filterResult = null;
    }

    public QueryFirstResponse(ResponseHeader _responseHeader, QueryDataSet[] _queryDataSets, ByteString _continuationPoint, ParsingResult[] _parsingResults, DiagnosticInfo[] _diagnosticInfos, ContentFilterResult _filterResult) {
        this._responseHeader = _responseHeader;
        this._queryDataSets = _queryDataSets;
        this._continuationPoint = _continuationPoint;
        this._parsingResults = _parsingResults;
        this._diagnosticInfos = _diagnosticInfos;
        this._filterResult = _filterResult;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public QueryDataSet[] getQueryDataSets() {
        return _queryDataSets;
    }

    public ByteString getContinuationPoint() {
        return _continuationPoint;
    }

    public ParsingResult[] getParsingResults() {
        return _parsingResults;
    }

    public DiagnosticInfo[] getDiagnosticInfos() {
        return _diagnosticInfos;
    }

    public ContentFilterResult getFilterResult() {
        return _filterResult;
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


    public static void encode(QueryFirstResponse queryFirstResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", queryFirstResponse._responseHeader != null ? queryFirstResponse._responseHeader : new ResponseHeader());
        encoder.encodeArray("QueryDataSets", queryFirstResponse._queryDataSets, encoder::encodeSerializable);
        encoder.encodeByteString("ContinuationPoint", queryFirstResponse._continuationPoint);
        encoder.encodeArray("ParsingResults", queryFirstResponse._parsingResults, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", queryFirstResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
        encoder.encodeSerializable("FilterResult", queryFirstResponse._filterResult != null ? queryFirstResponse._filterResult : new ContentFilterResult());
    }

    public static QueryFirstResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        QueryDataSet[] _queryDataSets = decoder.decodeArray("QueryDataSets", decoder::decodeSerializable, QueryDataSet.class);
        ByteString _continuationPoint = decoder.decodeByteString("ContinuationPoint");
        ParsingResult[] _parsingResults = decoder.decodeArray("ParsingResults", decoder::decodeSerializable, ParsingResult.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);
        ContentFilterResult _filterResult = decoder.decodeSerializable("FilterResult", ContentFilterResult.class);

        return new QueryFirstResponse(_responseHeader, _queryDataSets, _continuationPoint, _parsingResults, _diagnosticInfos, _filterResult);
    }

    static {
        DelegateRegistry.registerEncoder(QueryFirstResponse::encode, QueryFirstResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(QueryFirstResponse::decode, QueryFirstResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
