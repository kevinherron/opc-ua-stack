package com.digitalpetri.opcua.stack.server;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.server.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CancelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.FindServersRequest;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.PublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.QueryFirstRequest;
import com.digitalpetri.opcua.stack.core.types.structured.QueryNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterServerRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackExRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.digitalpetri.opcua.stack.core.types.structured.WriteRequest;
import com.digitalpetri.opcua.stack.server.services.AttributeServiceSet;
import com.digitalpetri.opcua.stack.server.services.DiscoveryServiceSet;
import com.digitalpetri.opcua.stack.server.services.MethodServiceSet;
import com.digitalpetri.opcua.stack.server.services.MonitoredItemServiceSet;
import com.digitalpetri.opcua.stack.server.services.NodeManagementServiceSet;
import com.digitalpetri.opcua.stack.server.services.QueryServiceSet;
import com.digitalpetri.opcua.stack.server.services.ServiceRequestHandler;
import com.digitalpetri.opcua.stack.server.services.SessionServiceSet;
import com.digitalpetri.opcua.stack.server.services.SubscriptionServiceSet;
import com.digitalpetri.opcua.stack.server.services.TestServiceSet;
import com.digitalpetri.opcua.stack.server.services.ViewServiceSet;

public interface UaServer {

    void bind();

    void shutdown();

    Optional<KeyPair> getKeyPair(ByteString thumbprint);

    Optional<Certificate> getCertificate(ByteString thumbprint);

    ChannelConfig getChannelConfig();

    ExecutorService getExecutorService();

    EndpointDescription[] getEndpointDescriptions();

    List<UserTokenPolicy> getUserTokenPolicies();

    ApplicationDescription getApplicationDescription();

    SignedSoftwareCertificate[] getSoftwareCertificates();

    ServerSecureChannel openSecureChannel();

    void closeSecureChannel(ServerSecureChannel secureChannel);

    default UaServer addEndpoint(String endpointUri,
                                 SecurityPolicy securityPolicy,
                                 MessageSecurityMode messageSecurity) {

        return addEndpoint(endpointUri, EnumSet.of(securityPolicy), EnumSet.of(messageSecurity));
    }

    default UaServer addEndpoint(String endpointUri,
                                 String bindAddress,
                                 SecurityPolicy securityPolicy,
                                 MessageSecurityMode messageSecurity) {

        return addEndpoint(endpointUri, bindAddress, EnumSet.of(securityPolicy), EnumSet.of(messageSecurity));
    }

    default UaServer addEndpoint(String endpointUri,
                                 EnumSet<SecurityPolicy> securityPolicies,
                                 EnumSet<MessageSecurityMode> messageSecurityModes) {

        return addEndpoint(endpointUri, null, securityPolicies, messageSecurityModes);
    }

    UaServer addEndpoint(String endpointUri,
                         String bindAddress,
                         EnumSet<SecurityPolicy> securityPolicies,
                         EnumSet<MessageSecurityMode> messageSecurityModes);

    <T extends UaRequestMessage, U extends UaResponseMessage>
    void addRequestHandler(Class<T> requestClass, ServiceRequestHandler<T, U> requestHandler);

    default void addServiceSet(AttributeServiceSet serviceSet) {
        addRequestHandler(ReadRequest.class, serviceSet::onRead);
        addRequestHandler(WriteRequest.class, serviceSet::onWrite);
        addRequestHandler(HistoryReadRequest.class, serviceSet::onHistoryRead);
        addRequestHandler(HistoryUpdateRequest.class, serviceSet::onHistoryUpdate);
    }

    default void addServiceSet(DiscoveryServiceSet serviceSet) {
        addRequestHandler(GetEndpointsRequest.class, serviceSet::onGetEndpoints);
        addRequestHandler(FindServersRequest.class, serviceSet::onFindServers);
        addRequestHandler(RegisterServerRequest.class, serviceSet::onRegisterServer);
    }

    default void addServiceSet(QueryServiceSet serviceSet) {
        addRequestHandler(QueryFirstRequest.class, serviceSet::onQueryFirst);
        addRequestHandler(QueryNextRequest.class, serviceSet::onQueryNext);
    }

    default void addServiceSet(MethodServiceSet serviceSet) {
        addRequestHandler(CallRequest.class, serviceSet::onCall);
    }

    default void addServiceSet(MonitoredItemServiceSet serviceSet) {
        addRequestHandler(CreateMonitoredItemsRequest.class, serviceSet::onCreateMonitoredItems);
        addRequestHandler(ModifyMonitoredItemsRequest.class, serviceSet::onModifyMonitoredItems);
        addRequestHandler(DeleteMonitoredItemsRequest.class, serviceSet::onDeleteMonitoredItems);
        addRequestHandler(SetMonitoringModeRequest.class, serviceSet::onSetMonitoringMode);
        addRequestHandler(SetTriggeringRequest.class, serviceSet::onSetTriggering);
    }

    default void addServiceSet(NodeManagementServiceSet serviceSet) {
        addRequestHandler(AddNodesRequest.class, serviceSet::onAddNodes);
        addRequestHandler(DeleteNodesRequest.class, serviceSet::onDeleteNodes);
        addRequestHandler(AddReferencesRequest.class, serviceSet::onAddReferences);
        addRequestHandler(DeleteReferencesRequest.class, serviceSet::onDeleteReferences);
    }

    default void addServiceSet(SessionServiceSet serviceSet) {
        addRequestHandler(CreateSessionRequest.class, serviceSet::onCreateSession);
        addRequestHandler(ActivateSessionRequest.class, serviceSet::onActivateSession);
        addRequestHandler(CloseSessionRequest.class, serviceSet::onCloseSession);
        addRequestHandler(CancelRequest.class, serviceSet::onCancel);
    }

    default void addServiceSet(SubscriptionServiceSet serviceSet) {
        addRequestHandler(CreateSubscriptionRequest.class, serviceSet::onCreateSubscription);
        addRequestHandler(ModifySubscriptionRequest.class, serviceSet::onModifySubscription);
        addRequestHandler(DeleteSubscriptionsRequest.class, serviceSet::onDeleteSubscriptions);
        addRequestHandler(TransferSubscriptionsRequest.class, serviceSet::onTransferSubscriptions);
        addRequestHandler(SetPublishingModeRequest.class, serviceSet::onSetPublishingMode);
        addRequestHandler(PublishRequest.class, serviceSet::onPublish);
        addRequestHandler(RepublishRequest.class, serviceSet::onRepublish);
    }

    default void addServiceSet(TestServiceSet serviceSet) {
        addRequestHandler(TestStackRequest.class, serviceSet::onTestStack);
        addRequestHandler(TestStackExRequest.class, serviceSet::onTestStackEx);
    }

    default void addServiceSet(ViewServiceSet serviceSet) {
        addRequestHandler(BrowseRequest.class, serviceSet::onBrowse);
        addRequestHandler(BrowseNextRequest.class, serviceSet::onBrowseNext);
        addRequestHandler(TranslateBrowsePathsToNodeIdsRequest.class, serviceSet::onTranslateBrowsePaths);
        addRequestHandler(RegisterNodesRequest.class, serviceSet::onRegisterNodes);
        addRequestHandler(UnregisterNodesRequest.class, serviceSet::onUnregisterNodes);
    }

}
