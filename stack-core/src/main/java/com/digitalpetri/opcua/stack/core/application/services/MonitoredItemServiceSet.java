/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
