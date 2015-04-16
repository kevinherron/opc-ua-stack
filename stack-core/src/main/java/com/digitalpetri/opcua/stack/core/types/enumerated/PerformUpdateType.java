package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum PerformUpdateType implements UaEnumeration {

    Insert(1),
    Replace(2),
    Update(3),
    Remove(4);

    private final int value;

    private PerformUpdateType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, PerformUpdateType> VALUES;

    static {
        Builder<Integer, PerformUpdateType> builder = ImmutableMap.builder();
        for (PerformUpdateType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static PerformUpdateType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(PerformUpdateType performUpdateType, UaEncoder encoder) {
        encoder.encodeInt32(null, performUpdateType.getValue());
    }

    public static PerformUpdateType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(PerformUpdateType::encode, PerformUpdateType.class);
        DelegateRegistry.registerDecoder(PerformUpdateType::decode, PerformUpdateType.class);
    }

}
