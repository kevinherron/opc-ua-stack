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

@UaDataType("AddReferencesRequest")
public class AddReferencesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.AddReferencesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.AddReferencesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AddReferencesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final AddReferencesItem[] _referencesToAdd;

    public AddReferencesRequest() {
        this._requestHeader = null;
        this._referencesToAdd = null;
    }

    public AddReferencesRequest(RequestHeader _requestHeader, AddReferencesItem[] _referencesToAdd) {
        this._requestHeader = _requestHeader;
        this._referencesToAdd = _referencesToAdd;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public AddReferencesItem[] getReferencesToAdd() { return _referencesToAdd; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(AddReferencesRequest addReferencesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", addReferencesRequest._requestHeader != null ? addReferencesRequest._requestHeader : new RequestHeader());
        encoder.encodeArray("ReferencesToAdd", addReferencesRequest._referencesToAdd, encoder::encodeSerializable);
    }

    public static AddReferencesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        AddReferencesItem[] _referencesToAdd = decoder.decodeArray("ReferencesToAdd", decoder::decodeSerializable, AddReferencesItem.class);

        return new AddReferencesRequest(_requestHeader, _referencesToAdd);
    }

    static {
        DelegateRegistry.registerEncoder(AddReferencesRequest::encode, AddReferencesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AddReferencesRequest::decode, AddReferencesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
