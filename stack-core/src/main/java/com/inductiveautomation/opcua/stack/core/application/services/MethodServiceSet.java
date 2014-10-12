package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.CallRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallResponse;

public interface MethodServiceSet {

    default void onCall(ServiceRequest<CallRequest, CallResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
