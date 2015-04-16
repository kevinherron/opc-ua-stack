package com.digitalpetri.opcua.stack.client.fsm;

public enum ConnectionStateEvent {

    CONNECT_REQUESTED,
    CONNECT_SUCCESS,
    CONNECT_FAILURE,
    DISCONNECT_REQUESTED,
    DISCONNECT_SUCCESS,
    CONNECTION_LOST

}
