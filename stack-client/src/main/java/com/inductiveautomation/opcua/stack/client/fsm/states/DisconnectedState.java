package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.UaTcpStackClient;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public class DisconnectedState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture = new CompletableFuture<>();

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_REQUESTED:
                CompletableFuture<Channel> channelFuture = UaTcpStackClient.bootstrap(context.getClient());

                channelFuture.whenCompleteAsync((ch, ex) -> {
                    if (ch != null) {
                        context.handleEvent(ConnectionStateEvent.CONNECT_SUCCESS);
                    } else {
                        context.handleEvent(ConnectionStateEvent.CONNECT_FAILURE);
                    }
                }, context.getClient().getExecutorService());

                return new ConnectingState(channelFuture);

            default:
                return context.getState();
        }
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
