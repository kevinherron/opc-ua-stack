package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class SetMonitoringModeResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.SetMonitoringModeResponse;
    public static final NodeId BinaryEncodingId = Identifiers.SetMonitoringModeResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SetMonitoringModeResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public SetMonitoringModeResponse(ResponseHeader _responseHeader, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public StatusCode[] getResults() { return _results; }

    public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SetMonitoringModeResponse setMonitoringModeResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", setMonitoringModeResponse._responseHeader);
        encoder.encodeArray("Results", setMonitoringModeResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", setMonitoringModeResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static SetMonitoringModeResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        StatusCode[] _results = decoder.decodeArray("Results", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new SetMonitoringModeResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(SetMonitoringModeResponse::encode, SetMonitoringModeResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SetMonitoringModeResponse::decode, SetMonitoringModeResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
