package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class ConnectingState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture;
    private volatile Channel channel;

    public ConnectingState(CompletableFuture<Channel> channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public void activate(ConnectionStateEvent event, ConnectionStateContext context) {
        UaTcpStackClient.bootstrap(context.getClient()).whenComplete((channel, ex) -> {
            if (channel != null) {
                this.channel = channel;
                context.handleEvent(ConnectionStateEvent.CONNECT_SUCCESS);
            } else {
                this.channelFuture.completeExceptionally(ex);
                context.handleEvent(ConnectionStateEvent.CONNECT_FAILURE);
            }
        });
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_SUCCESS:
                return new ConnectedState(channel, channelFuture);

            case CONNECT_FAILURE:
                return new ReconnectingState(1);

            case DISCONNECT_REQUESTED:
                return new DisconnectingState(channelFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
