package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

@UaDataType("TranslateBrowsePathsToNodeIdsResponse")
public class TranslateBrowsePathsToNodeIdsResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.TranslateBrowsePathsToNodeIdsResponse;
    public static final NodeId BinaryEncodingId = Identifiers.TranslateBrowsePathsToNodeIdsResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TranslateBrowsePathsToNodeIdsResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final BrowsePathResult[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public TranslateBrowsePathsToNodeIdsResponse() {
        this._responseHeader = null;
        this._results = null;
        this._diagnosticInfos = null;
    }

    public TranslateBrowsePathsToNodeIdsResponse(ResponseHeader _responseHeader, BrowsePathResult[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public BrowsePathResult[] getResults() {
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


    public static void encode(TranslateBrowsePathsToNodeIdsResponse translateBrowsePathsToNodeIdsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", translateBrowsePathsToNodeIdsResponse._responseHeader != null ? translateBrowsePathsToNodeIdsResponse._responseHeader : new ResponseHeader());
        encoder.encodeArray("Results", translateBrowsePathsToNodeIdsResponse._results, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", translateBrowsePathsToNodeIdsResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static TranslateBrowsePathsToNodeIdsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        BrowsePathResult[] _results = decoder.decodeArray("Results", decoder::decodeSerializable, BrowsePathResult.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new TranslateBrowsePathsToNodeIdsResponse(_responseHeader, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(TranslateBrowsePathsToNodeIdsResponse::encode, TranslateBrowsePathsToNodeIdsResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TranslateBrowsePathsToNodeIdsResponse::decode, TranslateBrowsePathsToNodeIdsResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
