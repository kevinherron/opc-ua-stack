package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public class TestStackRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.TestStackRequest;
    public static final NodeId BinaryEncodingId = Identifiers.TestStackRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TestStackRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _testId;
    protected final Integer _iteration;
    protected final Variant _input;

    public TestStackRequest(RequestHeader _requestHeader, UInteger _testId, Integer _iteration, Variant _input) {
        this._requestHeader = _requestHeader;
        this._testId = _testId;
        this._iteration = _iteration;
        this._input = _input;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public UInteger getTestId() {
        return _testId;
    }

    public Integer getIteration() {
        return _iteration;
    }

    public Variant getInput() {
        return _input;
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


    public static void encode(TestStackRequest testStackRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", testStackRequest._requestHeader);
        encoder.encodeUInt32("TestId", testStackRequest._testId);
        encoder.encodeInt32("Iteration", testStackRequest._iteration);
        encoder.encodeVariant("Input", testStackRequest._input);
    }

    public static TestStackRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _testId = decoder.decodeUInt32("TestId");
        Integer _iteration = decoder.decodeInt32("Iteration");
        Variant _input = decoder.decodeVariant("Input");

        return new TestStackRequest(_requestHeader, _testId, _iteration, _input);
    }

    static {
        DelegateRegistry.registerEncoder(TestStackRequest::encode, TestStackRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TestStackRequest::decode, TestStackRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
