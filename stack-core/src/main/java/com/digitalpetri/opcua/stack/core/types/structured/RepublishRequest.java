
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class RepublishRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.RepublishRequest;
	public static final NodeId BinaryEncodingId = Identifiers.RepublishRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.RepublishRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final Long _subscriptionId;
	protected final Long _retransmitSequenceNumber;

	public RepublishRequest(RequestHeader _requestHeader, Long _subscriptionId, Long _retransmitSequenceNumber) {

		this._requestHeader = _requestHeader;
		this._subscriptionId = _subscriptionId;
		this._retransmitSequenceNumber = _retransmitSequenceNumber;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public Long getSubscriptionId() { return _subscriptionId; }
	public Long getRetransmitSequenceNumber() { return _retransmitSequenceNumber; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(RepublishRequest republishRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", republishRequest._requestHeader);
		encoder.encodeUInt32("SubscriptionId", republishRequest._subscriptionId);
		encoder.encodeUInt32("RetransmitSequenceNumber", republishRequest._retransmitSequenceNumber);
	}

	public static RepublishRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Long _retransmitSequenceNumber = decoder.decodeUInt32("RetransmitSequenceNumber");

		return new RepublishRequest(_requestHeader, _subscriptionId, _retransmitSequenceNumber);
	}

	static {
		DelegateRegistry.registerEncoder(RepublishRequest::encode, RepublishRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(RepublishRequest::decode, RepublishRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
