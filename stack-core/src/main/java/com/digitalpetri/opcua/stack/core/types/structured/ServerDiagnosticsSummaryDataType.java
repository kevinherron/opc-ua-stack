/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ServerDiagnosticsSummaryDataType")
public class ServerDiagnosticsSummaryDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.ServerDiagnosticsSummaryDataType;
    public static final NodeId BinaryEncodingId = Identifiers.ServerDiagnosticsSummaryDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ServerDiagnosticsSummaryDataType_Encoding_DefaultXml;

    protected final UInteger _serverViewCount;
    protected final UInteger _currentSessionCount;
    protected final UInteger _cumulatedSessionCount;
    protected final UInteger _securityRejectedSessionCount;
    protected final UInteger _rejectedSessionCount;
    protected final UInteger _sessionTimeoutCount;
    protected final UInteger _sessionAbortCount;
    protected final UInteger _currentSubscriptionCount;
    protected final UInteger _cumulatedSubscriptionCount;
    protected final UInteger _publishingIntervalCount;
    protected final UInteger _securityRejectedRequestsCount;
    protected final UInteger _rejectedRequestsCount;

    public ServerDiagnosticsSummaryDataType() {
        this._serverViewCount = null;
        this._currentSessionCount = null;
        this._cumulatedSessionCount = null;
        this._securityRejectedSessionCount = null;
        this._rejectedSessionCount = null;
        this._sessionTimeoutCount = null;
        this._sessionAbortCount = null;
        this._currentSubscriptionCount = null;
        this._cumulatedSubscriptionCount = null;
        this._publishingIntervalCount = null;
        this._securityRejectedRequestsCount = null;
        this._rejectedRequestsCount = null;
    }

    public ServerDiagnosticsSummaryDataType(UInteger _serverViewCount, UInteger _currentSessionCount, UInteger _cumulatedSessionCount, UInteger _securityRejectedSessionCount, UInteger _rejectedSessionCount, UInteger _sessionTimeoutCount, UInteger _sessionAbortCount, UInteger _currentSubscriptionCount, UInteger _cumulatedSubscriptionCount, UInteger _publishingIntervalCount, UInteger _securityRejectedRequestsCount, UInteger _rejectedRequestsCount) {
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

    public UInteger getServerViewCount() { return _serverViewCount; }

    public UInteger getCurrentSessionCount() { return _currentSessionCount; }

    public UInteger getCumulatedSessionCount() { return _cumulatedSessionCount; }

    public UInteger getSecurityRejectedSessionCount() { return _securityRejectedSessionCount; }

    public UInteger getRejectedSessionCount() { return _rejectedSessionCount; }

    public UInteger getSessionTimeoutCount() { return _sessionTimeoutCount; }

    public UInteger getSessionAbortCount() { return _sessionAbortCount; }

    public UInteger getCurrentSubscriptionCount() { return _currentSubscriptionCount; }

    public UInteger getCumulatedSubscriptionCount() { return _cumulatedSubscriptionCount; }

    public UInteger getPublishingIntervalCount() { return _publishingIntervalCount; }

    public UInteger getSecurityRejectedRequestsCount() { return _securityRejectedRequestsCount; }

    public UInteger getRejectedRequestsCount() { return _rejectedRequestsCount; }

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
        UInteger _serverViewCount = decoder.decodeUInt32("ServerViewCount");
        UInteger _currentSessionCount = decoder.decodeUInt32("CurrentSessionCount");
        UInteger _cumulatedSessionCount = decoder.decodeUInt32("CumulatedSessionCount");
        UInteger _securityRejectedSessionCount = decoder.decodeUInt32("SecurityRejectedSessionCount");
        UInteger _rejectedSessionCount = decoder.decodeUInt32("RejectedSessionCount");
        UInteger _sessionTimeoutCount = decoder.decodeUInt32("SessionTimeoutCount");
        UInteger _sessionAbortCount = decoder.decodeUInt32("SessionAbortCount");
        UInteger _currentSubscriptionCount = decoder.decodeUInt32("CurrentSubscriptionCount");
        UInteger _cumulatedSubscriptionCount = decoder.decodeUInt32("CumulatedSubscriptionCount");
        UInteger _publishingIntervalCount = decoder.decodeUInt32("PublishingIntervalCount");
        UInteger _securityRejectedRequestsCount = decoder.decodeUInt32("SecurityRejectedRequestsCount");
        UInteger _rejectedRequestsCount = decoder.decodeUInt32("RejectedRequestsCount");

        return new ServerDiagnosticsSummaryDataType(_serverViewCount, _currentSessionCount, _cumulatedSessionCount, _securityRejectedSessionCount, _rejectedSessionCount, _sessionTimeoutCount, _sessionAbortCount, _currentSubscriptionCount, _cumulatedSubscriptionCount, _publishingIntervalCount, _securityRejectedRequestsCount, _rejectedRequestsCount);
    }

    static {
        DelegateRegistry.registerEncoder(ServerDiagnosticsSummaryDataType::encode, ServerDiagnosticsSummaryDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ServerDiagnosticsSummaryDataType::decode, ServerDiagnosticsSummaryDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
