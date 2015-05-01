package com.digitalpetri.opcua.stack.client.fsm;

public interface ConnectionStateObserver {

    /**
     * The connection has been opened and a secure channel has been established.
     */
    default void onConnectionEstablished() {}

    /**
     * The connection was lost.
     */
    default void onConnectionLost() {}

}
