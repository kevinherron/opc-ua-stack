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

@UaDataType("FindServersResponse")
public class FindServersResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.FindServersResponse;
    public static final NodeId BinaryEncodingId = Identifiers.FindServersResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.FindServersResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final ApplicationDescription[] _servers;

    public FindServersResponse() {
        this._responseHeader = null;
        this._servers = null;
    }

    public FindServersResponse(ResponseHeader _responseHeader, ApplicationDescription[] _servers) {
        this._responseHeader = _responseHeader;
        this._servers = _servers;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public ApplicationDescription[] getServers() {
        return _servers;
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


    public static void encode(FindServersResponse findServersResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", findServersResponse._responseHeader != null ? findServersResponse._responseHeader : new ResponseHeader());
        encoder.encodeArray("Servers", findServersResponse._servers, encoder::encodeSerializable);
    }

    public static FindServersResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        ApplicationDescription[] _servers = decoder.decodeArray("Servers", decoder::decodeSerializable, ApplicationDescription.class);

        return new FindServersResponse(_responseHeader, _servers);
    }

    static {
        DelegateRegistry.registerEncoder(FindServersResponse::encode, FindServersResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(FindServersResponse::decode, FindServersResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
