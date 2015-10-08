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
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

@UaDataType("TestStackResponse")
public class TestStackResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.TestStackResponse;
    public static final NodeId BinaryEncodingId = Identifiers.TestStackResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TestStackResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final Variant _output;

    public TestStackResponse() {
        this._responseHeader = null;
        this._output = null;
    }

    public TestStackResponse(ResponseHeader _responseHeader, Variant _output) {
        this._responseHeader = _responseHeader;
        this._output = _output;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public Variant getOutput() {
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


    public static void encode(TestStackResponse testStackResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", testStackResponse._responseHeader != null ? testStackResponse._responseHeader : new ResponseHeader());
        encoder.encodeVariant("Output", testStackResponse._output);
    }

    public static TestStackResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        Variant _output = decoder.decodeVariant("Output");

        return new TestStackResponse(_responseHeader, _output);
    }

    static {
        DelegateRegistry.registerEncoder(TestStackResponse::encode, TestStackResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TestStackResponse::decode, TestStackResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
