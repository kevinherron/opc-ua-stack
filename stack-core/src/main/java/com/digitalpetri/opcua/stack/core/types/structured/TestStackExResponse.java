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
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("TestStackExResponse")
public class TestStackExResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.TestStackExResponse;
    public static final NodeId BinaryEncodingId = Identifiers.TestStackExResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TestStackExResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final CompositeTestType _output;

    public TestStackExResponse() {
        this._responseHeader = null;
        this._output = null;
    }

    public TestStackExResponse(ResponseHeader _responseHeader, CompositeTestType _output) {
        this._responseHeader = _responseHeader;
        this._output = _output;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public CompositeTestType getOutput() {
        return _output;
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


    public static void encode(TestStackExResponse testStackExResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", testStackExResponse._responseHeader != null ? testStackExResponse._responseHeader : new ResponseHeader());
        encoder.encodeSerializable("Output", testStackExResponse._output != null ? testStackExResponse._output : new CompositeTestType());
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
