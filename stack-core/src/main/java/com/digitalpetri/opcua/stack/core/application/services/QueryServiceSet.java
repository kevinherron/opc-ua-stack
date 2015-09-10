package com.digitalpetri.opcua.stack.core.application.services;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.QueryFirstRequest;
import com.digitalpetri.opcua.stack.core.types.structured.QueryFirstResponse;
import com.digitalpetri.opcua.stack.core.types.structured.QueryNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.QueryNextResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public interface QueryServiceSet {

    default void onQueryFirst(ServiceRequest<QueryFirstRequest, QueryFirstResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onQueryNext(ServiceRequest<QueryNextRequest, QueryNextResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
