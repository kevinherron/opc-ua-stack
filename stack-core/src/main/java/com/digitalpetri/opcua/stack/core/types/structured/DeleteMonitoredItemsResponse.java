package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class DeleteMonitoredItemsResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.DeleteMonitoredItemsResponse;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteMonitoredItemsResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteMonitoredItemsResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public DeleteMonitoredItemsResponse(ResponseHeader _responseHeader, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
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


    public static void encode(DeleteMonitoredItemsResponse deleteMonitoredItemsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", deleteMonitoredItemsResponse._responseHeader);
        encoder.encodeArray("Results", deleteMonitoredItemsResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", deleteMonitoredItemsResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static DeleteMonitoredItemsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        StatusCode[] _results = decoder.decodeArray("Results", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new DeleteMonitoredItemsResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(DeleteMonitoredItemsResponse::encode, DeleteMonitoredItemsResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DeleteMonitoredItemsResponse::decode, DeleteMonitoredItemsResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
