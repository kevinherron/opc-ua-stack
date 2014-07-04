
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class MonitoredItemNotification implements UaStructure {

	public static final NodeId TypeId = Identifiers.MonitoredItemNotification;
	public static final NodeId BinaryEncodingId = Identifiers.MonitoredItemNotification_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.MonitoredItemNotification_Encoding_DefaultXml;

	protected final Long _clientHandle;
	protected final DataValue _value;

	public MonitoredItemNotification(Long _clientHandle, DataValue _value) {

		this._clientHandle = _clientHandle;
		this._value = _value;
	}

	public Long getClientHandle() { return _clientHandle; }
	public DataValue getValue() { return _value; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(MonitoredItemNotification monitoredItemNotification, UaEncoder encoder) {
		encoder.encodeUInt32("ClientHandle", monitoredItemNotification._clientHandle);
		encoder.encodeDataValue("Value", monitoredItemNotification._value);
	}

	public static MonitoredItemNotification decode(UaDecoder decoder) {
        Long _clientHandle = decoder.decodeUInt32("ClientHandle");
        DataValue _value = decoder.decodeDataValue("Value");

		return new MonitoredItemNotification(_clientHandle, _value);
	}

	static {
		DelegateRegistry.registerEncoder(MonitoredItemNotification::encode, MonitoredItemNotification.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(MonitoredItemNotification::decode, MonitoredItemNotification.class, BinaryEncodingId, XmlEncodingId);
	}

}
