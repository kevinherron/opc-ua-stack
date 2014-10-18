package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum ApplicationType implements UaEnumeration {

    Server(0),
    Client(1),
    ClientAndServer(2),
    DiscoveryServer(3);

    private final int value;

    private ApplicationType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, ApplicationType> VALUES;

    static {
        Builder<Integer, ApplicationType> builder = ImmutableMap.builder();
        for (ApplicationType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static void encode(ApplicationType applicationType, UaEncoder encoder) {
        encoder.encodeInt32(null, applicationType.getValue());
    }

    public static ApplicationType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(ApplicationType::encode, ApplicationType.class);
        DelegateRegistry.registerDecoder(ApplicationType::decode, ApplicationType.class);
    }

}
