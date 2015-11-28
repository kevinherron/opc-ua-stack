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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("FindServersOnNetworkRequest")
public class FindServersOnNetworkRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.FindServersOnNetworkRequest;
    public static final NodeId BinaryEncodingId = Identifiers.FindServersOnNetworkRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.FindServersOnNetworkRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _startingRecordId;
    protected final UInteger _maxRecordsToReturn;
    protected final String[] _serverCapabilityFilter;

    public FindServersOnNetworkRequest() {
        this._requestHeader = null;
        this._startingRecordId = null;
        this._maxRecordsToReturn = null;
        this._serverCapabilityFilter = null;
    }

    public FindServersOnNetworkRequest(RequestHeader _requestHeader, UInteger _startingRecordId, UInteger _maxRecordsToReturn, String[] _serverCapabilityFilter) {
        this._requestHeader = _requestHeader;
        this._startingRecordId = _startingRecordId;
        this._maxRecordsToReturn = _maxRecordsToReturn;
        this._serverCapabilityFilter = _serverCapabilityFilter;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public UInteger getStartingRecordId() { return _startingRecordId; }

    public UInteger getMaxRecordsToReturn() { return _maxRecordsToReturn; }

    public String[] getServerCapabilityFilter() { return _serverCapabilityFilter; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(FindServersOnNetworkRequest findServersOnNetworkRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", findServersOnNetworkRequest._requestHeader != null ? findServersOnNetworkRequest._requestHeader : new RequestHeader());
        encoder.encodeUInt32("StartingRecordId", findServersOnNetworkRequest._startingRecordId);
        encoder.encodeUInt32("MaxRecordsToReturn", findServersOnNetworkRequest._maxRecordsToReturn);
        encoder.encodeArray("ServerCapabilityFilter", findServersOnNetworkRequest._serverCapabilityFilter, encoder::encodeString);
    }

    public static FindServersOnNetworkRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _startingRecordId = decoder.decodeUInt32("StartingRecordId");
        UInteger _maxRecordsToReturn = decoder.decodeUInt32("MaxRecordsToReturn");
        String[] _serverCapabilityFilter = decoder.decodeArray("ServerCapabilityFilter", decoder::decodeString, String.class);

        return new FindServersOnNetworkRequest(_requestHeader, _startingRecordId, _maxRecordsToReturn, _serverCapabilityFilter);
    }

    static {
        DelegateRegistry.registerEncoder(FindServersOnNetworkRequest::encode, FindServersOnNetworkRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(FindServersOnNetworkRequest::decode, FindServersOnNetworkRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
