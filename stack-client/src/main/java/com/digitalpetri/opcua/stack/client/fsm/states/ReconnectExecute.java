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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionEvent;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateFsm;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconnectExecute implements ConnectionState {

    private static final int MAX_RECONNECT_DELAY_SECONDS = 16;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompletableFuture<ClientSecureChannel> channelFuture;

    private volatile ClientSecureChannel secureChannel;

    private final long delaySeconds;
    private final ClientSecureChannel existingChannel;

    public ReconnectExecute(CompletableFuture<ClientSecureChannel> channelFuture,
                            ClientSecureChannel existingChannel,
                            long delaySeconds) {

        this.channelFuture = channelFuture;
        this.existingChannel = existingChannel;
        this.delaySeconds = delaySeconds;
    }

    @Override
    public CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm) {
        if (!fsm.getClient().getConfig().isSecureChannelReauthenticationEnabled()) {
            existingChannel.setChannelId(0);
        }

        CompletableFuture<Void> future = new CompletableFuture<>();

        connect(fsm, true, new CompletableFuture<>()).whenComplete((sc, ex) -> {
            if (sc != null) {
                secureChannel = sc;
                fsm.handleEvent(ConnectionEvent.ReconnectSucceeded);
            } else {
                channelFuture.completeExceptionally(ex);
                fsm.handleEvent(ConnectionEvent.ReconnectFailed);
            }

            future.complete(null);
        });

        return future;
    }

    private CompletableFuture<ClientSecureChannel> connect(ConnectionStateFsm fsm,
                                                           boolean initialAttempt,
                                                           CompletableFuture<ClientSecureChannel> future) {

        logger.debug("Reconnecting...");

        UaTcpStackClient.bootstrap(fsm.getClient(), Optional.of(existingChannel)).whenComplete((sc, ex) -> {
            if (sc != null) {
                logger.debug("Channel bootstrap succeeded: localAddress={}, remoteAddress={}",
                        sc.getChannel().localAddress(), sc.getChannel().remoteAddress());

                future.complete(sc);
            } else {
                logger.debug("Channel bootstrap failed: {}", ex.getMessage(), ex);

                StatusCode statusCode = UaException.extract(ex)
                        .map(UaException::getStatusCode)
                        .orElse(StatusCode.BAD);

                boolean secureChannelError =
                        statusCode.getValue() == StatusCodes.Bad_TcpSecureChannelUnknown ||
                                statusCode.getValue() == StatusCodes.Bad_SecureChannelClosed ||
                                statusCode.getValue() == StatusCodes.Bad_SecureChannelIdInvalid ||
                                statusCode.getValue() == StatusCodes.Bad_ConnectionClosed;

                if (initialAttempt && secureChannelError) {
                    // Try again if bootstrapping failed because we couldn't re-open the previous channel.
                    logger.debug("Previous channel unusable, retrying...");

                    existingChannel.setChannelId(0);

                    connect(fsm, false, future);
                } else {
                    future.completeExceptionally(ex);
                }
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm) {
        return CF_VOID_COMPLETED;
    }

    @Override
    public ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm) {
        switch (event) {
            case DisconnectRequested:
                return new Disconnecting(secureChannel);

            case ReconnectFailed:
                return new ReconnectDelay(nextDelay(), existingChannel);

            case ReconnectSucceeded:
                return new Connected(secureChannel, channelFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<ClientSecureChannel> getSecureChannel() {
        return channelFuture;
    }

    private long nextDelay() {
        if (delaySeconds == 0) {
            return 1;
        } else {
            return Math.min(delaySeconds << 1, MAX_RECONNECT_DELAY_SECONDS);
        }
    }

    @Override
    public String toString() {
        return "ReconnectExecute{" +
                "delaySeconds=" + delaySeconds +
                ", secureChannelId=" + existingChannel.getChannelId() +
                '}';
    }

}
