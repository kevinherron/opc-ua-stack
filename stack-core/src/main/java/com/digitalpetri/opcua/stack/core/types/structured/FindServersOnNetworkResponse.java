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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("FindServersOnNetworkResponse")
public class FindServersOnNetworkResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.FindServersOnNetworkResponse;
    public static final NodeId BinaryEncodingId = Identifiers.FindServersOnNetworkResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.FindServersOnNetworkResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final DateTime _lastCounterResetTime;
    protected final ServerOnNetwork[] _servers;

    public FindServersOnNetworkResponse() {
        this._responseHeader = null;
        this._lastCounterResetTime = null;
        this._servers = null;
    }

    public FindServersOnNetworkResponse(ResponseHeader _responseHeader, DateTime _lastCounterResetTime, ServerOnNetwork[] _servers) {
        this._responseHeader = _responseHeader;
        this._lastCounterResetTime = _lastCounterResetTime;
        this._servers = _servers;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public DateTime getLastCounterResetTime() { return _lastCounterResetTime; }

    public ServerOnNetwork[] getServers() { return _servers; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(FindServersOnNetworkResponse findServersOnNetworkResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", findServersOnNetworkResponse._responseHeader != null ? findServersOnNetworkResponse._responseHeader : new ResponseHeader());
        encoder.encodeDateTime("LastCounterResetTime", findServersOnNetworkResponse._lastCounterResetTime);
        encoder.encodeArray("Servers", findServersOnNetworkResponse._servers, encoder::encodeSerializable);
    }

    public static FindServersOnNetworkResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        DateTime _lastCounterResetTime = decoder.decodeDateTime("LastCounterResetTime");
        ServerOnNetwork[] _servers = decoder.decodeArray("Servers", decoder::decodeSerializable, ServerOnNetwork.class);

        return new FindServersOnNetworkResponse(_responseHeader, _lastCounterResetTime, _servers);
    }

    static {
        DelegateRegistry.registerEncoder(FindServersOnNetworkResponse::encode, FindServersOnNetworkResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(FindServersOnNetworkResponse::decode, FindServersOnNetworkResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
