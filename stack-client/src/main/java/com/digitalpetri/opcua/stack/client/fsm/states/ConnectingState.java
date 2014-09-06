package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class ConnectingState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture;

    public ConnectingState(CompletableFuture<Channel> channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case ConnectSuccess:
                return new ConnectedState(channelFuture);

            case ConnectFailure:
                return new DisconnectedState();

            case DisconnectRequested:
                channelFuture.thenAccept(ch -> ch.close());

                return new DisconnectedState();

            default:
                return context.getState();
        }
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }
}
