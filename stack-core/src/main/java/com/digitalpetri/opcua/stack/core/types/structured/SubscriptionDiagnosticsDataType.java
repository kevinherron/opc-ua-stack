package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class SubscriptionDiagnosticsDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.SubscriptionDiagnosticsDataType;
    public static final NodeId BinaryEncodingId = Identifiers.SubscriptionDiagnosticsDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SubscriptionDiagnosticsDataType_Encoding_DefaultXml;

    protected final NodeId _sessionId;
    protected final Long _subscriptionId;
    protected final Short _priority;
    protected final Double _publishingInterval;
    protected final Long _maxKeepAliveCount;
    protected final Long _maxLifetimeCount;
    protected final Long _maxNotificationsPerPublish;
    protected final Boolean _publishingEnabled;
    protected final Long _modifyCount;
    protected final Long _enableCount;
    protected final Long _disableCount;
    protected final Long _republishRequestCount;
    protected final Long _republishMessageRequestCount;
    protected final Long _republishMessageCount;
    protected final Long _transferRequestCount;
    protected final Long _transferredToAltClientCount;
    protected final Long _transferredToSameClientCount;
    protected final Long _publishRequestCount;
    protected final Long _dataChangeNotificationsCount;
    protected final Long _eventNotificationsCount;
    protected final Long _notificationsCount;
    protected final Long _latePublishRequestCount;
    protected final Long _currentKeepAliveCount;
    protected final Long _currentLifetimeCount;
    protected final Long _unacknowledgedMessageCount;
    protected final Long _discardedMessageCount;
    protected final Long _monitoredItemCount;
    protected final Long _disabledMonitoredItemCount;
    protected final Long _monitoringQueueOverflowCount;
    protected final Long _nextSequenceNumber;
    protected final Long _eventQueueOverFlowCount;

    public SubscriptionDiagnosticsDataType(NodeId _sessionId, Long _subscriptionId, Short _priority, Double _publishingInterval, Long _maxKeepAliveCount, Long _maxLifetimeCount, Long _maxNotificationsPerPublish, Boolean _publishingEnabled, Long _modifyCount, Long _enableCount, Long _disableCount, Long _republishRequestCount, Long _republishMessageRequestCount, Long _republishMessageCount, Long _transferRequestCount, Long _transferredToAltClientCount, Long _transferredToSameClientCount, Long _publishRequestCount, Long _dataChangeNotificationsCount, Long _eventNotificationsCount, Long _notificationsCount, Long _latePublishRequestCount, Long _currentKeepAliveCount, Long _currentLifetimeCount, Long _unacknowledgedMessageCount, Long _discardedMessageCount, Long _monitoredItemCount, Long _disabledMonitoredItemCount, Long _monitoringQueueOverflowCount, Long _nextSequenceNumber, Long _eventQueueOverFlowCount) {
        this._sessionId = _sessionId;
        this._subscriptionId = _subscriptionId;
        this._priority = _priority;
        this._publishingInterval = _publishingInterval;
        this._maxKeepAliveCount = _maxKeepAliveCount;
        this._maxLifetimeCount = _maxLifetimeCount;
        this._maxNotificationsPerPublish = _maxNotificationsPerPublish;
        this._publishingEnabled = _publishingEnabled;
        this._modifyCount = _modifyCount;
        this._enableCount = _enableCount;
        this._disableCount = _disableCount;
        this._republishRequestCount = _republishRequestCount;
        this._republishMessageRequestCount = _republishMessageRequestCount;
        this._republishMessageCount = _republishMessageCount;
        this._transferRequestCount = _transferRequestCount;
        this._transferredToAltClientCount = _transferredToAltClientCount;
        this._transferredToSameClientCount = _transferredToSameClientCount;
        this._publishRequestCount = _publishRequestCount;
        this._dataChangeNotificationsCount = _dataChangeNotificationsCount;
        this._eventNotificationsCount = _eventNotificationsCount;
        this._notificationsCount = _notificationsCount;
        this._latePublishRequestCount = _latePublishRequestCount;
        this._currentKeepAliveCount = _currentKeepAliveCount;
        this._currentLifetimeCount = _currentLifetimeCount;
        this._unacknowledgedMessageCount = _unacknowledgedMessageCount;
        this._discardedMessageCount = _discardedMessageCount;
        this._monitoredItemCount = _monitoredItemCount;
        this._disabledMonitoredItemCount = _disabledMonitoredItemCount;
        this._monitoringQueueOverflowCount = _monitoringQueueOverflowCount;
        this._nextSequenceNumber = _nextSequenceNumber;
        this._eventQueueOverFlowCount = _eventQueueOverFlowCount;
    }

    public NodeId getSessionId() { return _sessionId; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public Short getPriority() { return _priority; }

    public Double getPublishingInterval() { return _publishingInterval; }

    public Long getMaxKeepAliveCount() { return _maxKeepAliveCount; }

    public Long getMaxLifetimeCount() { return _maxLifetimeCount; }

    public Long getMaxNotificationsPerPublish() { return _maxNotificationsPerPublish; }

    public Boolean getPublishingEnabled() { return _publishingEnabled; }

    public Long getModifyCount() { return _modifyCount; }

    public Long getEnableCount() { return _enableCount; }

    public Long getDisableCount() { return _disableCount; }

    public Long getRepublishRequestCount() { return _republishRequestCount; }

    public Long getRepublishMessageRequestCount() { return _republishMessageRequestCount; }

    public Long getRepublishMessageCount() { return _republishMessageCount; }

    public Long getTransferRequestCount() { return _transferRequestCount; }

    public Long getTransferredToAltClientCount() { return _transferredToAltClientCount; }

    public Long getTransferredToSameClientCount() { return _transferredToSameClientCount; }

    public Long getPublishRequestCount() { return _publishRequestCount; }

    public Long getDataChangeNotificationsCount() { return _dataChangeNotificationsCount; }

    public Long getEventNotificationsCount() { return _eventNotificationsCount; }

    public Long getNotificationsCount() { return _notificationsCount; }

    public Long getLatePublishRequestCount() { return _latePublishRequestCount; }

    public Long getCurrentKeepAliveCount() { return _currentKeepAliveCount; }

    public Long getCurrentLifetimeCount() { return _currentLifetimeCount; }

    public Long getUnacknowledgedMessageCount() { return _unacknowledgedMessageCount; }

    public Long getDiscardedMessageCount() { return _discardedMessageCount; }

    public Long getMonitoredItemCount() { return _monitoredItemCount; }

    public Long getDisabledMonitoredItemCount() { return _disabledMonitoredItemCount; }

    public Long getMonitoringQueueOverflowCount() { return _monitoringQueueOverflowCount; }

    public Long getNextSequenceNumber() { return _nextSequenceNumber; }

    public Long getEventQueueOverFlowCount() { return _eventQueueOverFlowCount; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SubscriptionDiagnosticsDataType subscriptionDiagnosticsDataType, UaEncoder encoder) {
        encoder.encodeNodeId("SessionId", subscriptionDiagnosticsDataType._sessionId);
        encoder.encodeUInt32("SubscriptionId", subscriptionDiagnosticsDataType._subscriptionId);
        encoder.encodeByte("Priority", subscriptionDiagnosticsDataType._priority);
        encoder.encodeDouble("PublishingInterval", subscriptionDiagnosticsDataType._publishingInterval);
        encoder.encodeUInt32("MaxKeepAliveCount", subscriptionDiagnosticsDataType._maxKeepAliveCount);
        encoder.encodeUInt32("MaxLifetimeCount", subscriptionDiagnosticsDataType._maxLifetimeCount);
        encoder.encodeUInt32("MaxNotificationsPerPublish", subscriptionDiagnosticsDataType._maxNotificationsPerPublish);
        encoder.encodeBoolean("PublishingEnabled", subscriptionDiagnosticsDataType._publishingEnabled);
        encoder.encodeUInt32("ModifyCount", subscriptionDiagnosticsDataType._modifyCount);
        encoder.encodeUInt32("EnableCount", subscriptionDiagnosticsDataType._enableCount);
        encoder.encodeUInt32("DisableCount", subscriptionDiagnosticsDataType._disableCount);
        encoder.encodeUInt32("RepublishRequestCount", subscriptionDiagnosticsDataType._republishRequestCount);
        encoder.encodeUInt32("RepublishMessageRequestCount", subscriptionDiagnosticsDataType._republishMessageRequestCount);
        encoder.encodeUInt32("RepublishMessageCount", subscriptionDiagnosticsDataType._republishMessageCount);
        encoder.encodeUInt32("TransferRequestCount", subscriptionDiagnosticsDataType._transferRequestCount);
        encoder.encodeUInt32("TransferredToAltClientCount", subscriptionDiagnosticsDataType._transferredToAltClientCount);
        encoder.encodeUInt32("TransferredToSameClientCount", subscriptionDiagnosticsDataType._transferredToSameClientCount);
        encoder.encodeUInt32("PublishRequestCount", subscriptionDiagnosticsDataType._publishRequestCount);
        encoder.encodeUInt32("DataChangeNotificationsCount", subscriptionDiagnosticsDataType._dataChangeNotificationsCount);
        encoder.encodeUInt32("EventNotificationsCount", subscriptionDiagnosticsDataType._eventNotificationsCount);
        encoder.encodeUInt32("NotificationsCount", subscriptionDiagnosticsDataType._notificationsCount);
        encoder.encodeUInt32("LatePublishRequestCount", subscriptionDiagnosticsDataType._latePublishRequestCount);
        encoder.encodeUInt32("CurrentKeepAliveCount", subscriptionDiagnosticsDataType._currentKeepAliveCount);
        encoder.encodeUInt32("CurrentLifetimeCount", subscriptionDiagnosticsDataType._currentLifetimeCount);
        encoder.encodeUInt32("UnacknowledgedMessageCount", subscriptionDiagnosticsDataType._unacknowledgedMessageCount);
        encoder.encodeUInt32("DiscardedMessageCount", subscriptionDiagnosticsDataType._discardedMessageCount);
        encoder.encodeUInt32("MonitoredItemCount", subscriptionDiagnosticsDataType._monitoredItemCount);
        encoder.encodeUInt32("DisabledMonitoredItemCount", subscriptionDiagnosticsDataType._disabledMonitoredItemCount);
        encoder.encodeUInt32("MonitoringQueueOverflowCount", subscriptionDiagnosticsDataType._monitoringQueueOverflowCount);
        encoder.encodeUInt32("NextSequenceNumber", subscriptionDiagnosticsDataType._nextSequenceNumber);
        encoder.encodeUInt32("EventQueueOverFlowCount", subscriptionDiagnosticsDataType._eventQueueOverFlowCount);
    }

    public static SubscriptionDiagnosticsDataType decode(UaDecoder decoder) {
        NodeId _sessionId = decoder.decodeNodeId("SessionId");
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Short _priority = decoder.decodeByte("Priority");
        Double _publishingInterval = decoder.decodeDouble("PublishingInterval");
        Long _maxKeepAliveCount = decoder.decodeUInt32("MaxKeepAliveCount");
        Long _maxLifetimeCount = decoder.decodeUInt32("MaxLifetimeCount");
        Long _maxNotificationsPerPublish = decoder.decodeUInt32("MaxNotificationsPerPublish");
        Boolean _publishingEnabled = decoder.decodeBoolean("PublishingEnabled");
        Long _modifyCount = decoder.decodeUInt32("ModifyCount");
        Long _enableCount = decoder.decodeUInt32("EnableCount");
        Long _disableCount = decoder.decodeUInt32("DisableCount");
        Long _republishRequestCount = decoder.decodeUInt32("RepublishRequestCount");
        Long _republishMessageRequestCount = decoder.decodeUInt32("RepublishMessageRequestCount");
        Long _republishMessageCount = decoder.decodeUInt32("RepublishMessageCount");
        Long _transferRequestCount = decoder.decodeUInt32("TransferRequestCount");
        Long _transferredToAltClientCount = decoder.decodeUInt32("TransferredToAltClientCount");
        Long _transferredToSameClientCount = decoder.decodeUInt32("TransferredToSameClientCount");
        Long _publishRequestCount = decoder.decodeUInt32("PublishRequestCount");
        Long _dataChangeNotificationsCount = decoder.decodeUInt32("DataChangeNotificationsCount");
        Long _eventNotificationsCount = decoder.decodeUInt32("EventNotificationsCount");
        Long _notificationsCount = decoder.decodeUInt32("NotificationsCount");
        Long _latePublishRequestCount = decoder.decodeUInt32("LatePublishRequestCount");
        Long _currentKeepAliveCount = decoder.decodeUInt32("CurrentKeepAliveCount");
        Long _currentLifetimeCount = decoder.decodeUInt32("CurrentLifetimeCount");
        Long _unacknowledgedMessageCount = decoder.decodeUInt32("UnacknowledgedMessageCount");
        Long _discardedMessageCount = decoder.decodeUInt32("DiscardedMessageCount");
        Long _monitoredItemCount = decoder.decodeUInt32("MonitoredItemCount");
        Long _disabledMonitoredItemCount = decoder.decodeUInt32("DisabledMonitoredItemCount");
        Long _monitoringQueueOverflowCount = decoder.decodeUInt32("MonitoringQueueOverflowCount");
        Long _nextSequenceNumber = decoder.decodeUInt32("NextSequenceNumber");
        Long _eventQueueOverFlowCount = decoder.decodeUInt32("EventQueueOverFlowCount");

        return new SubscriptionDiagnosticsDataType(_sessionId, _subscriptionId, _priority, _publishingInterval, _maxKeepAliveCount, _maxLifetimeCount, _maxNotificationsPerPublish, _publishingEnabled, _modifyCount, _enableCount, _disableCount, _republishRequestCount, _republishMessageRequestCount, _republishMessageCount, _transferRequestCount, _transferredToAltClientCount, _transferredToSameClientCount, _publishRequestCount, _dataChangeNotificationsCount, _eventNotificationsCount, _notificationsCount, _latePublishRequestCount, _currentKeepAliveCount, _currentLifetimeCount, _unacknowledgedMessageCount, _discardedMessageCount, _monitoredItemCount, _disabledMonitoredItemCount, _monitoringQueueOverflowCount, _nextSequenceNumber, _eventQueueOverFlowCount);
    }

    static {
        DelegateRegistry.registerEncoder(SubscriptionDiagnosticsDataType::encode, SubscriptionDiagnosticsDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SubscriptionDiagnosticsDataType::decode, SubscriptionDiagnosticsDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
