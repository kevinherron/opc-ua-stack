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
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.WriteRequest;
import com.digitalpetri.opcua.stack.core.types.structured.WriteResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

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
