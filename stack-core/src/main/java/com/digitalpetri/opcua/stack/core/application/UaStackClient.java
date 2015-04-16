package com.digitalpetri.opcua.stack.core.application;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;

public interface UaStackClient {

    CompletableFuture<UaStackClient> connect();

    CompletableFuture<UaStackClient> disconnect();

    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    void sendRequests(List<? extends UaRequestMessage> requests,
                      List<CompletableFuture<? extends UaResponseMessage>> futures);

    ApplicationDescription getApplication();

    Optional<KeyPair> getKeyPair();

    Optional<X509Certificate> getCertificate();

    ClientSecureChannel getSecureChannel();

    String getEndpointUrl();

    Optional<EndpointDescription> getEndpoint();

    ChannelConfig getChannelConfig();

    UInteger getChannelLifetime();

    ExecutorService getExecutorService();

}
