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

@UaDataType("TranslateBrowsePathsToNodeIdsRequest")
public class TranslateBrowsePathsToNodeIdsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.TranslateBrowsePathsToNodeIdsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.TranslateBrowsePathsToNodeIdsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TranslateBrowsePathsToNodeIdsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final BrowsePath[] _browsePaths;

    public TranslateBrowsePathsToNodeIdsRequest() {
        this._requestHeader = null;
        this._browsePaths = null;
    }

    public TranslateBrowsePathsToNodeIdsRequest(RequestHeader _requestHeader, BrowsePath[] _browsePaths) {
        this._requestHeader = _requestHeader;
        this._browsePaths = _browsePaths;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public BrowsePath[] getBrowsePaths() {
        return _browsePaths;
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


    public static void encode(TranslateBrowsePathsToNodeIdsRequest translateBrowsePathsToNodeIdsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", translateBrowsePathsToNodeIdsRequest._requestHeader != null ? translateBrowsePathsToNodeIdsRequest._requestHeader : new RequestHeader());
        encoder.encodeArray("BrowsePaths", translateBrowsePathsToNodeIdsRequest._browsePaths, encoder::encodeSerializable);
    }

    public static TranslateBrowsePathsToNodeIdsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        BrowsePath[] _browsePaths = decoder.decodeArray("BrowsePaths", decoder::decodeSerializable, BrowsePath.class);

        return new TranslateBrowsePathsToNodeIdsRequest(_requestHeader, _browsePaths);
    }

    static {
        DelegateRegistry.registerEncoder(TranslateBrowsePathsToNodeIdsRequest::encode, TranslateBrowsePathsToNodeIdsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TranslateBrowsePathsToNodeIdsRequest::decode, TranslateBrowsePathsToNodeIdsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
