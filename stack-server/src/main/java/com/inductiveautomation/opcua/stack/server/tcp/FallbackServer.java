/*
 * Copyright 2014
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

package com.inductiveautomation.opcua.stack.server.tcp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequestHandler;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.FindServersRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.FindServersResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;

/**
 * Provides a "fallback" server for when a UA TCP Hello contains an unknown endpoint URL.
 */
public class FallbackServer {

    private final Set<UaTcpServer> registered = Sets.newConcurrentHashSet();
    private final Map<String, UaTcpServer> servers = Maps.newConcurrentMap();

    private final UaTcpServer server;

    public FallbackServer() {
        server = new UaTcpServerBuilder()
                .setApplicationName(LocalizedText.english("Stack Discovery Server"))
                .setApplicationUri("urn:inductiveautomation:stack:discovery")
                .setProductUri("http://www.inductiveautomation.com/opc-ua/stack")
                .build();

        server.addRequestHandler(FindServersRequest.class, new FindServersHandler());
        server.addRequestHandler(GetEndpointsRequest.class, new GetEndpointsHandler());
    }

    public void registerServer(UaTcpServer server) {
        if (registered.add(server)) {
            server.getDiscoveryUrls().forEach(url -> servers.put(url, server));
        }
    }

    public void unregisterServer(UaTcpServer server) {
        if (registered.remove(server)) {
            server.getDiscoveryUrls().forEach(servers::remove);
        }
    }

    public UaTcpServer getServer() {
        return server;
    }

    private class GetEndpointsHandler implements ServiceRequestHandler<GetEndpointsRequest, GetEndpointsResponse> {

        @Override
        public void handle(ServiceRequest<GetEndpointsRequest, GetEndpointsResponse> service) throws UaException {
            GetEndpointsRequest request = service.getRequest();

            String endpointUrl = request.getEndpointUrl();
            if (endpointUrl == null) endpointUrl = "";

            UaTcpServer server = servers.get(endpointUrl);

            EndpointDescription[] endpoints = (server != null) ?
                    server.getEndpointDescriptions() :
                    new EndpointDescription[0];

            List<String> profileUris = request.getProfileUris() != null ?
                    Lists.newArrayList(request.getProfileUris()) :
                    Lists.newArrayList();

            EndpointDescription[] filtered = Arrays.stream(endpoints)
                    .filter(ed -> filterProfileUris(ed, profileUris))
                    .filter(this::filterEndpointUrls)
                    .toArray(EndpointDescription[]::new);

            service.setResponse(new GetEndpointsResponse(
                    service.createResponseHeader(),
                    filtered
            ));
        }

        private boolean filterProfileUris(EndpointDescription endpoint, List<String> profileUris) {
            return profileUris.size() == 0 || profileUris.contains(endpoint.getTransportProfileUri());
        }

        private boolean filterEndpointUrls(EndpointDescription endpoint) {
            return true;
        }

    }

    private class FindServersHandler implements ServiceRequestHandler<FindServersRequest, FindServersResponse> {

        @Override
        public void handle(ServiceRequest<FindServersRequest, FindServersResponse> service) throws UaException {
            FindServersRequest request = service.getRequest();

            List<ApplicationDescription> servers = Lists.newArrayList();
            List<String> serverUris = Lists.newArrayList(request.getServerUris());

            for (UaTcpServer server : registered) {
                ApplicationDescription description = server.getApplicationDescription();

                if (serverUris.isEmpty()) {
                    servers.add(description);
                } else {
                    if (serverUris.contains(description.getApplicationUri())) {
                        servers.add(description);
                    }
                }
            }

            ResponseHeader header = service.createResponseHeader();
            FindServersResponse response = new FindServersResponse(
                    header, servers.toArray(new ApplicationDescription[servers.size()]));

            service.setResponse(response);
        }

    }

}
