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

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextResponse;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesResponse;

public interface ViewServiceSet {

    default void onBrowse(ServiceRequest<BrowseRequest, BrowseResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onBrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onTranslateBrowsePaths(ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onRegisterNodes(ServiceRequest<RegisterNodesRequest, RegisterNodesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    default void onUnregisterNodes(ServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

}
