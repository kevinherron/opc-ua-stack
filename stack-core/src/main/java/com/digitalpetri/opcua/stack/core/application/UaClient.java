package com.digitalpetri.opcua.stack.core.application;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;

public interface UaClient {

    CompletableFuture<UaClient> connect();

    CompletableFuture<UaClient> disconnect();

    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    void sendRequests(List<? extends UaRequestMessage> requests,
                      List<CompletableFuture<? extends UaResponseMessage>> futures);

}
