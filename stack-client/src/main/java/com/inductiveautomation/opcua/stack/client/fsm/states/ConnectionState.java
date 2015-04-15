package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import io.netty.channel.Channel;

public interface ConnectionState {

    /**
     * Activate this state.
     *
     * @param event   the {@link ConnectionStateEvent} that caused this state to be activated.
     * @param context the {@link ConnectionStateContext}.
     */
    void activate(ConnectionStateEvent event, ConnectionStateContext context);

    /**
     * Given {@code event}, return the next {@link ConnectionState}.
     *
     * @param event   the {@link ConnectionStateEvent}.
     * @param context the {@link ConnectionStateContext}.
     * @return the next {@link ConnectionState}.
     */
    ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context);

    /**
     * @return the {@link CompletableFuture} holding the {@link Channel} for this client connection.
     */
    CompletableFuture<Channel> getChannelFuture();

}
