package com.inductiveautomation.opcua.stack.core.application.services;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteResponse;

public interface AttributeServiceSet {

    default void onRead(ServiceRequest<ReadRequest, ReadResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onWrite(ServiceRequest<WriteRequest, WriteResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onHistoryRead(ServiceRequest<HistoryReadRequest, HistoryReadResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onHistoryUpdate(ServiceRequest<HistoryUpdateRequest, HistoryUpdateResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
