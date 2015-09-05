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
