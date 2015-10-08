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
