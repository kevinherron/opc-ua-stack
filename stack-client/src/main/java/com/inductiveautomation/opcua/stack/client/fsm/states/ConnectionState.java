package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public interface ConnectionState {

    /**
     * Given {@code event}, return the next {@link ConnectionState}.
     *
     * @param event   the {@link ConnectionStateEvent}.
     * @param context the {@link ConnectionStateContext}.
     * @return the next {@link ConnectionState}.
     */
    ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context);

    /**
     * @return the {@link CompletableFuture} holding the eventual {@link Channel}.
     */
    CompletableFuture<Channel> getChannelFuture();

}
