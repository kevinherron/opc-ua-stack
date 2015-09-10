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
