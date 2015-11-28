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
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("RegisterServer2Request")
public class RegisterServer2Request implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RegisterServer2Request;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterServer2Request_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterServer2Request_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final RegisteredServer _server;
    protected final ExtensionObject[] _discoveryConfiguration;

    public RegisterServer2Request() {
        this._requestHeader = null;
        this._server = null;
        this._discoveryConfiguration = null;
    }

    public RegisterServer2Request(RequestHeader _requestHeader, RegisteredServer _server, ExtensionObject[] _discoveryConfiguration) {
        this._requestHeader = _requestHeader;
        this._server = _server;
        this._discoveryConfiguration = _discoveryConfiguration;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public RegisteredServer getServer() { return _server; }

    public ExtensionObject[] getDiscoveryConfiguration() { return _discoveryConfiguration; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RegisterServer2Request registerServer2Request, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", registerServer2Request._requestHeader != null ? registerServer2Request._requestHeader : new RequestHeader());
        encoder.encodeSerializable("Server", registerServer2Request._server != null ? registerServer2Request._server : new RegisteredServer());
        encoder.encodeArray("DiscoveryConfiguration", registerServer2Request._discoveryConfiguration, encoder::encodeExtensionObject);
    }

    public static RegisterServer2Request decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        RegisteredServer _server = decoder.decodeSerializable("Server", RegisteredServer.class);
        ExtensionObject[] _discoveryConfiguration = decoder.decodeArray("DiscoveryConfiguration", decoder::decodeExtensionObject, ExtensionObject.class);

        return new RegisterServer2Request(_requestHeader, _server, _discoveryConfiguration);
    }

    static {
        DelegateRegistry.registerEncoder(RegisterServer2Request::encode, RegisterServer2Request.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RegisterServer2Request::decode, RegisterServer2Request.class, BinaryEncodingId, XmlEncodingId);
    }

}
