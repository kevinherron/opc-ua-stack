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
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("RegisterServer2Response")
public class RegisterServer2Response implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.RegisterServer2Response;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterServer2Response_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterServer2Response_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final StatusCode[] _configurationResults;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public RegisterServer2Response() {
        this._responseHeader = null;
        this._configurationResults = null;
        this._diagnosticInfos = null;
    }

    public RegisterServer2Response(ResponseHeader _responseHeader, StatusCode[] _configurationResults, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._configurationResults = _configurationResults;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public StatusCode[] getConfigurationResults() { return _configurationResults; }

    public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RegisterServer2Response registerServer2Response, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", registerServer2Response._responseHeader != null ? registerServer2Response._responseHeader : new ResponseHeader());
        encoder.encodeArray("ConfigurationResults", registerServer2Response._configurationResults, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", registerServer2Response._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static RegisterServer2Response decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        StatusCode[] _configurationResults = decoder.decodeArray("ConfigurationResults", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new RegisterServer2Response(_responseHeader, _configurationResults, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(RegisterServer2Response::encode, RegisterServer2Response.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RegisterServer2Response::decode, RegisterServer2Response.class, BinaryEncodingId, XmlEncodingId);
    }

}
