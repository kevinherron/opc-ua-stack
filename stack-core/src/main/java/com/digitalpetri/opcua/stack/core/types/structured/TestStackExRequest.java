/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("TestStackExRequest")
public class TestStackExRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.TestStackExRequest;
    public static final NodeId BinaryEncodingId = Identifiers.TestStackExRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TestStackExRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _testId;
    protected final Integer _iteration;
    protected final CompositeTestType _input;

    public TestStackExRequest() {
        this._requestHeader = null;
        this._testId = null;
        this._iteration = null;
        this._input = null;
    }

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
        encoder.encodeSerializable("RequestHeader", testStackExRequest._requestHeader != null ? testStackExRequest._requestHeader : new RequestHeader());
        encoder.encodeUInt32("TestId", testStackExRequest._testId);
        encoder.encodeInt32("Iteration", testStackExRequest._iteration);
        encoder.encodeSerializable("Input", testStackExRequest._input != null ? testStackExRequest._input : new CompositeTestType());
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
