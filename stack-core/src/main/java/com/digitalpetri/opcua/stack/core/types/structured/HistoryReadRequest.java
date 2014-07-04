
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class HistoryReadRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.HistoryReadRequest;
	public static final NodeId BinaryEncodingId = Identifiers.HistoryReadRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.HistoryReadRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final ExtensionObject _historyReadDetails;
	protected final TimestampsToReturn _timestampsToReturn;
	protected final Boolean _releaseContinuationPoints;
	protected final HistoryReadValueId[] _nodesToRead;

	public HistoryReadRequest(RequestHeader _requestHeader, ExtensionObject _historyReadDetails, TimestampsToReturn _timestampsToReturn, Boolean _releaseContinuationPoints, HistoryReadValueId[] _nodesToRead) {

		this._requestHeader = _requestHeader;
		this._historyReadDetails = _historyReadDetails;
		this._timestampsToReturn = _timestampsToReturn;
		this._releaseContinuationPoints = _releaseContinuationPoints;
		this._nodesToRead = _nodesToRead;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public ExtensionObject getHistoryReadDetails() { return _historyReadDetails; }
	public TimestampsToReturn getTimestampsToReturn() { return _timestampsToReturn; }
	public Boolean getReleaseContinuationPoints() { return _releaseContinuationPoints; }
	public HistoryReadValueId[] getNodesToRead() { return _nodesToRead; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(HistoryReadRequest historyReadRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", historyReadRequest._requestHeader);
		encoder.encodeExtensionObject("HistoryReadDetails", historyReadRequest._historyReadDetails);
        encoder.encodeSerializable("TimestampsToReturn", historyReadRequest._timestampsToReturn);
		encoder.encodeBoolean("ReleaseContinuationPoints", historyReadRequest._releaseContinuationPoints);
        encoder.encodeArray("NodesToRead", historyReadRequest._nodesToRead, encoder::encodeSerializable);
	}

	public static HistoryReadRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        ExtensionObject _historyReadDetails = decoder.decodeExtensionObject("HistoryReadDetails");
        TimestampsToReturn _timestampsToReturn = decoder.decodeSerializable("TimestampsToReturn", TimestampsToReturn.class);
        Boolean _releaseContinuationPoints = decoder.decodeBoolean("ReleaseContinuationPoints");
        HistoryReadValueId[] _nodesToRead = decoder.decodeArray("NodesToRead", decoder::decodeSerializable, HistoryReadValueId.class);

		return new HistoryReadRequest(_requestHeader, _historyReadDetails, _timestampsToReturn, _releaseContinuationPoints, _nodesToRead);
	}

	static {
		DelegateRegistry.registerEncoder(HistoryReadRequest::encode, HistoryReadRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(HistoryReadRequest::decode, HistoryReadRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
