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

@UaDataType("RegisterNodesRequest")
public class RegisterNodesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.RegisterNodesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.RegisterNodesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RegisterNodesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final NodeId[] _nodesToRegister;

    public RegisterNodesRequest() {
        this._requestHeader = null;
        this._nodesToRegister = null;
    }

    public RegisterNodesRequest(RequestHeader _requestHeader, NodeId[] _nodesToRegister) {
        this._requestHeader = _requestHeader;
        this._nodesToRegister = _nodesToRegister;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public NodeId[] getNodesToRegister() {
        return _nodesToRegister;
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


    public static void encode(RegisterNodesRequest registerNodesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", registerNodesRequest._requestHeader != null ? registerNodesRequest._requestHeader : new RequestHeader());
        encoder.encodeArray("NodesToRegister", registerNodesRequest._nodesToRegister, encoder::encodeNodeId);
    }

    public static RegisterNodesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        NodeId[] _nodesToRegister = decoder.decodeArray("NodesToRegister", decoder::decodeNodeId, NodeId.class);

        return new RegisterNodesRequest(_requestHeader, _nodesToRegister);
    }

    static {
        DelegateRegistry.registerEncoder(RegisterNodesRequest::encode, RegisterNodesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RegisterNodesRequest::decode, RegisterNodesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
