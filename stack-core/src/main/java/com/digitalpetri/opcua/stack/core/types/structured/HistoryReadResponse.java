package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("HistoryReadResponse")
public class HistoryReadResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.HistoryReadResponse;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryReadResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryReadResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final HistoryReadResult[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public HistoryReadResponse() {
        this._responseHeader = null;
        this._results = null;
        this._diagnosticInfos = null;
    }

    public HistoryReadResponse(ResponseHeader _responseHeader, HistoryReadResult[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public HistoryReadResult[] getResults() {
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


    public static void encode(HistoryReadResponse historyReadResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", historyReadResponse._responseHeader != null ? historyReadResponse._responseHeader : new ResponseHeader());
        encoder.encodeArray("Results", historyReadResponse._results, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", historyReadResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static HistoryReadResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        HistoryReadResult[] _results = decoder.decodeArray("Results", decoder::decodeSerializable, HistoryReadResult.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new HistoryReadResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryReadResponse::encode, HistoryReadResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryReadResponse::decode, HistoryReadResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
