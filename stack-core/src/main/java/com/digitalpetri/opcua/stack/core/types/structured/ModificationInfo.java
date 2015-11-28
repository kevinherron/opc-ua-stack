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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.HistoryUpdateType;

@UaDataType("ModificationInfo")
public class ModificationInfo implements UaStructure {

    public static final NodeId TypeId = Identifiers.ModificationInfo;
    public static final NodeId BinaryEncodingId = Identifiers.ModificationInfo_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModificationInfo_Encoding_DefaultXml;

    protected final DateTime _modificationTime;
    protected final HistoryUpdateType _updateType;
    protected final String _userName;

    public ModificationInfo() {
        this._modificationTime = null;
        this._updateType = null;
        this._userName = null;
    }

    public ModificationInfo(DateTime _modificationTime, HistoryUpdateType _updateType, String _userName) {
        this._modificationTime = _modificationTime;
        this._updateType = _updateType;
        this._userName = _userName;
    }

    public DateTime getModificationTime() { return _modificationTime; }

    public HistoryUpdateType getUpdateType() { return _updateType; }

    public String getUserName() { return _userName; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ModificationInfo modificationInfo, UaEncoder encoder) {
        encoder.encodeDateTime("ModificationTime", modificationInfo._modificationTime);
        encoder.encodeEnumeration("UpdateType", modificationInfo._updateType);
        encoder.encodeString("UserName", modificationInfo._userName);
    }

    public static ModificationInfo decode(UaDecoder decoder) {
        DateTime _modificationTime = decoder.decodeDateTime("ModificationTime");
        HistoryUpdateType _updateType = decoder.decodeEnumeration("UpdateType", HistoryUpdateType.class);
        String _userName = decoder.decodeString("UserName");

        return new ModificationInfo(_modificationTime, _updateType, _userName);
    }

    static {
        DelegateRegistry.registerEncoder(ModificationInfo::encode, ModificationInfo.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModificationInfo::decode, ModificationInfo.class, BinaryEncodingId, XmlEncodingId);
    }

}
