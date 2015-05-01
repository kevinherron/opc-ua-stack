package com.digitalpetri.opcua.stack.client.fsm;

public enum ConnectionStateEvent {

    CONNECT_REQUESTED,
    CONNECT_SUCCESS,

    DISCONNECT_REQUESTED,
    DISCONNECT_SUCCESS,

    ERR_CONNECT_FAILED,
    ERR_CONNECTION_LOST

}
