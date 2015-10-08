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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;

@UaDataType("RedundantServerDataType")
public class RedundantServerDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.RedundantServerDataType;
    public static final NodeId BinaryEncodingId = Identifiers.RedundantServerDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RedundantServerDataType_Encoding_DefaultXml;

    protected final String _serverId;
    protected final UByte _serviceLevel;
    protected final ServerState _serverState;

    public RedundantServerDataType() {
        this._serverId = null;
        this._serviceLevel = null;
        this._serverState = null;
    }

    public RedundantServerDataType(String _serverId, UByte _serviceLevel, ServerState _serverState) {
        this._serverId = _serverId;
        this._serviceLevel = _serviceLevel;
        this._serverState = _serverState;
    }

    public String getServerId() {
        return _serverId;
    }

    public UByte getServiceLevel() {
        return _serviceLevel;
    }

    public ServerState getServerState() {
        return _serverState;
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


    public static void encode(RedundantServerDataType redundantServerDataType, UaEncoder encoder) {
        encoder.encodeString("ServerId", redundantServerDataType._serverId);
        encoder.encodeByte("ServiceLevel", redundantServerDataType._serviceLevel);
        encoder.encodeEnumeration("ServerState", redundantServerDataType._serverState);
    }

    public static RedundantServerDataType decode(UaDecoder decoder) {
        String _serverId = decoder.decodeString("ServerId");
        UByte _serviceLevel = decoder.decodeByte("ServiceLevel");
        ServerState _serverState = decoder.decodeEnumeration("ServerState", ServerState.class);

        return new RedundantServerDataType(_serverId, _serviceLevel, _serverState);
    }

    static {
        DelegateRegistry.registerEncoder(RedundantServerDataType::encode, RedundantServerDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RedundantServerDataType::decode, RedundantServerDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
