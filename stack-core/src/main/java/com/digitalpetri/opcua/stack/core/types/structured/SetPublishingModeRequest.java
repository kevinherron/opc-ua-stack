
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class SetPublishingModeRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.SetPublishingModeRequest;
	public static final NodeId BinaryEncodingId = Identifiers.SetPublishingModeRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.SetPublishingModeRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final Boolean _publishingEnabled;
	protected final Long[] _subscriptionIds;

	public SetPublishingModeRequest(RequestHeader _requestHeader, Boolean _publishingEnabled, Long[] _subscriptionIds) {

		this._requestHeader = _requestHeader;
		this._publishingEnabled = _publishingEnabled;
		this._subscriptionIds = _subscriptionIds;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public Boolean getPublishingEnabled() { return _publishingEnabled; }
	public Long[] getSubscriptionIds() { return _subscriptionIds; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(SetPublishingModeRequest setPublishingModeRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", setPublishingModeRequest._requestHeader);
		encoder.encodeBoolean("PublishingEnabled", setPublishingModeRequest._publishingEnabled);
        encoder.encodeArray("SubscriptionIds", setPublishingModeRequest._subscriptionIds, encoder::encodeUInt32);
	}

	public static SetPublishingModeRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Boolean _publishingEnabled = decoder.decodeBoolean("PublishingEnabled");
        Long[] _subscriptionIds = decoder.decodeArray("SubscriptionIds", decoder::decodeUInt32, Long.class);

		return new SetPublishingModeRequest(_requestHeader, _publishingEnabled, _subscriptionIds);
	}

	static {
		DelegateRegistry.registerEncoder(SetPublishingModeRequest::encode, SetPublishingModeRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(SetPublishingModeRequest::decode, SetPublishingModeRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
