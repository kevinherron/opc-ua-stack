package com.inductiveautomation.opcua.stack.core.application.services;

import com.google.common.base.MoreObjects;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;

public class ServiceResponse {

    private final UaResponseMessage response;
    private final long requestId;

    public ServiceResponse(UaResponseMessage response, long requestId) {
        this.response = response;
        this.requestId = requestId;
    }

    public UaResponseMessage getResponse() {
        return response;
    }

    public long getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("requestId", requestId)
                .add("response", response.getClass().getSimpleName())
                .toString();
    }

}
