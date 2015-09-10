package com.digitalpetri.opcua.stack.core.application.services;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackExRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackExResponse;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TestStackResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public interface TestServiceSet {

    default void onTestStack(ServiceRequest<TestStackRequest, TestStackResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onTestStackEx(ServiceRequest<TestStackExRequest, TestStackExResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
