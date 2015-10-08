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
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;

@UaDataType("OpenSecureChannelRequest")
public class OpenSecureChannelRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.OpenSecureChannelRequest;
    public static final NodeId BinaryEncodingId = Identifiers.OpenSecureChannelRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.OpenSecureChannelRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _clientProtocolVersion;
    protected final SecurityTokenRequestType _requestType;
    protected final MessageSecurityMode _securityMode;
    protected final ByteString _clientNonce;
    protected final UInteger _requestedLifetime;

    public OpenSecureChannelRequest() {
        this._requestHeader = null;
        this._clientProtocolVersion = null;
        this._requestType = null;
        this._securityMode = null;
        this._clientNonce = null;
        this._requestedLifetime = null;
    }

    public OpenSecureChannelRequest(RequestHeader _requestHeader, UInteger _clientProtocolVersion, SecurityTokenRequestType _requestType, MessageSecurityMode _securityMode, ByteString _clientNonce, UInteger _requestedLifetime) {
        this._requestHeader = _requestHeader;
        this._clientProtocolVersion = _clientProtocolVersion;
        this._requestType = _requestType;
        this._securityMode = _securityMode;
        this._clientNonce = _clientNonce;
        this._requestedLifetime = _requestedLifetime;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public UInteger getClientProtocolVersion() {
        return _clientProtocolVersion;
    }

    public SecurityTokenRequestType getRequestType() {
        return _requestType;
    }

    public MessageSecurityMode getSecurityMode() {
        return _securityMode;
    }

    public ByteString getClientNonce() {
        return _clientNonce;
    }

    public UInteger getRequestedLifetime() {
        return _requestedLifetime;
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


    public static void encode(OpenSecureChannelRequest openSecureChannelRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", openSecureChannelRequest._requestHeader != null ? openSecureChannelRequest._requestHeader : new RequestHeader());
        encoder.encodeUInt32("ClientProtocolVersion", openSecureChannelRequest._clientProtocolVersion);
        encoder.encodeEnumeration("RequestType", openSecureChannelRequest._requestType);
        encoder.encodeEnumeration("SecurityMode", openSecureChannelRequest._securityMode);
        encoder.encodeByteString("ClientNonce", openSecureChannelRequest._clientNonce);
        encoder.encodeUInt32("RequestedLifetime", openSecureChannelRequest._requestedLifetime);
    }

    public static OpenSecureChannelRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _clientProtocolVersion = decoder.decodeUInt32("ClientProtocolVersion");
        SecurityTokenRequestType _requestType = decoder.decodeEnumeration("RequestType", SecurityTokenRequestType.class);
        MessageSecurityMode _securityMode = decoder.decodeEnumeration("SecurityMode", MessageSecurityMode.class);
        ByteString _clientNonce = decoder.decodeByteString("ClientNonce");
        UInteger _requestedLifetime = decoder.decodeUInt32("RequestedLifetime");

        return new OpenSecureChannelRequest(_requestHeader, _clientProtocolVersion, _requestType, _securityMode, _clientNonce, _requestedLifetime);
    }

    static {
        DelegateRegistry.registerEncoder(OpenSecureChannelRequest::encode, OpenSecureChannelRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(OpenSecureChannelRequest::decode, OpenSecureChannelRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
