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

@UaDataType("ParsingResult")
public class ParsingResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.ParsingResult;
    public static final NodeId BinaryEncodingId = Identifiers.ParsingResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ParsingResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final StatusCode[] _dataStatusCodes;
    protected final DiagnosticInfo[] _dataDiagnosticInfos;

    public ParsingResult() {
        this._statusCode = null;
        this._dataStatusCodes = null;
        this._dataDiagnosticInfos = null;
    }

    public ParsingResult(StatusCode _statusCode, StatusCode[] _dataStatusCodes, DiagnosticInfo[] _dataDiagnosticInfos) {
        this._statusCode = _statusCode;
        this._dataStatusCodes = _dataStatusCodes;
        this._dataDiagnosticInfos = _dataDiagnosticInfos;
    }

    public StatusCode getStatusCode() { return _statusCode; }

    public StatusCode[] getDataStatusCodes() { return _dataStatusCodes; }

    public DiagnosticInfo[] getDataDiagnosticInfos() { return _dataDiagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ParsingResult parsingResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", parsingResult._statusCode);
        encoder.encodeArray("DataStatusCodes", parsingResult._dataStatusCodes, encoder::encodeStatusCode);
        encoder.encodeArray("DataDiagnosticInfos", parsingResult._dataDiagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static ParsingResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        StatusCode[] _dataStatusCodes = decoder.decodeArray("DataStatusCodes", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _dataDiagnosticInfos = decoder.decodeArray("DataDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new ParsingResult(_statusCode, _dataStatusCodes, _dataDiagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(ParsingResult::encode, ParsingResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ParsingResult::decode, ParsingResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
