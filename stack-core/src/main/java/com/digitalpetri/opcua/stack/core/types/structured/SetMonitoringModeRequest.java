
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;

public class SetMonitoringModeRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.SetMonitoringModeRequest;
	public static final NodeId BinaryEncodingId = Identifiers.SetMonitoringModeRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.SetMonitoringModeRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final Long _subscriptionId;
	protected final MonitoringMode _monitoringMode;
	protected final Long[] _monitoredItemIds;

	public SetMonitoringModeRequest(RequestHeader _requestHeader, Long _subscriptionId, MonitoringMode _monitoringMode, Long[] _monitoredItemIds) {

		this._requestHeader = _requestHeader;
		this._subscriptionId = _subscriptionId;
		this._monitoringMode = _monitoringMode;
		this._monitoredItemIds = _monitoredItemIds;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public Long getSubscriptionId() { return _subscriptionId; }
	public MonitoringMode getMonitoringMode() { return _monitoringMode; }
	public Long[] getMonitoredItemIds() { return _monitoredItemIds; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(SetMonitoringModeRequest setMonitoringModeRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", setMonitoringModeRequest._requestHeader);
		encoder.encodeUInt32("SubscriptionId", setMonitoringModeRequest._subscriptionId);
        encoder.encodeSerializable("MonitoringMode", setMonitoringModeRequest._monitoringMode);
        encoder.encodeArray("MonitoredItemIds", setMonitoringModeRequest._monitoredItemIds, encoder::encodeUInt32);
	}

	public static SetMonitoringModeRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        MonitoringMode _monitoringMode = decoder.decodeSerializable("MonitoringMode", MonitoringMode.class);
        Long[] _monitoredItemIds = decoder.decodeArray("MonitoredItemIds", decoder::decodeUInt32, Long.class);

		return new SetMonitoringModeRequest(_requestHeader, _subscriptionId, _monitoringMode, _monitoredItemIds);
	}

	static {
		DelegateRegistry.registerEncoder(SetMonitoringModeRequest::encode, SetMonitoringModeRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(SetMonitoringModeRequest::decode, SetMonitoringModeRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
