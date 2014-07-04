package com.digitalpetri.opcua.stack.server.services;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.*;
import com.digitalpetri.opcua.stack.server.ServiceRequest;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public interface NodeManagementServiceSet {

    default void onAddNodes(ServiceRequest<AddNodesRequest, AddNodesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onDeleteNodes(ServiceRequest<DeleteNodesRequest, DeleteNodesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onAddReferences(ServiceRequest<AddReferencesRequest, AddReferencesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onDeleteReferences(ServiceRequest<DeleteReferencesRequest, DeleteReferencesResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
