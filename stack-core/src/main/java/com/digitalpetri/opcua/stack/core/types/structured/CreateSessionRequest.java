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

@UaDataType("CreateSessionRequest")
public class CreateSessionRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.CreateSessionRequest;
    public static final NodeId BinaryEncodingId = Identifiers.CreateSessionRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CreateSessionRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final ApplicationDescription _clientDescription;
    protected final String _serverUri;
    protected final String _endpointUrl;
    protected final String _sessionName;
    protected final ByteString _clientNonce;
    protected final ByteString _clientCertificate;
    protected final Double _requestedSessionTimeout;
    protected final UInteger _maxResponseMessageSize;

    public CreateSessionRequest() {
        this._requestHeader = null;
        this._clientDescription = null;
        this._serverUri = null;
        this._endpointUrl = null;
        this._sessionName = null;
        this._clientNonce = null;
        this._clientCertificate = null;
        this._requestedSessionTimeout = null;
        this._maxResponseMessageSize = null;
    }

    public CreateSessionRequest(RequestHeader _requestHeader, ApplicationDescription _clientDescription, String _serverUri, String _endpointUrl, String _sessionName, ByteString _clientNonce, ByteString _clientCertificate, Double _requestedSessionTimeout, UInteger _maxResponseMessageSize) {
        this._requestHeader = _requestHeader;
        this._clientDescription = _clientDescription;
        this._serverUri = _serverUri;
        this._endpointUrl = _endpointUrl;
        this._sessionName = _sessionName;
        this._clientNonce = _clientNonce;
        this._clientCertificate = _clientCertificate;
        this._requestedSessionTimeout = _requestedSessionTimeout;
        this._maxResponseMessageSize = _maxResponseMessageSize;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public ApplicationDescription getClientDescription() { return _clientDescription; }

    public String getServerUri() { return _serverUri; }

    public String getEndpointUrl() { return _endpointUrl; }

    public String getSessionName() { return _sessionName; }

    public ByteString getClientNonce() { return _clientNonce; }

    public ByteString getClientCertificate() { return _clientCertificate; }

    public Double getRequestedSessionTimeout() { return _requestedSessionTimeout; }

    public UInteger getMaxResponseMessageSize() { return _maxResponseMessageSize; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CreateSessionRequest createSessionRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", createSessionRequest._requestHeader != null ? createSessionRequest._requestHeader : new RequestHeader());
        encoder.encodeSerializable("ClientDescription", createSessionRequest._clientDescription != null ? createSessionRequest._clientDescription : new ApplicationDescription());
        encoder.encodeString("ServerUri", createSessionRequest._serverUri);
        encoder.encodeString("EndpointUrl", createSessionRequest._endpointUrl);
        encoder.encodeString("SessionName", createSessionRequest._sessionName);
        encoder.encodeByteString("ClientNonce", createSessionRequest._clientNonce);
        encoder.encodeByteString("ClientCertificate", createSessionRequest._clientCertificate);
        encoder.encodeDouble("RequestedSessionTimeout", createSessionRequest._requestedSessionTimeout);
        encoder.encodeUInt32("MaxResponseMessageSize", createSessionRequest._maxResponseMessageSize);
    }

    public static CreateSessionRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        ApplicationDescription _clientDescription = decoder.decodeSerializable("ClientDescription", ApplicationDescription.class);
        String _serverUri = decoder.decodeString("ServerUri");
        String _endpointUrl = decoder.decodeString("EndpointUrl");
        String _sessionName = decoder.decodeString("SessionName");
        ByteString _clientNonce = decoder.decodeByteString("ClientNonce");
        ByteString _clientCertificate = decoder.decodeByteString("ClientCertificate");
        Double _requestedSessionTimeout = decoder.decodeDouble("RequestedSessionTimeout");
        UInteger _maxResponseMessageSize = decoder.decodeUInt32("MaxResponseMessageSize");

        return new CreateSessionRequest(_requestHeader, _clientDescription, _serverUri, _endpointUrl, _sessionName, _clientNonce, _clientCertificate, _requestedSessionTimeout, _maxResponseMessageSize);
    }

    static {
        DelegateRegistry.registerEncoder(CreateSessionRequest::encode, CreateSessionRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CreateSessionRequest::decode, CreateSessionRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
