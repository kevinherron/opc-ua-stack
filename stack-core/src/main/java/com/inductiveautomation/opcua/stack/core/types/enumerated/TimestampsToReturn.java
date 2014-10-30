package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum TimestampsToReturn implements UaEnumeration {

    Source(0),
    Server(1),
    Both(2),
    Neither(3);

    private final int value;

    private TimestampsToReturn(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, TimestampsToReturn> VALUES;

    static {
        Builder<Integer, TimestampsToReturn> builder = ImmutableMap.builder();
        for (TimestampsToReturn e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static TimestampsToReturn from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(TimestampsToReturn timestampsToReturn, UaEncoder encoder) {
        encoder.encodeInt32(null, timestampsToReturn.getValue());
    }

    public static TimestampsToReturn decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(TimestampsToReturn::encode, TimestampsToReturn.class);
        DelegateRegistry.registerDecoder(TimestampsToReturn::decode, TimestampsToReturn.class);
    }

}
