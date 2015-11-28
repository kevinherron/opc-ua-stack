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
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("HistoryUpdateResult")
public class HistoryUpdateResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryUpdateResult;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryUpdateResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryUpdateResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final StatusCode[] _operationResults;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public HistoryUpdateResult() {
        this._statusCode = null;
        this._operationResults = null;
        this._diagnosticInfos = null;
    }

    public HistoryUpdateResult(StatusCode _statusCode, StatusCode[] _operationResults, DiagnosticInfo[] _diagnosticInfos) {
        this._statusCode = _statusCode;
        this._operationResults = _operationResults;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public StatusCode getStatusCode() { return _statusCode; }

    public StatusCode[] getOperationResults() { return _operationResults; }

    public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(HistoryUpdateResult historyUpdateResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", historyUpdateResult._statusCode);
        encoder.encodeArray("OperationResults", historyUpdateResult._operationResults, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", historyUpdateResult._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static HistoryUpdateResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        StatusCode[] _operationResults = decoder.decodeArray("OperationResults", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new HistoryUpdateResult(_statusCode, _operationResults, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryUpdateResult::encode, HistoryUpdateResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryUpdateResult::decode, HistoryUpdateResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
