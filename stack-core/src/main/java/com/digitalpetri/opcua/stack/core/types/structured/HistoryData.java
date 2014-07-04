
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class HistoryData implements UaStructure {

	public static final NodeId TypeId = Identifiers.HistoryData;
	public static final NodeId BinaryEncodingId = Identifiers.HistoryData_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.HistoryData_Encoding_DefaultXml;

	protected final DataValue[] _dataValues;

	public HistoryData(DataValue[] _dataValues) {

		this._dataValues = _dataValues;
	}

	public DataValue[] getDataValues() { return _dataValues; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(HistoryData historyData, UaEncoder encoder) {
        encoder.encodeArray("DataValues", historyData._dataValues, encoder::encodeDataValue);
	}

	public static HistoryData decode(UaDecoder decoder) {
        DataValue[] _dataValues = decoder.decodeArray("DataValues", decoder::decodeDataValue, DataValue.class);

		return new HistoryData(_dataValues);
	}

	static {
		DelegateRegistry.registerEncoder(HistoryData::encode, HistoryData.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(HistoryData::decode, HistoryData.class, BinaryEncodingId, XmlEncodingId);
	}

}
