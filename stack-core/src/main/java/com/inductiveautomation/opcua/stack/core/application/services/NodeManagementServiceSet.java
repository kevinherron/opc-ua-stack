package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesResponse;

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
