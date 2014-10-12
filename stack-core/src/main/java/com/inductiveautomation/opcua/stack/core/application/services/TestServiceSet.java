package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackExRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackExResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackResponse;

public interface TestServiceSet {

    default void onTestStack(ServiceRequest<TestStackRequest, TestStackResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onTestStackEx(ServiceRequest<TestStackExRequest, TestStackExResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
