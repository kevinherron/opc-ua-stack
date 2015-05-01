package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import io.netty.channel.Channel;

public class Disconnected implements ConnectionState {

    private final CompletableFuture<Channel> future = new CompletableFuture<>();

    public Disconnected() {
        future.completeExceptionally(new UaException(StatusCodes.Bad_ServerNotConnected, "not connected"));
    }

    @Override
    public void activate(ConnectionStateEvent fromEvent, ConnectionStateContext context) {}

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_REQUESTED:
                return new Connecting(new CompletableFuture<>());
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return future;
    }

}
