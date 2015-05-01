package com.digitalpetri.opcua.stack.client.fsm;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.states.Connected;
import com.digitalpetri.opcua.stack.client.fsm.states.Disconnected;
import com.digitalpetri.opcua.stack.core.util.ExecutionQueue;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.collect.Lists.newCopyOnWriteArrayList;

public class ConnectionStateContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<ConnectionStateObserver> stateObservers =
            newCopyOnWriteArrayList();

    private final AtomicReference<ConnectionState> state =
            new AtomicReference<>(new Disconnected());

    private final ExecutionQueue notificationQueue;

    private final UaTcpStackClient client;

    public ConnectionStateContext(UaTcpStackClient client) {
        this.client = client;

        notificationQueue = new ExecutionQueue(client.getExecutorService());
    }

    public synchronized void handleEvent(ConnectionStateEvent event) {
        ConnectionState currState = state.get();
        ConnectionState nextState = currState.transition(event, this);

        logger.debug("S({}) x E({}) = S'({})",
                currState.getClass().getSimpleName(), event, nextState.getClass().getSimpleName());

        if (nextState != currState) {
            state.set(nextState);
            nextState.activate(event, this);

            if (event == ConnectionStateEvent.ERR_CONNECTION_LOST) {
                notifyConnectionLost();
            } else if (event == ConnectionStateEvent.CONNECT_SUCCESS) {
                notifyConnectionEstablished();
            }
        }
    }

    private void notifyConnectionEstablished() {
        notificationQueue.submit(() ->
                stateObservers.forEach(ConnectionStateObserver::onConnectionEstablished));
    }

    private void notifyConnectionLost() {
        notificationQueue.submit(() ->
                stateObservers.forEach(ConnectionStateObserver::onConnectionLost));
    }

    public synchronized CompletableFuture<Channel> getChannelFuture() {
        return state.get().getChannelFuture();
    }

    public boolean isConnected() {
        return state.get() instanceof Connected;
    }

    public UaTcpStackClient getClient() {
        return client;
    }

    public void addStateObserver(ConnectionStateObserver observer) {
        stateObservers.add(observer);
    }

    public void removeStateObserver(ConnectionStateObserver observer) {
        stateObservers.remove(observer);
    }

}
