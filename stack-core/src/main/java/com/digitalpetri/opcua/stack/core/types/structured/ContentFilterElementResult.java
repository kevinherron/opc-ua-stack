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

@UaDataType("ContentFilterElementResult")
public class ContentFilterElementResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.ContentFilterElementResult;
    public static final NodeId BinaryEncodingId = Identifiers.ContentFilterElementResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ContentFilterElementResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final StatusCode[] _operandStatusCodes;
    protected final DiagnosticInfo[] _operandDiagnosticInfos;

    public ContentFilterElementResult() {
        this._statusCode = null;
        this._operandStatusCodes = null;
        this._operandDiagnosticInfos = null;
    }

    public ContentFilterElementResult(StatusCode _statusCode, StatusCode[] _operandStatusCodes, DiagnosticInfo[] _operandDiagnosticInfos) {
        this._statusCode = _statusCode;
        this._operandStatusCodes = _operandStatusCodes;
        this._operandDiagnosticInfos = _operandDiagnosticInfos;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public StatusCode[] getOperandStatusCodes() {
        return _operandStatusCodes;
    }

    public DiagnosticInfo[] getOperandDiagnosticInfos() {
        return _operandDiagnosticInfos;
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


    public static void encode(ContentFilterElementResult contentFilterElementResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", contentFilterElementResult._statusCode);
        encoder.encodeArray("OperandStatusCodes", contentFilterElementResult._operandStatusCodes, encoder::encodeStatusCode);
        encoder.encodeArray("OperandDiagnosticInfos", contentFilterElementResult._operandDiagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static ContentFilterElementResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        StatusCode[] _operandStatusCodes = decoder.decodeArray("OperandStatusCodes", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _operandDiagnosticInfos = decoder.decodeArray("OperandDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new ContentFilterElementResult(_statusCode, _operandStatusCodes, _operandDiagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(ContentFilterElementResult::encode, ContentFilterElementResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ContentFilterElementResult::decode, ContentFilterElementResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
