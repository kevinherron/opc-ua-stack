package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesResponse;

public interface ViewServiceSet {

    default void onBrowse(ServiceRequest<BrowseRequest, BrowseResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onBrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onTranslateBrowsePaths(ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onRegisterNodes(ServiceRequest<RegisterNodesRequest, RegisterNodesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onUnregisterNodes(ServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
