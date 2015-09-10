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

@UaDataType("DeleteReferencesResponse")
public class DeleteReferencesResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.DeleteReferencesResponse;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteReferencesResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteReferencesResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public DeleteReferencesResponse() {
        this._responseHeader = null;
        this._results = null;
        this._diagnosticInfos = null;
    }

    public DeleteReferencesResponse(ResponseHeader _responseHeader, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
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


    public static void encode(DeleteReferencesResponse deleteReferencesResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", deleteReferencesResponse._responseHeader != null ? deleteReferencesResponse._responseHeader : new ResponseHeader());
        encoder.encodeArray("Results", deleteReferencesResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", deleteReferencesResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static DeleteReferencesResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        StatusCode[] _results = decoder.decodeArray("Results", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new DeleteReferencesResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(DeleteReferencesResponse::encode, DeleteReferencesResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DeleteReferencesResponse::decode, DeleteReferencesResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
