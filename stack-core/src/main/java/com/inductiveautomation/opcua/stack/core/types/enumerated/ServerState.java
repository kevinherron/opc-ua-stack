package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

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

    private static final ImmutableMap<Integer, ServerState> VALUES;

    static {
        Builder<Integer, ServerState> builder = ImmutableMap.builder();
        for (ServerState e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static void encode(ServerState serverState, UaEncoder encoder) {
        encoder.encodeInt32(null, serverState.getValue());
    }

    public static ServerState decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(ServerState::encode, ServerState.class);
        DelegateRegistry.registerDecoder(ServerState::decode, ServerState.class);
    }

}
