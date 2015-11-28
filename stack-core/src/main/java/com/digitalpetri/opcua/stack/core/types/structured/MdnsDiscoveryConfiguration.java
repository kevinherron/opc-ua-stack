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
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("MdnsDiscoveryConfiguration")
public class MdnsDiscoveryConfiguration extends DiscoveryConfiguration {

    public static final NodeId TypeId = Identifiers.MdnsDiscoveryConfiguration;
    public static final NodeId BinaryEncodingId = Identifiers.MdnsDiscoveryConfiguration_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MdnsDiscoveryConfiguration_Encoding_DefaultXml;

    protected final String _mdnsServerName;
    protected final String[] _serverCapabilities;

    public MdnsDiscoveryConfiguration() {
        super();
        this._mdnsServerName = null;
        this._serverCapabilities = null;
    }

    public MdnsDiscoveryConfiguration(String _mdnsServerName, String[] _serverCapabilities) {
        super();
        this._mdnsServerName = _mdnsServerName;
        this._serverCapabilities = _serverCapabilities;
    }

    public String getMdnsServerName() { return _mdnsServerName; }

    public String[] getServerCapabilities() { return _serverCapabilities; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(MdnsDiscoveryConfiguration mdnsDiscoveryConfiguration, UaEncoder encoder) {
        encoder.encodeString("MdnsServerName", mdnsDiscoveryConfiguration._mdnsServerName);
        encoder.encodeArray("ServerCapabilities", mdnsDiscoveryConfiguration._serverCapabilities, encoder::encodeString);
    }

    public static MdnsDiscoveryConfiguration decode(UaDecoder decoder) {
        String _mdnsServerName = decoder.decodeString("MdnsServerName");
        String[] _serverCapabilities = decoder.decodeArray("ServerCapabilities", decoder::decodeString, String.class);

        return new MdnsDiscoveryConfiguration(_mdnsServerName, _serverCapabilities);
    }

    static {
        DelegateRegistry.registerEncoder(MdnsDiscoveryConfiguration::encode, MdnsDiscoveryConfiguration.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MdnsDiscoveryConfiguration::decode, MdnsDiscoveryConfiguration.class, BinaryEncodingId, XmlEncodingId);
    }

}
