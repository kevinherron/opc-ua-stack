package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

public class ContentFilterElementResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.ContentFilterElementResult;
    public static final NodeId BinaryEncodingId = Identifiers.ContentFilterElementResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ContentFilterElementResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final StatusCode[] _operandStatusCodes;
    protected final DiagnosticInfo[] _operandDiagnosticInfos;

    public ContentFilterElementResult(StatusCode _statusCode, StatusCode[] _operandStatusCodes, DiagnosticInfo[] _operandDiagnosticInfos) {
        this._statusCode = _statusCode;
        this._operandStatusCodes = _operandStatusCodes;
        this._operandDiagnosticInfos = _operandDiagnosticInfos;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public StatusCode[] getOperandStatusCodes() {
        return _operandStatusCodes;
    }

    public DiagnosticInfo[] getOperandDiagnosticInfos() {
        return _operandDiagnosticInfos;
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


    public static void encode(ContentFilterElementResult contentFilterElementResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", contentFilterElementResult._statusCode);
        encoder.encodeArray("OperandStatusCodes", contentFilterElementResult._operandStatusCodes, encoder::encodeStatusCode);
        encoder.encodeArray("OperandDiagnosticInfos", contentFilterElementResult._operandDiagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static ContentFilterElementResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        StatusCode[] _operandStatusCodes = decoder.decodeArray("OperandStatusCodes", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _operandDiagnosticInfos = decoder.decodeArray("OperandDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new ContentFilterElementResult(_statusCode, _operandStatusCodes, _operandDiagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(ContentFilterElementResult::encode, ContentFilterElementResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ContentFilterElementResult::decode, ContentFilterElementResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
