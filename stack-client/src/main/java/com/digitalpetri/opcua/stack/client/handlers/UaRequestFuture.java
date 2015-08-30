package com.digitalpetri.opcua.stack.client.handlers;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;

public class UaRequestFuture {

    private final UaRequestMessage request;
    private final CompletableFuture<UaResponseMessage> future;

    public UaRequestFuture(UaRequestMessage request) {
        this(request, new CompletableFuture<>());
    }

    public UaRequestFuture(UaRequestMessage request, CompletableFuture<UaResponseMessage> future) {
        this.request = request;
        this.future = future;
    }

    public UaRequestMessage getRequest() {
        return request;
    }

    public CompletableFuture<UaResponseMessage> getFuture() {
        return future;
    }

}
