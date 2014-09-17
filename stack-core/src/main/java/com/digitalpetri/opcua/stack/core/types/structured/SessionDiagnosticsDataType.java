package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class SessionDiagnosticsDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.SessionDiagnosticsDataType;
    public static final NodeId BinaryEncodingId = Identifiers.SessionDiagnosticsDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SessionDiagnosticsDataType_Encoding_DefaultXml;

    protected final NodeId _sessionId;
    protected final String _sessionName;
    protected final ApplicationDescription _clientDescription;
    protected final String _serverUri;
    protected final String _endpointUrl;
    protected final String[] _localeIds;
    protected final Double _actualSessionTimeout;
    protected final Long _maxResponseMessageSize;
    protected final DateTime _clientConnectionTime;
    protected final DateTime _clientLastContactTime;
    protected final Long _currentSubscriptionsCount;
    protected final Long _currentMonitoredItemsCount;
    protected final Long _currentPublishRequestsInQueue;
    protected final ServiceCounterDataType _totalRequestCount;
    protected final Long _unauthorizedRequestCount;
    protected final ServiceCounterDataType _readCount;
    protected final ServiceCounterDataType _historyReadCount;
    protected final ServiceCounterDataType _writeCount;
    protected final ServiceCounterDataType _historyUpdateCount;
    protected final ServiceCounterDataType _callCount;
    protected final ServiceCounterDataType _createMonitoredItemsCount;
    protected final ServiceCounterDataType _modifyMonitoredItemsCount;
    protected final ServiceCounterDataType _setMonitoringModeCount;
    protected final ServiceCounterDataType _setTriggeringCount;
    protected final ServiceCounterDataType _deleteMonitoredItemsCount;
    protected final ServiceCounterDataType _createSubscriptionCount;
    protected final ServiceCounterDataType _modifySubscriptionCount;
    protected final ServiceCounterDataType _setPublishingModeCount;
    protected final ServiceCounterDataType _publishCount;
    protected final ServiceCounterDataType _republishCount;
    protected final ServiceCounterDataType _transferSubscriptionsCount;
    protected final ServiceCounterDataType _deleteSubscriptionsCount;
    protected final ServiceCounterDataType _addNodesCount;
    protected final ServiceCounterDataType _addReferencesCount;
    protected final ServiceCounterDataType _deleteNodesCount;
    protected final ServiceCounterDataType _deleteReferencesCount;
    protected final ServiceCounterDataType _browseCount;
    protected final ServiceCounterDataType _browseNextCount;
    protected final ServiceCounterDataType _translateBrowsePathsToNodeIdsCount;
    protected final ServiceCounterDataType _queryFirstCount;
    protected final ServiceCounterDataType _queryNextCount;
    protected final ServiceCounterDataType _registerNodesCount;
    protected final ServiceCounterDataType _unregisterNodesCount;

    public SessionDiagnosticsDataType(NodeId _sessionId, String _sessionName, ApplicationDescription _clientDescription, String _serverUri, String _endpointUrl, String[] _localeIds, Double _actualSessionTimeout, Long _maxResponseMessageSize, DateTime _clientConnectionTime, DateTime _clientLastContactTime, Long _currentSubscriptionsCount, Long _currentMonitoredItemsCount, Long _currentPublishRequestsInQueue, ServiceCounterDataType _totalRequestCount, Long _unauthorizedRequestCount, ServiceCounterDataType _readCount, ServiceCounterDataType _historyReadCount, ServiceCounterDataType _writeCount, ServiceCounterDataType _historyUpdateCount, ServiceCounterDataType _callCount, ServiceCounterDataType _createMonitoredItemsCount, ServiceCounterDataType _modifyMonitoredItemsCount, ServiceCounterDataType _setMonitoringModeCount, ServiceCounterDataType _setTriggeringCount, ServiceCounterDataType _deleteMonitoredItemsCount, ServiceCounterDataType _createSubscriptionCount, ServiceCounterDataType _modifySubscriptionCount, ServiceCounterDataType _setPublishingModeCount, ServiceCounterDataType _publishCount, ServiceCounterDataType _republishCount, ServiceCounterDataType _transferSubscriptionsCount, ServiceCounterDataType _deleteSubscriptionsCount, ServiceCounterDataType _addNodesCount, ServiceCounterDataType _addReferencesCount, ServiceCounterDataType _deleteNodesCount, ServiceCounterDataType _deleteReferencesCount, ServiceCounterDataType _browseCount, ServiceCounterDataType _browseNextCount, ServiceCounterDataType _translateBrowsePathsToNodeIdsCount, ServiceCounterDataType _queryFirstCount, ServiceCounterDataType _queryNextCount, ServiceCounterDataType _registerNodesCount, ServiceCounterDataType _unregisterNodesCount) {
        this._sessionId = _sessionId;
        this._sessionName = _sessionName;
        this._clientDescription = _clientDescription;
        this._serverUri = _serverUri;
        this._endpointUrl = _endpointUrl;
        this._localeIds = _localeIds;
        this._actualSessionTimeout = _actualSessionTimeout;
        this._maxResponseMessageSize = _maxResponseMessageSize;
        this._clientConnectionTime = _clientConnectionTime;
        this._clientLastContactTime = _clientLastContactTime;
        this._currentSubscriptionsCount = _currentSubscriptionsCount;
        this._currentMonitoredItemsCount = _currentMonitoredItemsCount;
        this._currentPublishRequestsInQueue = _currentPublishRequestsInQueue;
        this._totalRequestCount = _totalRequestCount;
        this._unauthorizedRequestCount = _unauthorizedRequestCount;
        this._readCount = _readCount;
        this._historyReadCount = _historyReadCount;
        this._writeCount = _writeCount;
        this._historyUpdateCount = _historyUpdateCount;
        this._callCount = _callCount;
        this._createMonitoredItemsCount = _createMonitoredItemsCount;
        this._modifyMonitoredItemsCount = _modifyMonitoredItemsCount;
        this._setMonitoringModeCount = _setMonitoringModeCount;
        this._setTriggeringCount = _setTriggeringCount;
        this._deleteMonitoredItemsCount = _deleteMonitoredItemsCount;
        this._createSubscriptionCount = _createSubscriptionCount;
        this._modifySubscriptionCount = _modifySubscriptionCount;
        this._setPublishingModeCount = _setPublishingModeCount;
        this._publishCount = _publishCount;
        this._republishCount = _republishCount;
        this._transferSubscriptionsCount = _transferSubscriptionsCount;
        this._deleteSubscriptionsCount = _deleteSubscriptionsCount;
        this._addNodesCount = _addNodesCount;
        this._addReferencesCount = _addReferencesCount;
        this._deleteNodesCount = _deleteNodesCount;
        this._deleteReferencesCount = _deleteReferencesCount;
        this._browseCount = _browseCount;
        this._browseNextCount = _browseNextCount;
        this._translateBrowsePathsToNodeIdsCount = _translateBrowsePathsToNodeIdsCount;
        this._queryFirstCount = _queryFirstCount;
        this._queryNextCount = _queryNextCount;
        this._registerNodesCount = _registerNodesCount;
        this._unregisterNodesCount = _unregisterNodesCount;
    }

    public NodeId getSessionId() { return _sessionId; }

    public String getSessionName() { return _sessionName; }

    public ApplicationDescription getClientDescription() { return _clientDescription; }

    public String getServerUri() { return _serverUri; }

    public String getEndpointUrl() { return _endpointUrl; }

    public String[] getLocaleIds() { return _localeIds; }

    public Double getActualSessionTimeout() { return _actualSessionTimeout; }

    public Long getMaxResponseMessageSize() { return _maxResponseMessageSize; }

    public DateTime getClientConnectionTime() { return _clientConnectionTime; }

    public DateTime getClientLastContactTime() { return _clientLastContactTime; }

    public Long getCurrentSubscriptionsCount() { return _currentSubscriptionsCount; }

    public Long getCurrentMonitoredItemsCount() { return _currentMonitoredItemsCount; }

    public Long getCurrentPublishRequestsInQueue() { return _currentPublishRequestsInQueue; }

    public ServiceCounterDataType getTotalRequestCount() { return _totalRequestCount; }

    public Long getUnauthorizedRequestCount() { return _unauthorizedRequestCount; }

    public ServiceCounterDataType getReadCount() { return _readCount; }

    public ServiceCounterDataType getHistoryReadCount() { return _historyReadCount; }

    public ServiceCounterDataType getWriteCount() { return _writeCount; }

    public ServiceCounterDataType getHistoryUpdateCount() { return _historyUpdateCount; }

    public ServiceCounterDataType getCallCount() { return _callCount; }

    public ServiceCounterDataType getCreateMonitoredItemsCount() { return _createMonitoredItemsCount; }

    public ServiceCounterDataType getModifyMonitoredItemsCount() { return _modifyMonitoredItemsCount; }

    public ServiceCounterDataType getSetMonitoringModeCount() { return _setMonitoringModeCount; }

    public ServiceCounterDataType getSetTriggeringCount() { return _setTriggeringCount; }

    public ServiceCounterDataType getDeleteMonitoredItemsCount() { return _deleteMonitoredItemsCount; }

    public ServiceCounterDataType getCreateSubscriptionCount() { return _createSubscriptionCount; }

    public ServiceCounterDataType getModifySubscriptionCount() { return _modifySubscriptionCount; }

    public ServiceCounterDataType getSetPublishingModeCount() { return _setPublishingModeCount; }

    public ServiceCounterDataType getPublishCount() { return _publishCount; }

    public ServiceCounterDataType getRepublishCount() { return _republishCount; }

    public ServiceCounterDataType getTransferSubscriptionsCount() { return _transferSubscriptionsCount; }

    public ServiceCounterDataType getDeleteSubscriptionsCount() { return _deleteSubscriptionsCount; }

    public ServiceCounterDataType getAddNodesCount() { return _addNodesCount; }

    public ServiceCounterDataType getAddReferencesCount() { return _addReferencesCount; }

    public ServiceCounterDataType getDeleteNodesCount() { return _deleteNodesCount; }

    public ServiceCounterDataType getDeleteReferencesCount() { return _deleteReferencesCount; }

    public ServiceCounterDataType getBrowseCount() { return _browseCount; }

    public ServiceCounterDataType getBrowseNextCount() { return _browseNextCount; }

    public ServiceCounterDataType getTranslateBrowsePathsToNodeIdsCount() { return _translateBrowsePathsToNodeIdsCount; }

    public ServiceCounterDataType getQueryFirstCount() { return _queryFirstCount; }

    public ServiceCounterDataType getQueryNextCount() { return _queryNextCount; }

    public ServiceCounterDataType getRegisterNodesCount() { return _registerNodesCount; }

    public ServiceCounterDataType getUnregisterNodesCount() { return _unregisterNodesCount; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SessionDiagnosticsDataType sessionDiagnosticsDataType, UaEncoder encoder) {
        encoder.encodeNodeId("SessionId", sessionDiagnosticsDataType._sessionId);
        encoder.encodeString("SessionName", sessionDiagnosticsDataType._sessionName);
        encoder.encodeSerializable("ClientDescription", sessionDiagnosticsDataType._clientDescription);
        encoder.encodeString("ServerUri", sessionDiagnosticsDataType._serverUri);
        encoder.encodeString("EndpointUrl", sessionDiagnosticsDataType._endpointUrl);
        encoder.encodeArray("LocaleIds", sessionDiagnosticsDataType._localeIds, encoder::encodeString);
        encoder.encodeDouble("ActualSessionTimeout", sessionDiagnosticsDataType._actualSessionTimeout);
        encoder.encodeUInt32("MaxResponseMessageSize", sessionDiagnosticsDataType._maxResponseMessageSize);
        encoder.encodeDateTime("ClientConnectionTime", sessionDiagnosticsDataType._clientConnectionTime);
        encoder.encodeDateTime("ClientLastContactTime", sessionDiagnosticsDataType._clientLastContactTime);
        encoder.encodeUInt32("CurrentSubscriptionsCount", sessionDiagnosticsDataType._currentSubscriptionsCount);
        encoder.encodeUInt32("CurrentMonitoredItemsCount", sessionDiagnosticsDataType._currentMonitoredItemsCount);
        encoder.encodeUInt32("CurrentPublishRequestsInQueue", sessionDiagnosticsDataType._currentPublishRequestsInQueue);
        encoder.encodeSerializable("TotalRequestCount", sessionDiagnosticsDataType._totalRequestCount);
        encoder.encodeUInt32("UnauthorizedRequestCount", sessionDiagnosticsDataType._unauthorizedRequestCount);
        encoder.encodeSerializable("ReadCount", sessionDiagnosticsDataType._readCount);
        encoder.encodeSerializable("HistoryReadCount", sessionDiagnosticsDataType._historyReadCount);
        encoder.encodeSerializable("WriteCount", sessionDiagnosticsDataType._writeCount);
        encoder.encodeSerializable("HistoryUpdateCount", sessionDiagnosticsDataType._historyUpdateCount);
        encoder.encodeSerializable("CallCount", sessionDiagnosticsDataType._callCount);
        encoder.encodeSerializable("CreateMonitoredItemsCount", sessionDiagnosticsDataType._createMonitoredItemsCount);
        encoder.encodeSerializable("ModifyMonitoredItemsCount", sessionDiagnosticsDataType._modifyMonitoredItemsCount);
        encoder.encodeSerializable("SetMonitoringModeCount", sessionDiagnosticsDataType._setMonitoringModeCount);
        encoder.encodeSerializable("SetTriggeringCount", sessionDiagnosticsDataType._setTriggeringCount);
        encoder.encodeSerializable("DeleteMonitoredItemsCount", sessionDiagnosticsDataType._deleteMonitoredItemsCount);
        encoder.encodeSerializable("CreateSubscriptionCount", sessionDiagnosticsDataType._createSubscriptionCount);
        encoder.encodeSerializable("ModifySubscriptionCount", sessionDiagnosticsDataType._modifySubscriptionCount);
        encoder.encodeSerializable("SetPublishingModeCount", sessionDiagnosticsDataType._setPublishingModeCount);
        encoder.encodeSerializable("PublishCount", sessionDiagnosticsDataType._publishCount);
        encoder.encodeSerializable("RepublishCount", sessionDiagnosticsDataType._republishCount);
        encoder.encodeSerializable("TransferSubscriptionsCount", sessionDiagnosticsDataType._transferSubscriptionsCount);
        encoder.encodeSerializable("DeleteSubscriptionsCount", sessionDiagnosticsDataType._deleteSubscriptionsCount);
        encoder.encodeSerializable("AddNodesCount", sessionDiagnosticsDataType._addNodesCount);
        encoder.encodeSerializable("AddReferencesCount", sessionDiagnosticsDataType._addReferencesCount);
        encoder.encodeSerializable("DeleteNodesCount", sessionDiagnosticsDataType._deleteNodesCount);
        encoder.encodeSerializable("DeleteReferencesCount", sessionDiagnosticsDataType._deleteReferencesCount);
        encoder.encodeSerializable("BrowseCount", sessionDiagnosticsDataType._browseCount);
        encoder.encodeSerializable("BrowseNextCount", sessionDiagnosticsDataType._browseNextCount);
        encoder.encodeSerializable("TranslateBrowsePathsToNodeIdsCount", sessionDiagnosticsDataType._translateBrowsePathsToNodeIdsCount);
        encoder.encodeSerializable("QueryFirstCount", sessionDiagnosticsDataType._queryFirstCount);
        encoder.encodeSerializable("QueryNextCount", sessionDiagnosticsDataType._queryNextCount);
        encoder.encodeSerializable("RegisterNodesCount", sessionDiagnosticsDataType._registerNodesCount);
        encoder.encodeSerializable("UnregisterNodesCount", sessionDiagnosticsDataType._unregisterNodesCount);
    }

    public static SessionDiagnosticsDataType decode(UaDecoder decoder) {
        NodeId _sessionId = decoder.decodeNodeId("SessionId");
        String _sessionName = decoder.decodeString("SessionName");
        ApplicationDescription _clientDescription = decoder.decodeSerializable("ClientDescription", ApplicationDescription.class);
        String _serverUri = decoder.decodeString("ServerUri");
        String _endpointUrl = decoder.decodeString("EndpointUrl");
        String[] _localeIds = decoder.decodeArray("LocaleIds", decoder::decodeString, String.class);
        Double _actualSessionTimeout = decoder.decodeDouble("ActualSessionTimeout");
        Long _maxResponseMessageSize = decoder.decodeUInt32("MaxResponseMessageSize");
        DateTime _clientConnectionTime = decoder.decodeDateTime("ClientConnectionTime");
        DateTime _clientLastContactTime = decoder.decodeDateTime("ClientLastContactTime");
        Long _currentSubscriptionsCount = decoder.decodeUInt32("CurrentSubscriptionsCount");
        Long _currentMonitoredItemsCount = decoder.decodeUInt32("CurrentMonitoredItemsCount");
        Long _currentPublishRequestsInQueue = decoder.decodeUInt32("CurrentPublishRequestsInQueue");
        ServiceCounterDataType _totalRequestCount = decoder.decodeSerializable("TotalRequestCount", ServiceCounterDataType.class);
        Long _unauthorizedRequestCount = decoder.decodeUInt32("UnauthorizedRequestCount");
        ServiceCounterDataType _readCount = decoder.decodeSerializable("ReadCount", ServiceCounterDataType.class);
        ServiceCounterDataType _historyReadCount = decoder.decodeSerializable("HistoryReadCount", ServiceCounterDataType.class);
        ServiceCounterDataType _writeCount = decoder.decodeSerializable("WriteCount", ServiceCounterDataType.class);
        ServiceCounterDataType _historyUpdateCount = decoder.decodeSerializable("HistoryUpdateCount", ServiceCounterDataType.class);
        ServiceCounterDataType _callCount = decoder.decodeSerializable("CallCount", ServiceCounterDataType.class);
        ServiceCounterDataType _createMonitoredItemsCount = decoder.decodeSerializable("CreateMonitoredItemsCount", ServiceCounterDataType.class);
        ServiceCounterDataType _modifyMonitoredItemsCount = decoder.decodeSerializable("ModifyMonitoredItemsCount", ServiceCounterDataType.class);
        ServiceCounterDataType _setMonitoringModeCount = decoder.decodeSerializable("SetMonitoringModeCount", ServiceCounterDataType.class);
        ServiceCounterDataType _setTriggeringCount = decoder.decodeSerializable("SetTriggeringCount", ServiceCounterDataType.class);
        ServiceCounterDataType _deleteMonitoredItemsCount = decoder.decodeSerializable("DeleteMonitoredItemsCount", ServiceCounterDataType.class);
        ServiceCounterDataType _createSubscriptionCount = decoder.decodeSerializable("CreateSubscriptionCount", ServiceCounterDataType.class);
        ServiceCounterDataType _modifySubscriptionCount = decoder.decodeSerializable("ModifySubscriptionCount", ServiceCounterDataType.class);
        ServiceCounterDataType _setPublishingModeCount = decoder.decodeSerializable("SetPublishingModeCount", ServiceCounterDataType.class);
        ServiceCounterDataType _publishCount = decoder.decodeSerializable("PublishCount", ServiceCounterDataType.class);
        ServiceCounterDataType _republishCount = decoder.decodeSerializable("RepublishCount", ServiceCounterDataType.class);
        ServiceCounterDataType _transferSubscriptionsCount = decoder.decodeSerializable("TransferSubscriptionsCount", ServiceCounterDataType.class);
        ServiceCounterDataType _deleteSubscriptionsCount = decoder.decodeSerializable("DeleteSubscriptionsCount", ServiceCounterDataType.class);
        ServiceCounterDataType _addNodesCount = decoder.decodeSerializable("AddNodesCount", ServiceCounterDataType.class);
        ServiceCounterDataType _addReferencesCount = decoder.decodeSerializable("AddReferencesCount", ServiceCounterDataType.class);
        ServiceCounterDataType _deleteNodesCount = decoder.decodeSerializable("DeleteNodesCount", ServiceCounterDataType.class);
        ServiceCounterDataType _deleteReferencesCount = decoder.decodeSerializable("DeleteReferencesCount", ServiceCounterDataType.class);
        ServiceCounterDataType _browseCount = decoder.decodeSerializable("BrowseCount", ServiceCounterDataType.class);
        ServiceCounterDataType _browseNextCount = decoder.decodeSerializable("BrowseNextCount", ServiceCounterDataType.class);
        ServiceCounterDataType _translateBrowsePathsToNodeIdsCount = decoder.decodeSerializable("TranslateBrowsePathsToNodeIdsCount", ServiceCounterDataType.class);
        ServiceCounterDataType _queryFirstCount = decoder.decodeSerializable("QueryFirstCount", ServiceCounterDataType.class);
        ServiceCounterDataType _queryNextCount = decoder.decodeSerializable("QueryNextCount", ServiceCounterDataType.class);
        ServiceCounterDataType _registerNodesCount = decoder.decodeSerializable("RegisterNodesCount", ServiceCounterDataType.class);
        ServiceCounterDataType _unregisterNodesCount = decoder.decodeSerializable("UnregisterNodesCount", ServiceCounterDataType.class);

        return new SessionDiagnosticsDataType(_sessionId, _sessionName, _clientDescription, _serverUri, _endpointUrl, _localeIds, _actualSessionTimeout, _maxResponseMessageSize, _clientConnectionTime, _clientLastContactTime, _currentSubscriptionsCount, _currentMonitoredItemsCount, _currentPublishRequestsInQueue, _totalRequestCount, _unauthorizedRequestCount, _readCount, _historyReadCount, _writeCount, _historyUpdateCount, _callCount, _createMonitoredItemsCount, _modifyMonitoredItemsCount, _setMonitoringModeCount, _setTriggeringCount, _deleteMonitoredItemsCount, _createSubscriptionCount, _modifySubscriptionCount, _setPublishingModeCount, _publishCount, _republishCount, _transferSubscriptionsCount, _deleteSubscriptionsCount, _addNodesCount, _addReferencesCount, _deleteNodesCount, _deleteReferencesCount, _browseCount, _browseNextCount, _translateBrowsePathsToNodeIdsCount, _queryFirstCount, _queryNextCount, _registerNodesCount, _unregisterNodesCount);
    }

    static {
        DelegateRegistry.registerEncoder(SessionDiagnosticsDataType::encode, SessionDiagnosticsDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SessionDiagnosticsDataType::decode, SessionDiagnosticsDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
