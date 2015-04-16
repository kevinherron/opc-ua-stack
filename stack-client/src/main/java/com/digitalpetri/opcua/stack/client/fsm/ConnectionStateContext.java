package com.digitalpetri.opcua.stack.client.fsm;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.states.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.states.DisconnectedState;
import com.digitalpetri.opcua.stack.core.util.ExecutionQueue;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionStateContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<ConnectionState> state = new AtomicReference<>(new DisconnectedState());

    private final ExecutionQueue queue;

    private final UaTcpStackClient client;

    public ConnectionStateContext(UaTcpStackClient client, ExecutorService executor) {
        this.client = client;

        queue = new ExecutionQueue(executor);
    }

    public synchronized void handleEvent(ConnectionStateEvent event) {
        ConnectionState currState = state.get();
        ConnectionState nextState = currState.transition(event, this);

        state.set(nextState);

        logger.debug("S({}) x E({}) = S'({})",
                currState.getClass().getSimpleName(), event, nextState.getClass().getSimpleName());

        if (nextState != currState) {
            nextState.activate(event, this);
        }
    }

    public UaTcpStackClient getClient() {
        return client;
    }

    public synchronized CompletableFuture<Channel> getChannel() {
        if (state.get() instanceof DisconnectedState) {
            handleEvent(ConnectionStateEvent.CONNECT_REQUESTED);
        }
        return state.get().getChannelFuture();
    }

    public ConnectionState getState() {
        return state.get();
    }

}
