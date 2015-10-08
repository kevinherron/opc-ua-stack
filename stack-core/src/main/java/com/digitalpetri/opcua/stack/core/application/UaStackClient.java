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

public interface UaStackClient {

    CompletableFuture<UaStackClient> connect();

    CompletableFuture<UaStackClient> disconnect();

    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    void sendRequests(List<? extends UaRequestMessage> requests,
                      List<CompletableFuture<? extends UaResponseMessage>> futures);

    ApplicationDescription getApplication();

    Optional<KeyPair> getKeyPair();

    Optional<X509Certificate> getCertificate();

    String getEndpointUrl();

    Optional<EndpointDescription> getEndpoint();

    ChannelConfig getChannelConfig();

    UInteger getChannelLifetime();

    ExecutorService getExecutorService();

}
