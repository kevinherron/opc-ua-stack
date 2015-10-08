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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;

@UaDataType("ServerStatusDataType")
public class ServerStatusDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.ServerStatusDataType;
    public static final NodeId BinaryEncodingId = Identifiers.ServerStatusDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ServerStatusDataType_Encoding_DefaultXml;

    protected final DateTime _startTime;
    protected final DateTime _currentTime;
    protected final ServerState _state;
    protected final BuildInfo _buildInfo;
    protected final UInteger _secondsTillShutdown;
    protected final LocalizedText _shutdownReason;

    public ServerStatusDataType() {
        this._startTime = null;
        this._currentTime = null;
        this._state = null;
        this._buildInfo = null;
        this._secondsTillShutdown = null;
        this._shutdownReason = null;
    }

    public ServerStatusDataType(DateTime _startTime, DateTime _currentTime, ServerState _state, BuildInfo _buildInfo, UInteger _secondsTillShutdown, LocalizedText _shutdownReason) {
        this._startTime = _startTime;
        this._currentTime = _currentTime;
        this._state = _state;
        this._buildInfo = _buildInfo;
        this._secondsTillShutdown = _secondsTillShutdown;
        this._shutdownReason = _shutdownReason;
    }

    public DateTime getStartTime() {
        return _startTime;
    }

    public DateTime getCurrentTime() {
        return _currentTime;
    }

    public ServerState getState() {
        return _state;
    }

    public BuildInfo getBuildInfo() {
        return _buildInfo;
    }

    public UInteger getSecondsTillShutdown() {
        return _secondsTillShutdown;
    }

    public LocalizedText getShutdownReason() {
        return _shutdownReason;
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


    public static void encode(ServerStatusDataType serverStatusDataType, UaEncoder encoder) {
        encoder.encodeDateTime("StartTime", serverStatusDataType._startTime);
        encoder.encodeDateTime("CurrentTime", serverStatusDataType._currentTime);
        encoder.encodeEnumeration("State", serverStatusDataType._state);
        encoder.encodeSerializable("BuildInfo", serverStatusDataType._buildInfo != null ? serverStatusDataType._buildInfo : new BuildInfo());
        encoder.encodeUInt32("SecondsTillShutdown", serverStatusDataType._secondsTillShutdown);
        encoder.encodeLocalizedText("ShutdownReason", serverStatusDataType._shutdownReason);
    }

    public static ServerStatusDataType decode(UaDecoder decoder) {
        DateTime _startTime = decoder.decodeDateTime("StartTime");
        DateTime _currentTime = decoder.decodeDateTime("CurrentTime");
        ServerState _state = decoder.decodeEnumeration("State", ServerState.class);
        BuildInfo _buildInfo = decoder.decodeSerializable("BuildInfo", BuildInfo.class);
        UInteger _secondsTillShutdown = decoder.decodeUInt32("SecondsTillShutdown");
        LocalizedText _shutdownReason = decoder.decodeLocalizedText("ShutdownReason");

        return new ServerStatusDataType(_startTime, _currentTime, _state, _buildInfo, _secondsTillShutdown, _shutdownReason);
    }

    static {
        DelegateRegistry.registerEncoder(ServerStatusDataType::encode, ServerStatusDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ServerStatusDataType::decode, ServerStatusDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
