package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class TestStackExResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.TestStackExResponse;
    public static final NodeId BinaryEncodingId = Identifiers.TestStackExResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TestStackExResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final CompositeTestType _output;

    public TestStackExResponse(ResponseHeader _responseHeader, CompositeTestType _output) {
        this._responseHeader = _responseHeader;
        this._output = _output;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public CompositeTestType getOutput() { return _output; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(TestStackExResponse testStackExResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", testStackExResponse._responseHeader);
        encoder.encodeSerializable("Output", testStackExResponse._output);
    }

    public static TestStackExResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        CompositeTestType _output = decoder.decodeSerializable("Output", CompositeTestType.class);

        return new TestStackExResponse(_responseHeader, _output);
    }

    static {
        DelegateRegistry.registerEncoder(TestStackExResponse::encode, TestStackExResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TestStackExResponse::decode, TestStackExResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
