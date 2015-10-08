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

@UaDataType("StatusResult")
public class StatusResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.StatusResult;
    public static final NodeId BinaryEncodingId = Identifiers.StatusResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.StatusResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final DiagnosticInfo _diagnosticInfo;

    public StatusResult() {
        this._statusCode = null;
        this._diagnosticInfo = null;
    }

    public StatusResult(StatusCode _statusCode, DiagnosticInfo _diagnosticInfo) {
        this._statusCode = _statusCode;
        this._diagnosticInfo = _diagnosticInfo;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public DiagnosticInfo getDiagnosticInfo() {
        return _diagnosticInfo;
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


    public static void encode(StatusResult statusResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", statusResult._statusCode);
        encoder.encodeDiagnosticInfo("DiagnosticInfo", statusResult._diagnosticInfo);
    }

    public static StatusResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        DiagnosticInfo _diagnosticInfo = decoder.decodeDiagnosticInfo("DiagnosticInfo");

        return new StatusResult(_statusCode, _diagnosticInfo);
    }

    static {
        DelegateRegistry.registerEncoder(StatusResult::encode, StatusResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(StatusResult::decode, StatusResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
