package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.FindServersRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.FindServersResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.GetEndpointsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterServerRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterServerResponse;

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
