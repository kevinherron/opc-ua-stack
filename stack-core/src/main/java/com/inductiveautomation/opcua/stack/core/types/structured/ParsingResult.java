package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("ParsingResult")
public class ParsingResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.ParsingResult;
    public static final NodeId BinaryEncodingId = Identifiers.ParsingResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ParsingResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final StatusCode[] _dataStatusCodes;
    protected final DiagnosticInfo[] _dataDiagnosticInfos;

    public ParsingResult() {
        this._statusCode = null;
        this._dataStatusCodes = null;
        this._dataDiagnosticInfos = null;
    }

    public ParsingResult(StatusCode _statusCode, StatusCode[] _dataStatusCodes, DiagnosticInfo[] _dataDiagnosticInfos) {
        this._statusCode = _statusCode;
        this._dataStatusCodes = _dataStatusCodes;
        this._dataDiagnosticInfos = _dataDiagnosticInfos;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public StatusCode[] getDataStatusCodes() {
        return _dataStatusCodes;
    }

    public DiagnosticInfo[] getDataDiagnosticInfos() {
        return _dataDiagnosticInfos;
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


    public static void encode(ParsingResult parsingResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", parsingResult._statusCode);
        encoder.encodeArray("DataStatusCodes", parsingResult._dataStatusCodes, encoder::encodeStatusCode);
        encoder.encodeArray("DataDiagnosticInfos", parsingResult._dataDiagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static ParsingResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        StatusCode[] _dataStatusCodes = decoder.decodeArray("DataStatusCodes", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _dataDiagnosticInfos = decoder.decodeArray("DataDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new ParsingResult(_statusCode, _dataStatusCodes, _dataDiagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(ParsingResult::encode, ParsingResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ParsingResult::decode, ParsingResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
