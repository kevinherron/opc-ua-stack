
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class ReadRawModifiedDetails extends HistoryReadDetails {

	public static final NodeId TypeId = Identifiers.ReadRawModifiedDetails;
	public static final NodeId BinaryEncodingId = Identifiers.ReadRawModifiedDetails_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.ReadRawModifiedDetails_Encoding_DefaultXml;

	protected final Boolean _isReadModified;
	protected final DateTime _startTime;
	protected final DateTime _endTime;
	protected final Long _numValuesPerNode;
	protected final Boolean _returnBounds;

	public ReadRawModifiedDetails(Boolean _isReadModified, DateTime _startTime, DateTime _endTime, Long _numValuesPerNode, Boolean _returnBounds) {
        super();

		this._isReadModified = _isReadModified;
		this._startTime = _startTime;
		this._endTime = _endTime;
		this._numValuesPerNode = _numValuesPerNode;
		this._returnBounds = _returnBounds;
	}

	public Boolean getIsReadModified() { return _isReadModified; }
	public DateTime getStartTime() { return _startTime; }
	public DateTime getEndTime() { return _endTime; }
	public Long getNumValuesPerNode() { return _numValuesPerNode; }
	public Boolean getReturnBounds() { return _returnBounds; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(ReadRawModifiedDetails readRawModifiedDetails, UaEncoder encoder) {
		encoder.encodeBoolean("IsReadModified", readRawModifiedDetails._isReadModified);
		encoder.encodeDateTime("StartTime", readRawModifiedDetails._startTime);
		encoder.encodeDateTime("EndTime", readRawModifiedDetails._endTime);
		encoder.encodeUInt32("NumValuesPerNode", readRawModifiedDetails._numValuesPerNode);
		encoder.encodeBoolean("ReturnBounds", readRawModifiedDetails._returnBounds);
	}

	public static ReadRawModifiedDetails decode(UaDecoder decoder) {
        Boolean _isReadModified = decoder.decodeBoolean("IsReadModified");
        DateTime _startTime = decoder.decodeDateTime("StartTime");
        DateTime _endTime = decoder.decodeDateTime("EndTime");
        Long _numValuesPerNode = decoder.decodeUInt32("NumValuesPerNode");
        Boolean _returnBounds = decoder.decodeBoolean("ReturnBounds");

		return new ReadRawModifiedDetails(_isReadModified, _startTime, _endTime, _numValuesPerNode, _returnBounds);
	}

	static {
		DelegateRegistry.registerEncoder(ReadRawModifiedDetails::encode, ReadRawModifiedDetails.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(ReadRawModifiedDetails::decode, ReadRawModifiedDetails.class, BinaryEncodingId, XmlEncodingId);
	}

}
