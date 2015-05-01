package com.digitalpetri.opcua.stack.client.fsm;

import java.util.concurrent.CompletableFuture;

import io.netty.channel.Channel;

public interface ConnectionState {

    /**
     * Activate this state.
     *
     * @param fromEvent the {@link ConnectionStateEvent} that caused this state to be activated.
     * @param context   the {@link ConnectionStateContext}.
     */
    void activate(ConnectionStateEvent fromEvent, ConnectionStateContext context);

    /**
     * Given {@code event}, return the next {@link ConnectionState}.
     *
     * @param event   the {@link ConnectionStateEvent}.
     * @param context the {@link ConnectionStateContext}.
     * @return the next {@link ConnectionState}.
     */
    ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context);

    /**
     * @return the {@link CompletableFuture} for the {@link Channel}.
     */
    CompletableFuture<Channel> getChannelFuture();

}
