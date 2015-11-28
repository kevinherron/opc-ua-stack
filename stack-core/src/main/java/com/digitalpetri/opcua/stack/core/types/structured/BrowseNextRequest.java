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

@UaDataType("BrowseNextRequest")
public class BrowseNextRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.BrowseNextRequest;
    public static final NodeId BinaryEncodingId = Identifiers.BrowseNextRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowseNextRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final Boolean _releaseContinuationPoints;
    protected final ByteString[] _continuationPoints;

    public BrowseNextRequest() {
        this._requestHeader = null;
        this._releaseContinuationPoints = null;
        this._continuationPoints = null;
    }

    public BrowseNextRequest(RequestHeader _requestHeader, Boolean _releaseContinuationPoints, ByteString[] _continuationPoints) {
        this._requestHeader = _requestHeader;
        this._releaseContinuationPoints = _releaseContinuationPoints;
        this._continuationPoints = _continuationPoints;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public Boolean getReleaseContinuationPoints() { return _releaseContinuationPoints; }

    public ByteString[] getContinuationPoints() { return _continuationPoints; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(BrowseNextRequest browseNextRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", browseNextRequest._requestHeader != null ? browseNextRequest._requestHeader : new RequestHeader());
        encoder.encodeBoolean("ReleaseContinuationPoints", browseNextRequest._releaseContinuationPoints);
        encoder.encodeArray("ContinuationPoints", browseNextRequest._continuationPoints, encoder::encodeByteString);
    }

    public static BrowseNextRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Boolean _releaseContinuationPoints = decoder.decodeBoolean("ReleaseContinuationPoints");
        ByteString[] _continuationPoints = decoder.decodeArray("ContinuationPoints", decoder::decodeByteString, ByteString.class);

        return new BrowseNextRequest(_requestHeader, _releaseContinuationPoints, _continuationPoints);
    }

    static {
        DelegateRegistry.registerEncoder(BrowseNextRequest::encode, BrowseNextRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowseNextRequest::decode, BrowseNextRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
