package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class TransferResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.TransferResult;
    public static final NodeId BinaryEncodingId = Identifiers.TransferResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TransferResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final Long[] _availableSequenceNumbers;

    public TransferResult(StatusCode _statusCode, Long[] _availableSequenceNumbers) {
        this._statusCode = _statusCode;
        this._availableSequenceNumbers = _availableSequenceNumbers;
    }

    public StatusCode getStatusCode() { return _statusCode; }

    public Long[] getAvailableSequenceNumbers() { return _availableSequenceNumbers; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(TransferResult transferResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", transferResult._statusCode);
        encoder.encodeArray("AvailableSequenceNumbers", transferResult._availableSequenceNumbers, encoder::encodeUInt32);
    }

    public static TransferResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        Long[] _availableSequenceNumbers = decoder.decodeArray("AvailableSequenceNumbers", decoder::decodeUInt32, Long.class);

        return new TransferResult(_statusCode, _availableSequenceNumbers);
    }

    static {
        DelegateRegistry.registerEncoder(TransferResult::encode, TransferResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TransferResult::decode, TransferResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
