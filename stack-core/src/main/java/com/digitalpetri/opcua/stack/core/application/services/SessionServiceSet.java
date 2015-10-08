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
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CancelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CancelResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSessionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public interface SessionServiceSet {

    default void onCreateSession(ServiceRequest<CreateSessionRequest, CreateSessionResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onActivateSession(ServiceRequest<ActivateSessionRequest, ActivateSessionResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onCloseSession(ServiceRequest<CloseSessionRequest, CloseSessionResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

    default void onCancel(ServiceRequest<CancelRequest, CancelResponse> serviceRequest) throws UaException {
        serviceRequest.setServiceFault(Bad_ServiceUnsupported);
    }

}
