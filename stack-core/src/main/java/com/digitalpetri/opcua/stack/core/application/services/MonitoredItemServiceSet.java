package com.digitalpetri.opcua.stack.core.application.services;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public interface MonitoredItemServiceSet {

    default void onCreateMonitoredItems(ServiceRequest<CreateMonitoredItemsRequest, CreateMonitoredItemsResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onModifyMonitoredItems(ServiceRequest<ModifyMonitoredItemsRequest, ModifyMonitoredItemsResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onDeleteMonitoredItems(ServiceRequest<DeleteMonitoredItemsRequest, DeleteMonitoredItemsResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onSetMonitoringMode(ServiceRequest<SetMonitoringModeRequest, SetMonitoringModeResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onSetTriggering(ServiceRequest<SetTriggeringRequest, SetTriggeringResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
