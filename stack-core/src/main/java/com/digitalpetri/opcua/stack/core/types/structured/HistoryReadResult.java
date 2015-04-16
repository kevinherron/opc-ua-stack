package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("HistoryReadResult")
public class HistoryReadResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryReadResult;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryReadResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryReadResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final ByteString _continuationPoint;
    protected final ExtensionObject _historyData;

    public HistoryReadResult() {
        this._statusCode = null;
        this._continuationPoint = null;
        this._historyData = null;
    }

    public HistoryReadResult(StatusCode _statusCode, ByteString _continuationPoint, ExtensionObject _historyData) {
        this._statusCode = _statusCode;
        this._continuationPoint = _continuationPoint;
        this._historyData = _historyData;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public ByteString getContinuationPoint() {
        return _continuationPoint;
    }

    public ExtensionObject getHistoryData() {
        return _historyData;
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


    public static void encode(HistoryReadResult historyReadResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", historyReadResult._statusCode);
        encoder.encodeByteString("ContinuationPoint", historyReadResult._continuationPoint);
        encoder.encodeExtensionObject("HistoryData", historyReadResult._historyData);
    }

    public static HistoryReadResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        ByteString _continuationPoint = decoder.decodeByteString("ContinuationPoint");
        ExtensionObject _historyData = decoder.decodeExtensionObject("HistoryData");

        return new HistoryReadResult(_statusCode, _continuationPoint, _historyData);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryReadResult::encode, HistoryReadResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryReadResult::decode, HistoryReadResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
