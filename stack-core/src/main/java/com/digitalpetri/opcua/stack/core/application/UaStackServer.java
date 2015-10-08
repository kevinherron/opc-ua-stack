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

package com.digitalpetri.opcua.stack.core.application;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.core.application.services.AttributeServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.DiscoveryServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.MethodServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.QueryServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequestHandler;
import com.digitalpetri.opcua.stack.core.application.services.SessionServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.TestServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ViewServiceSet;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
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

public interface UaStackServer {

    void startup();

    void shutdown();

    CertificateManager getCertificateManager();

    CertificateValidator getCertificateValidator();

    ChannelConfig getChannelConfig();

    ExecutorService getExecutorService();

    EndpointDescription[] getEndpointDescriptions();

    List<UserTokenPolicy> getUserTokenPolicies();

    ApplicationDescription getApplicationDescription();

    SignedSoftwareCertificate[] getSoftwareCertificates();

    ServerSecureChannel openSecureChannel();

    void closeSecureChannel(ServerSecureChannel secureChannel);

    <T extends UaRequestMessage, U extends UaResponseMessage>
    void addRequestHandler(Class<T> requestClass, ServiceRequestHandler<T, U> requestHandler);

    /**
     * Add an endpoint with the given security configuration. A certificate must be provided for secure endpoints.
     *
     * @param endpointUri     the endpoint URL.
     * @param bindAddress     the address to bind to.
     * @param certificate     the {@link X509Certificate} for this endpoint.
     * @param securityPolicy  the {@link SecurityPolicy} for this endpoint.
     * @param messageSecurity the {@link MessageSecurityMode} for this endpoint.
     * @return this {@link UaStackServer}.
     */
    UaStackServer addEndpoint(String endpointUri,
                              String bindAddress,
                              X509Certificate certificate,
                              SecurityPolicy securityPolicy,
                              MessageSecurityMode messageSecurity);

    /**
     * Add an endpoint with no certificate or security.
     *
     * @param endpointUri the endpoint URL.
     * @param bindAddress the address to bind to.
     * @return this {@link UaStackServer}.
     */
    default UaStackServer addEndpoint(String endpointUri, String bindAddress) {
        return addEndpoint(
                endpointUri, bindAddress, null,
                SecurityPolicy.None, MessageSecurityMode.None);
    }

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
