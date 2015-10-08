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

@UaDataType("TransferSubscriptionsResponse")
public class TransferSubscriptionsResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.TransferSubscriptionsResponse;
    public static final NodeId BinaryEncodingId = Identifiers.TransferSubscriptionsResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TransferSubscriptionsResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final TransferResult[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public TransferSubscriptionsResponse() {
        this._responseHeader = null;
        this._results = null;
        this._diagnosticInfos = null;
    }

    public TransferSubscriptionsResponse(ResponseHeader _responseHeader, TransferResult[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public TransferResult[] getResults() {
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


    public static void encode(TransferSubscriptionsResponse transferSubscriptionsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", transferSubscriptionsResponse._responseHeader != null ? transferSubscriptionsResponse._responseHeader : new ResponseHeader());
        encoder.encodeArray("Results", transferSubscriptionsResponse._results, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", transferSubscriptionsResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static TransferSubscriptionsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        TransferResult[] _results = decoder.decodeArray("Results", decoder::decodeSerializable, TransferResult.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new TransferSubscriptionsResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(TransferSubscriptionsResponse::encode, TransferSubscriptionsResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TransferSubscriptionsResponse::decode, TransferSubscriptionsResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
