package com.digitalpetri.opcua.stack.core.application.services;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.CallRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallResponse;

public interface MethodServiceSet {

    default void onCall(ServiceRequest<CallRequest, CallResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
