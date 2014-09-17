package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum ServerState implements UaEnumeration {

    Running(0),
    Failed(1),
    NoConfiguration(2),
    Suspended(3),
    Shutdown(4),
    Test(5),
    CommunicationFault(6),
    Unknown(7);

    private final int value;

    private ServerState(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(ServerState serverState, UaEncoder encoder) {
        encoder.encodeInt32(null, serverState.ordinal());
    }

    public static ServerState decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return ServerState.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(ServerState::encode, ServerState.class);
        DelegateRegistry.registerDecoder(ServerState::decode, ServerState.class);
    }

}
