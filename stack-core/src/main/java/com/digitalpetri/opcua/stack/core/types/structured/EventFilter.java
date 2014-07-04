
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class EventFilter extends MonitoringFilter {

	public static final NodeId TypeId = Identifiers.EventFilter;
	public static final NodeId BinaryEncodingId = Identifiers.EventFilter_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.EventFilter_Encoding_DefaultXml;

	protected final SimpleAttributeOperand[] _selectClauses;
	protected final ContentFilter _whereClause;

	public EventFilter(SimpleAttributeOperand[] _selectClauses, ContentFilter _whereClause) {
        super();

		this._selectClauses = _selectClauses;
		this._whereClause = _whereClause;
	}

	public SimpleAttributeOperand[] getSelectClauses() { return _selectClauses; }
	public ContentFilter getWhereClause() { return _whereClause; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(EventFilter eventFilter, UaEncoder encoder) {
        encoder.encodeArray("SelectClauses", eventFilter._selectClauses, encoder::encodeSerializable);
        encoder.encodeSerializable("WhereClause", eventFilter._whereClause);
	}

	public static EventFilter decode(UaDecoder decoder) {
        SimpleAttributeOperand[] _selectClauses = decoder.decodeArray("SelectClauses", decoder::decodeSerializable, SimpleAttributeOperand.class);
        ContentFilter _whereClause = decoder.decodeSerializable("WhereClause", ContentFilter.class);

		return new EventFilter(_selectClauses, _whereClause);
	}

	static {
		DelegateRegistry.registerEncoder(EventFilter::encode, EventFilter.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(EventFilter::decode, EventFilter.class, BinaryEncodingId, XmlEncodingId);
	}

}
