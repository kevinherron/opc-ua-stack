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
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ServerOnNetwork")
public class ServerOnNetwork implements UaStructure {

    public static final NodeId TypeId = Identifiers.ServerOnNetwork;
    public static final NodeId BinaryEncodingId = Identifiers.ServerOnNetwork_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ServerOnNetwork_Encoding_DefaultXml;

    protected final UInteger _recordId;
    protected final String _serverName;
    protected final String _discoveryUrl;
    protected final String[] _serverCapabilities;

    public ServerOnNetwork() {
        this._recordId = null;
        this._serverName = null;
        this._discoveryUrl = null;
        this._serverCapabilities = null;
    }

    public ServerOnNetwork(UInteger _recordId, String _serverName, String _discoveryUrl, String[] _serverCapabilities) {
        this._recordId = _recordId;
        this._serverName = _serverName;
        this._discoveryUrl = _discoveryUrl;
        this._serverCapabilities = _serverCapabilities;
    }

    public UInteger getRecordId() { return _recordId; }

    public String getServerName() { return _serverName; }

    public String getDiscoveryUrl() { return _discoveryUrl; }

    public String[] getServerCapabilities() { return _serverCapabilities; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ServerOnNetwork serverOnNetwork, UaEncoder encoder) {
        encoder.encodeUInt32("RecordId", serverOnNetwork._recordId);
        encoder.encodeString("ServerName", serverOnNetwork._serverName);
        encoder.encodeString("DiscoveryUrl", serverOnNetwork._discoveryUrl);
        encoder.encodeArray("ServerCapabilities", serverOnNetwork._serverCapabilities, encoder::encodeString);
    }

    public static ServerOnNetwork decode(UaDecoder decoder) {
        UInteger _recordId = decoder.decodeUInt32("RecordId");
        String _serverName = decoder.decodeString("ServerName");
        String _discoveryUrl = decoder.decodeString("DiscoveryUrl");
        String[] _serverCapabilities = decoder.decodeArray("ServerCapabilities", decoder::decodeString, String.class);

        return new ServerOnNetwork(_recordId, _serverName, _discoveryUrl, _serverCapabilities);
    }

    static {
        DelegateRegistry.registerEncoder(ServerOnNetwork::encode, ServerOnNetwork.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ServerOnNetwork::decode, ServerOnNetwork.class, BinaryEncodingId, XmlEncodingId);
    }

}
