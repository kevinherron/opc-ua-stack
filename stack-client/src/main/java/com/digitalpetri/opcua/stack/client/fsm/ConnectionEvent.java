package com.digitalpetri.opcua.stack.client.fsm;

public enum ConnectionEvent {

    ConnectFailed,
    ConnectRequested,
    ConnectSucceeded,
    ConnectionLost,
    DisconnectRequested,
    DisconnectSucceeded,
    ReconnectRequested,
    ReconnectFailed,
    ReconnectSucceeded

}
