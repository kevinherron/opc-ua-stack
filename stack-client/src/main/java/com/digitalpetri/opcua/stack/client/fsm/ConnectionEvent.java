package com.digitalpetri.opcua.stack.client.fsm;

public enum ConnectionEvent {

    CONNECT_FAILED,
    CONNECT_REQUESTED,
    CONNECT_SUCCEEDED,
    CONNECTION_LOST,
    DISCONNECT_REQUESTED,
    DISCONNECT_SUCCEEDED,
    RECONNECT_FAILED,
    RECONNECT_SUCCEEDED

}
