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

package com.digitalpetri.opcua.stack.core.application.services;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.FindServersRequest;
import com.digitalpetri.opcua.stack.core.types.structured.FindServersResponse;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.GetEndpointsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterServerRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterServerResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public interface DiscoveryServiceSet {

    default void onFindServers(ServiceRequest<FindServersRequest, FindServersResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onGetEndpoints(ServiceRequest<GetEndpointsRequest, GetEndpointsResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onRegisterServer(ServiceRequest<RegisterServerRequest, RegisterServerResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
