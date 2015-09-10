package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("CancelResponse")
public class CancelResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.CancelResponse;
    public static final NodeId BinaryEncodingId = Identifiers.CancelResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CancelResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final UInteger _cancelCount;

    public CancelResponse() {
        this._responseHeader = null;
        this._cancelCount = null;
    }

    public CancelResponse(ResponseHeader _responseHeader, UInteger _cancelCount) {
        this._responseHeader = _responseHeader;
        this._cancelCount = _cancelCount;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public UInteger getCancelCount() {
        return _cancelCount;
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


    public static void encode(CancelResponse cancelResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", cancelResponse._responseHeader != null ? cancelResponse._responseHeader : new ResponseHeader());
        encoder.encodeUInt32("CancelCount", cancelResponse._cancelCount);
    }

    public static CancelResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        UInteger _cancelCount = decoder.decodeUInt32("CancelCount");

        return new CancelResponse(_responseHeader, _cancelCount);
    }

    static {
        DelegateRegistry.registerEncoder(CancelResponse::encode, CancelResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CancelResponse::decode, CancelResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
