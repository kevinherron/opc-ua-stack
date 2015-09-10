package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("WriteRequest")
public class WriteRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.WriteRequest;
    public static final NodeId BinaryEncodingId = Identifiers.WriteRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.WriteRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final WriteValue[] _nodesToWrite;

    public WriteRequest() {
        this._requestHeader = null;
        this._nodesToWrite = null;
    }

    public WriteRequest(RequestHeader _requestHeader, WriteValue[] _nodesToWrite) {
        this._requestHeader = _requestHeader;
        this._nodesToWrite = _nodesToWrite;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public WriteValue[] getNodesToWrite() {
        return _nodesToWrite;
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


    public static void encode(WriteRequest writeRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", writeRequest._requestHeader != null ? writeRequest._requestHeader : new RequestHeader());
        encoder.encodeArray("NodesToWrite", writeRequest._nodesToWrite, encoder::encodeSerializable);
    }

    public static WriteRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        WriteValue[] _nodesToWrite = decoder.decodeArray("NodesToWrite", decoder::decodeSerializable, WriteValue.class);

        return new WriteRequest(_requestHeader, _nodesToWrite);
    }

    static {
        DelegateRegistry.registerEncoder(WriteRequest::encode, WriteRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(WriteRequest::decode, WriteRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
