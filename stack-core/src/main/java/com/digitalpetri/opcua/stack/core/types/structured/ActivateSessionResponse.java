package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class ActivateSessionResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.ActivateSessionResponse;
    public static final NodeId BinaryEncodingId = Identifiers.ActivateSessionResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ActivateSessionResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final ByteString _serverNonce;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public ActivateSessionResponse(ResponseHeader _responseHeader, ByteString _serverNonce, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._serverNonce = _serverNonce;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public ByteString getServerNonce() { return _serverNonce; }

    public StatusCode[] getResults() { return _results; }

    public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ActivateSessionResponse activateSessionResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", activateSessionResponse._responseHeader);
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
