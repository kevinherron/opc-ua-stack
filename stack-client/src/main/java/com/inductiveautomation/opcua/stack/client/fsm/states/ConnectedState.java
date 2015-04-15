package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class ConnectedState implements ConnectionState {

    private final Channel channel;
    private final CompletableFuture<Channel> channelFuture;

    public ConnectedState(Channel channel, CompletableFuture<Channel> channelFuture) {
        this.channel = channel;
        this.channelFuture = channelFuture;
    }

    @Override
    public void activate(ConnectionStateEvent event, ConnectionStateContext context) {
        channelFuture.complete(channel);
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case DISCONNECT_REQUESTED:
                return new DisconnectingState(channelFuture);

            case CONNECTION_LOST:
                return new ReconnectingState(1);
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
