package com.digitalpetri.opcua.stack.core.application.services;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesResponse;

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
