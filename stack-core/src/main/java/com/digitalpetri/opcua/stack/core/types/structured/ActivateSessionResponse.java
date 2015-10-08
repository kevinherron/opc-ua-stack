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
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("ActivateSessionResponse")
public class ActivateSessionResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.ActivateSessionResponse;
    public static final NodeId BinaryEncodingId = Identifiers.ActivateSessionResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ActivateSessionResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final ByteString _serverNonce;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public ActivateSessionResponse() {
        this._responseHeader = null;
        this._serverNonce = null;
        this._results = null;
        this._diagnosticInfos = null;
    }

    public ActivateSessionResponse(ResponseHeader _responseHeader, ByteString _serverNonce, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._serverNonce = _serverNonce;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public ByteString getServerNonce() {
        return _serverNonce;
    }

    public StatusCode[] getResults() {
        return _results;
    }

    public DiagnosticInfo[] getDiagnosticInfos() {
        return _diagnosticInfos;
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


    public static void encode(ActivateSessionResponse activateSessionResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", activateSessionResponse._responseHeader != null ? activateSessionResponse._responseHeader : new ResponseHeader());
        encoder.encodeByteString("ServerNonce", activateSessionResponse._serverNonce);
        encoder.encodeArray("Results", activateSessionResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", activateSessionResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static ActivateSessionResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        ByteString _serverNonce = decoder.decodeByteString("ServerNonce");
        StatusCode[] _results = decoder.decodeArray("Results", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new ActivateSessionResponse(_responseHeader, _serverNonce, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(ActivateSessionResponse::encode, ActivateSessionResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ActivateSessionResponse::decode, ActivateSessionResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
