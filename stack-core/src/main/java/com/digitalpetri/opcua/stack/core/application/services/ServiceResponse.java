package com.digitalpetri.opcua.stack.core.application.services;

import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.google.common.base.MoreObjects;

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
