package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public class TestStackExRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.TestStackExRequest;
    public static final NodeId BinaryEncodingId = Identifiers.TestStackExRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TestStackExRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _testId;
    protected final Integer _iteration;
    protected final CompositeTestType _input;

    public TestStackExRequest(RequestHeader _requestHeader, UInteger _testId, Integer _iteration, CompositeTestType _input) {
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

    public CompositeTestType getInput() {
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


    public static void encode(TestStackExRequest testStackExRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", testStackExRequest._requestHeader);
        encoder.encodeUInt32("TestId", testStackExRequest._testId);
        encoder.encodeInt32("Iteration", testStackExRequest._iteration);
        encoder.encodeSerializable("Input", testStackExRequest._input);
    }

    public static TestStackExRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _testId = decoder.decodeUInt32("TestId");
        Integer _iteration = decoder.decodeInt32("Iteration");
        CompositeTestType _input = decoder.decodeSerializable("Input", CompositeTestType.class);

        return new TestStackExRequest(_requestHeader, _testId, _iteration, _input);
    }

    static {
        DelegateRegistry.registerEncoder(TestStackExRequest::encode, TestStackExRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TestStackExRequest::decode, TestStackExRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
