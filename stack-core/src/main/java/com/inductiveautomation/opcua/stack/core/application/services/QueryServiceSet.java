package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextResponse;

public interface QueryServiceSet {

    default void onQueryFirst(ServiceRequest<QueryFirstRequest, QueryFirstResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onQueryNext(ServiceRequest<QueryNextRequest, QueryNextResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
