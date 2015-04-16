package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class DisconnectedState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture = new CompletableFuture<>();

    @Override
    public void activate(ConnectionStateEvent event, ConnectionStateContext context) {

    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_REQUESTED:
                return new ConnectingState(channelFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
