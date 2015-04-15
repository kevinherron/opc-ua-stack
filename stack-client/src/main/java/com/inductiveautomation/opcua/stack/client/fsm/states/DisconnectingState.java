/*
 * Copyright 2015
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

package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.Channel;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class DisconnectingState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture;

    public DisconnectingState(CompletableFuture<Channel> channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public void activate(ConnectionStateEvent event, ConnectionStateContext context) {
        channelFuture.thenAccept(channel -> {
            RequestHeader requestHeader = new RequestHeader(
                    NodeId.NULL_VALUE, DateTime.now(), uint(0), uint(0), null, uint(0), null);

            CloseSecureChannelRequest request = new CloseSecureChannelRequest(requestHeader);

            channel.pipeline().fireUserEventTriggered(request);

        });

        context.handleEvent(ConnectionStateEvent.DISCONNECT_SUCCESS);
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case DISCONNECT_SUCCESS:
                return new DisconnectedState();
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
