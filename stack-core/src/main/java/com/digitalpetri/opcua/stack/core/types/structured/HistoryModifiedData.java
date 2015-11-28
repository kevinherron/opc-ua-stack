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
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("HistoryModifiedData")
public class HistoryModifiedData extends HistoryData {

    public static final NodeId TypeId = Identifiers.HistoryModifiedData;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryModifiedData_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryModifiedData_Encoding_DefaultXml;

    protected final DataValue[] _dataValues;
    protected final ModificationInfo[] _modificationInfos;

    public HistoryModifiedData() {
        super();
        this._dataValues = null;
        this._modificationInfos = null;
    }

    public HistoryModifiedData(DataValue[] _dataValues, ModificationInfo[] _modificationInfos) {
        super();
        this._dataValues = _dataValues;
        this._modificationInfos = _modificationInfos;
    }

    public DataValue[] getDataValues() { return _dataValues; }

    public ModificationInfo[] getModificationInfos() { return _modificationInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(HistoryModifiedData historyModifiedData, UaEncoder encoder) {
        encoder.encodeArray("DataValues", historyModifiedData._dataValues, encoder::encodeDataValue);
        encoder.encodeArray("ModificationInfos", historyModifiedData._modificationInfos, encoder::encodeSerializable);
    }

    public static HistoryModifiedData decode(UaDecoder decoder) {
        DataValue[] _dataValues = decoder.decodeArray("DataValues", decoder::decodeDataValue, DataValue.class);
        ModificationInfo[] _modificationInfos = decoder.decodeArray("ModificationInfos", decoder::decodeSerializable, ModificationInfo.class);

        return new HistoryModifiedData(_dataValues, _modificationInfos);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryModifiedData::encode, HistoryModifiedData.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryModifiedData::decode, HistoryModifiedData.class, BinaryEncodingId, XmlEncodingId);
    }

}
