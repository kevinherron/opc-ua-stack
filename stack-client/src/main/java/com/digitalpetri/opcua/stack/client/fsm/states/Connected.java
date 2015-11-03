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

package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionEvent;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateFsm;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connected implements ConnectionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile ChannelInboundHandlerAdapter inactivityListener;

    private final ClientSecureChannel secureChannel;
    private final CompletableFuture<ClientSecureChannel> channelFuture;

    public Connected(ClientSecureChannel secureChannel, CompletableFuture<ClientSecureChannel> channelFuture) {
        this.secureChannel = secureChannel;
        this.channelFuture = channelFuture;
    }

    @Override
    public CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm) {
        inactivityListener = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                logger.warn("Channel went inactive: {}", ctx.channel());

                fsm.handleEvent(ConnectionEvent.ConnectionLost);

                super.channelInactive(ctx);
            }
        };

        secureChannel.getChannel().pipeline().addLast(inactivityListener);

        channelFuture.complete(secureChannel);

        return CF_VOID_COMPLETED;
    }

    @Override
    public CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm) {
        if (secureChannel != null && inactivityListener != null) {
            secureChannel.getChannel().pipeline().remove(inactivityListener);
            logger.debug("Removed inactivityListener");
        }

        return CF_VOID_COMPLETED;
    }

    @Override
    public ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm) {
        switch (event) {
            case ConnectionLost:
                return new ReconnectDelay(0L, secureChannel);

            case DisconnectRequested:
                return new Disconnecting(secureChannel);
        }

        return this;
    }

    @Override
    public CompletableFuture<ClientSecureChannel> getSecureChannel() {
        return channelFuture;
    }

    @Override
    public String toString() {
        return "Connected{}";
    }

}
