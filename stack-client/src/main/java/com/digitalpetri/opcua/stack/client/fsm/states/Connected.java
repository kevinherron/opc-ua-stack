package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class Connected implements ConnectionState {

    private final CompletableFuture<Channel> future;
    private final Channel channel;

    public Connected(CompletableFuture<Channel> future, Channel channel) {
        this.future = future;
        this.channel = channel;
    }

    @Override
    public void activate(ConnectionStateEvent fromEvent, ConnectionStateContext context) {
        future.complete(channel);
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case DISCONNECT_REQUESTED:
                return new Disconnecting(future);

            case ERR_CONNECTION_LOST:
                return new Reconnecting(new CompletableFuture<>(), 0);
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return future;
    }
}
