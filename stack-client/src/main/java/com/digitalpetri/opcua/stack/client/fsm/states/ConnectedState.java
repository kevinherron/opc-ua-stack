package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.UaTcpClient;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class ConnectedState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture;

    public ConnectedState(CompletableFuture<Channel> channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch(event) {
            case DisconnectRequested:
                channelFuture.thenAccept(ch -> ch.close());

                return new DisconnectedState();

            case ConnectionLost:
                CompletableFuture<Channel> channelFuture = UaTcpClient.bootstrap(context.getClient());

                channelFuture.whenCompleteAsync((ch, ex) -> {
                    if (ch != null) {
                        context.handleEvent(ConnectionStateEvent.ConnectSuccess);
                    } else {
                        context.handleEvent(ConnectionStateEvent.ConnectFailure);
                    }
                }, context.getClient().getExecutor());

                return new ReconnectingState(channelFuture);

            default:
                return context.getState();
        }
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
