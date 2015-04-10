package com.inductiveautomation.opcua.stack.core.application;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.channel.ClientSecureChannel;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;

public interface UaClient {

    CompletableFuture<UaClient> connect();

    CompletableFuture<UaClient> disconnect();

    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    void sendRequests(List<? extends UaRequestMessage> requests,
                      List<CompletableFuture<? extends UaResponseMessage>> futures);

    ApplicationDescription getApplication();

    Optional<KeyPair> getKeyPair();

    Optional<Certificate> getCertificate();

    ClientSecureChannel getSecureChannel();

    EndpointDescription getEndpoint();

    String getEndpointUrl();

    long getRequestTimeout();

    ChannelConfig getChannelConfig();

    ExecutorService getExecutorService();

}
