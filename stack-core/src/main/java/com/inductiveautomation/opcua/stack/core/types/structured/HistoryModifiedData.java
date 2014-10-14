package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class HistoryModifiedData extends HistoryData {

    public static final NodeId TypeId = Identifiers.HistoryModifiedData;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryModifiedData_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryModifiedData_Encoding_DefaultXml;

    protected final ModificationInfo[] _modificationInfos;

    public HistoryModifiedData(DataValue[] _dataValues, ModificationInfo[] _modificationInfos) {
        super(_dataValues);
        this._modificationInfos = _modificationInfos;
    }

    public ModificationInfo[] getModificationInfos() {
        return _modificationInfos;
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
