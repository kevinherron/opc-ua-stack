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

@UaDataType("CloseSessionResponse")
public class CloseSessionResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.CloseSessionResponse;
    public static final NodeId BinaryEncodingId = Identifiers.CloseSessionResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CloseSessionResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;

    public CloseSessionResponse() {
        this._responseHeader = null;
    }

    public CloseSessionResponse(ResponseHeader _responseHeader) {
        this._responseHeader = _responseHeader;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
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


    public static void encode(CloseSessionResponse closeSessionResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", closeSessionResponse._responseHeader != null ? closeSessionResponse._responseHeader : new ResponseHeader());
    }

    public static CloseSessionResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);

        return new CloseSessionResponse(_responseHeader);
    }

    static {
        DelegateRegistry.registerEncoder(CloseSessionResponse::encode, CloseSessionResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CloseSessionResponse::decode, CloseSessionResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
