
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

public class EventFieldList implements UaStructure {

	public static final NodeId TypeId = Identifiers.EventFieldList;
	public static final NodeId BinaryEncodingId = Identifiers.EventFieldList_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.EventFieldList_Encoding_DefaultXml;

	protected final Long _clientHandle;
	protected final Variant[] _eventFields;

	public EventFieldList(Long _clientHandle, Variant[] _eventFields) {

		this._clientHandle = _clientHandle;
		this._eventFields = _eventFields;
	}

	public Long getClientHandle() { return _clientHandle; }
	public Variant[] getEventFields() { return _eventFields; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(EventFieldList eventFieldList, UaEncoder encoder) {
		encoder.encodeUInt32("ClientHandle", eventFieldList._clientHandle);
        encoder.encodeArray("EventFields", eventFieldList._eventFields, encoder::encodeVariant);
	}

	public static EventFieldList decode(UaDecoder decoder) {
        Long _clientHandle = decoder.decodeUInt32("ClientHandle");
        Variant[] _eventFields = decoder.decodeArray("EventFields", decoder::decodeVariant, Variant.class);

		return new EventFieldList(_clientHandle, _eventFields);
	}

	static {
		DelegateRegistry.registerEncoder(EventFieldList::encode, EventFieldList.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(EventFieldList::decode, EventFieldList.class, BinaryEncodingId, XmlEncodingId);
	}

}
