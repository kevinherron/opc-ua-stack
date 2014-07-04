
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class DeleteSubscriptionsRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.DeleteSubscriptionsRequest;
	public static final NodeId BinaryEncodingId = Identifiers.DeleteSubscriptionsRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.DeleteSubscriptionsRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final Long[] _subscriptionIds;

	public DeleteSubscriptionsRequest(RequestHeader _requestHeader, Long[] _subscriptionIds) {

		this._requestHeader = _requestHeader;
		this._subscriptionIds = _subscriptionIds;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public Long[] getSubscriptionIds() { return _subscriptionIds; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(DeleteSubscriptionsRequest deleteSubscriptionsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", deleteSubscriptionsRequest._requestHeader);
        encoder.encodeArray("SubscriptionIds", deleteSubscriptionsRequest._subscriptionIds, encoder::encodeUInt32);
	}

	public static DeleteSubscriptionsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long[] _subscriptionIds = decoder.decodeArray("SubscriptionIds", decoder::decodeUInt32, Long.class);

		return new DeleteSubscriptionsRequest(_requestHeader, _subscriptionIds);
	}

	static {
		DelegateRegistry.registerEncoder(DeleteSubscriptionsRequest::encode, DeleteSubscriptionsRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(DeleteSubscriptionsRequest::decode, DeleteSubscriptionsRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
