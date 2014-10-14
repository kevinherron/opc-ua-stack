package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class CreateMonitoredItemsResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.CreateMonitoredItemsResponse;
    public static final NodeId BinaryEncodingId = Identifiers.CreateMonitoredItemsResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CreateMonitoredItemsResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final MonitoredItemCreateResult[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public CreateMonitoredItemsResponse(ResponseHeader _responseHeader, MonitoredItemCreateResult[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public MonitoredItemCreateResult[] getResults() {
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


    public static void encode(CreateMonitoredItemsResponse createMonitoredItemsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", createMonitoredItemsResponse._responseHeader);
        encoder.encodeArray("Results", createMonitoredItemsResponse._results, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", createMonitoredItemsResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static CreateMonitoredItemsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        MonitoredItemCreateResult[] _results = decoder.decodeArray("Results", decoder::decodeSerializable, MonitoredItemCreateResult.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new CreateMonitoredItemsResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(CreateMonitoredItemsResponse::encode, CreateMonitoredItemsResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CreateMonitoredItemsResponse::decode, CreateMonitoredItemsResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
