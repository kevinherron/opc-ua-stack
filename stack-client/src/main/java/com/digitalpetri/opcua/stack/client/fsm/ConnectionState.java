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

package com.digitalpetri.opcua.stack.client.fsm;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;

public interface ConnectionState {

    CompletableFuture<Void> CF_VOID_COMPLETED = CompletableFuture.completedFuture(null);

    CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm);

    CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm);

    ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm);

    CompletableFuture<ClientSecureChannel> getSecureChannel();

}
