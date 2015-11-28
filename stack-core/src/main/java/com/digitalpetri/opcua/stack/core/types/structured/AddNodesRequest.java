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

@UaDataType("AddNodesRequest")
public class AddNodesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.AddNodesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.AddNodesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AddNodesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final AddNodesItem[] _nodesToAdd;

    public AddNodesRequest() {
        this._requestHeader = null;
        this._nodesToAdd = null;
    }

    public AddNodesRequest(RequestHeader _requestHeader, AddNodesItem[] _nodesToAdd) {
        this._requestHeader = _requestHeader;
        this._nodesToAdd = _nodesToAdd;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public AddNodesItem[] getNodesToAdd() { return _nodesToAdd; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(AddNodesRequest addNodesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", addNodesRequest._requestHeader != null ? addNodesRequest._requestHeader : new RequestHeader());
        encoder.encodeArray("NodesToAdd", addNodesRequest._nodesToAdd, encoder::encodeSerializable);
    }

    public static AddNodesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        AddNodesItem[] _nodesToAdd = decoder.decodeArray("NodesToAdd", decoder::decodeSerializable, AddNodesItem.class);

        return new AddNodesRequest(_requestHeader, _nodesToAdd);
    }

    static {
        DelegateRegistry.registerEncoder(AddNodesRequest::encode, AddNodesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AddNodesRequest::decode, AddNodesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
