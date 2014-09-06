package com.digitalpetri.opcua.stack.client.fsm;

import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.client.UaTcpClient;
import com.digitalpetri.opcua.stack.client.fsm.states.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.states.DisconnectedState;

public class ConnectionStateContext {

    private final AtomicReference<ConnectionState> state = new AtomicReference<>(new DisconnectedState());

    private final UaTcpClient client;

    public ConnectionStateContext(UaTcpClient client) {
        this.client = client;
    }

    public synchronized ConnectionState handleEvent(ConnectionStateEvent event) {
        ConnectionState currState = state.get();
        ConnectionState nextState = currState.transition(event, this);

        state.set(nextState);

        return nextState;
    }

    public UaTcpClient getClient() {
        return client;
    }

    public ConnectionState getState() {
        return state.get();
    }

}
