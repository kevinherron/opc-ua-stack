package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum DeadbandType implements UaEnumeration {

    None(0),
    Absolute(1),
    Percent(2);

    private final int value;

    private DeadbandType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, DeadbandType> VALUES;

    static {
        Builder<Integer, DeadbandType> builder = ImmutableMap.builder();
        for (DeadbandType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static DeadbandType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(DeadbandType deadbandType, UaEncoder encoder) {
        encoder.encodeInt32(null, deadbandType.getValue());
    }

    public static DeadbandType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(DeadbandType::encode, DeadbandType.class);
        DelegateRegistry.registerDecoder(DeadbandType::decode, DeadbandType.class);
    }

}
