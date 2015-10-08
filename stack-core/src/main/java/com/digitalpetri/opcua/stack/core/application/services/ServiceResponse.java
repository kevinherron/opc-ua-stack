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

import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

public class ServiceResponse {

    private final UaRequestMessage request;
    private final UaResponseMessage response;
    private final long requestId;
    private final boolean serviceFault;

    public ServiceResponse(UaRequestMessage request, long requestId, UaResponseMessage response) {
        this.request = request;
        this.requestId = requestId;
        this.response = response;
        this.serviceFault = false;
    }

    public ServiceResponse(UaRequestMessage request, long requestId, ServiceFault serviceFault) {
        this.request = request;
        this.requestId = requestId;
        this.response = serviceFault;
        this.serviceFault = true;
    }

    public UaRequestMessage getRequest() {
        return request;
    }

    public long getRequestId() {
        return requestId;
    }

    public UaResponseMessage getResponse() {
        return response;
    }

    public boolean isServiceFault() {
        return serviceFault;
    }

    @Override
    public String toString() {
        ToStringHelper helper = MoreObjects.toStringHelper(this)
                .add("requestId", requestId)
                .add("request", request.getClass().getSimpleName())
                .add("response", response.getClass().getSimpleName());

        if (serviceFault) {
            helper.add("result", response.getResponseHeader().getServiceResult());
        }

        return helper.toString();
    }

}
