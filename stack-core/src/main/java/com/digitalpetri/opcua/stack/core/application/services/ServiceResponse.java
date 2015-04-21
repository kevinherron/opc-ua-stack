package com.digitalpetri.opcua.stack.core.application.services;

import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

public class ServiceResponse {

    private final UaResponseMessage response;
    private final long requestId;
    private final boolean serviceFault;

    public ServiceResponse(UaResponseMessage response, long requestId) {
        this.response = response;
        this.requestId = requestId;
        this.serviceFault = false;
    }

    public ServiceResponse(ServiceFault serviceFault, long requestId) {
        this.response = serviceFault;
        this.requestId = requestId;
        this.serviceFault = true;
    }

    public UaResponseMessage getResponse() {
        return response;
    }

    public long getRequestId() {
        return requestId;
    }

    public boolean isServiceFault() {
        return serviceFault;
    }

    @Override
    public String toString() {
        ToStringHelper helper = MoreObjects.toStringHelper(this)
                .add("requestId", requestId)
                .add("response", response.getClass().getSimpleName());

        if (serviceFault) {
            helper.add("result", response.getResponseHeader().getServiceResult());
        }

        return helper.toString();
    }

}
