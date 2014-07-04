
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class ServerDiagnosticsSummaryDataType implements UaStructure {

	public static final NodeId TypeId = Identifiers.ServerDiagnosticsSummaryDataType;
	public static final NodeId BinaryEncodingId = Identifiers.ServerDiagnosticsSummaryDataType_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.ServerDiagnosticsSummaryDataType_Encoding_DefaultXml;

	protected final Long _serverViewCount;
	protected final Long _currentSessionCount;
	protected final Long _cumulatedSessionCount;
	protected final Long _securityRejectedSessionCount;
	protected final Long _rejectedSessionCount;
	protected final Long _sessionTimeoutCount;
	protected final Long _sessionAbortCount;
	protected final Long _currentSubscriptionCount;
	protected final Long _cumulatedSubscriptionCount;
	protected final Long _publishingIntervalCount;
	protected final Long _securityRejectedRequestsCount;
	protected final Long _rejectedRequestsCount;

	public ServerDiagnosticsSummaryDataType(Long _serverViewCount, Long _currentSessionCount, Long _cumulatedSessionCount, Long _securityRejectedSessionCount, Long _rejectedSessionCount, Long _sessionTimeoutCount, Long _sessionAbortCount, Long _currentSubscriptionCount, Long _cumulatedSubscriptionCount, Long _publishingIntervalCount, Long _securityRejectedRequestsCount, Long _rejectedRequestsCount) {

		this._serverViewCount = _serverViewCount;
		this._currentSessionCount = _currentSessionCount;
		this._cumulatedSessionCount = _cumulatedSessionCount;
		this._securityRejectedSessionCount = _securityRejectedSessionCount;
		this._rejectedSessionCount = _rejectedSessionCount;
		this._sessionTimeoutCount = _sessionTimeoutCount;
		this._sessionAbortCount = _sessionAbortCount;
		this._currentSubscriptionCount = _currentSubscriptionCount;
		this._cumulatedSubscriptionCount = _cumulatedSubscriptionCount;
		this._publishingIntervalCount = _publishingIntervalCount;
		this._securityRejectedRequestsCount = _securityRejectedRequestsCount;
		this._rejectedRequestsCount = _rejectedRequestsCount;
	}

	public Long getServerViewCount() { return _serverViewCount; }
	public Long getCurrentSessionCount() { return _currentSessionCount; }
	public Long getCumulatedSessionCount() { return _cumulatedSessionCount; }
	public Long getSecurityRejectedSessionCount() { return _securityRejectedSessionCount; }
	public Long getRejectedSessionCount() { return _rejectedSessionCount; }
	public Long getSessionTimeoutCount() { return _sessionTimeoutCount; }
	public Long getSessionAbortCount() { return _sessionAbortCount; }
	public Long getCurrentSubscriptionCount() { return _currentSubscriptionCount; }
	public Long getCumulatedSubscriptionCount() { return _cumulatedSubscriptionCount; }
	public Long getPublishingIntervalCount() { return _publishingIntervalCount; }
	public Long getSecurityRejectedRequestsCount() { return _securityRejectedRequestsCount; }
	public Long getRejectedRequestsCount() { return _rejectedRequestsCount; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(ServerDiagnosticsSummaryDataType serverDiagnosticsSummaryDataType, UaEncoder encoder) {
		encoder.encodeUInt32("ServerViewCount", serverDiagnosticsSummaryDataType._serverViewCount);
		encoder.encodeUInt32("CurrentSessionCount", serverDiagnosticsSummaryDataType._currentSessionCount);
		encoder.encodeUInt32("CumulatedSessionCount", serverDiagnosticsSummaryDataType._cumulatedSessionCount);
		encoder.encodeUInt32("SecurityRejectedSessionCount", serverDiagnosticsSummaryDataType._securityRejectedSessionCount);
		encoder.encodeUInt32("RejectedSessionCount", serverDiagnosticsSummaryDataType._rejectedSessionCount);
		encoder.encodeUInt32("SessionTimeoutCount", serverDiagnosticsSummaryDataType._sessionTimeoutCount);
		encoder.encodeUInt32("SessionAbortCount", serverDiagnosticsSummaryDataType._sessionAbortCount);
		encoder.encodeUInt32("CurrentSubscriptionCount", serverDiagnosticsSummaryDataType._currentSubscriptionCount);
		encoder.encodeUInt32("CumulatedSubscriptionCount", serverDiagnosticsSummaryDataType._cumulatedSubscriptionCount);
		encoder.encodeUInt32("PublishingIntervalCount", serverDiagnosticsSummaryDataType._publishingIntervalCount);
		encoder.encodeUInt32("SecurityRejectedRequestsCount", serverDiagnosticsSummaryDataType._securityRejectedRequestsCount);
		encoder.encodeUInt32("RejectedRequestsCount", serverDiagnosticsSummaryDataType._rejectedRequestsCount);
	}

	public static ServerDiagnosticsSummaryDataType decode(UaDecoder decoder) {
        Long _serverViewCount = decoder.decodeUInt32("ServerViewCount");
        Long _currentSessionCount = decoder.decodeUInt32("CurrentSessionCount");
        Long _cumulatedSessionCount = decoder.decodeUInt32("CumulatedSessionCount");
        Long _securityRejectedSessionCount = decoder.decodeUInt32("SecurityRejectedSessionCount");
        Long _rejectedSessionCount = decoder.decodeUInt32("RejectedSessionCount");
        Long _sessionTimeoutCount = decoder.decodeUInt32("SessionTimeoutCount");
        Long _sessionAbortCount = decoder.decodeUInt32("SessionAbortCount");
        Long _currentSubscriptionCount = decoder.decodeUInt32("CurrentSubscriptionCount");
        Long _cumulatedSubscriptionCount = decoder.decodeUInt32("CumulatedSubscriptionCount");
        Long _publishingIntervalCount = decoder.decodeUInt32("PublishingIntervalCount");
        Long _securityRejectedRequestsCount = decoder.decodeUInt32("SecurityRejectedRequestsCount");
        Long _rejectedRequestsCount = decoder.decodeUInt32("RejectedRequestsCount");

		return new ServerDiagnosticsSummaryDataType(_serverViewCount, _currentSessionCount, _cumulatedSessionCount, _securityRejectedSessionCount, _rejectedSessionCount, _sessionTimeoutCount, _sessionAbortCount, _currentSubscriptionCount, _cumulatedSubscriptionCount, _publishingIntervalCount, _securityRejectedRequestsCount, _rejectedRequestsCount);
	}

	static {
		DelegateRegistry.registerEncoder(ServerDiagnosticsSummaryDataType::encode, ServerDiagnosticsSummaryDataType.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(ServerDiagnosticsSummaryDataType::decode, ServerDiagnosticsSummaryDataType.class, BinaryEncodingId, XmlEncodingId);
	}

}
