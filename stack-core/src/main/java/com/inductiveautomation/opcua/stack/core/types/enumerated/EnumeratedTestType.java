package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum EnumeratedTestType implements UaEnumeration {

    Red(1),
    Yellow(4),
    Green(5);

    private final int value;

    private EnumeratedTestType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, EnumeratedTestType> VALUES;

    static {
        Builder<Integer, EnumeratedTestType> builder = ImmutableMap.builder();
        for (EnumeratedTestType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static EnumeratedTestType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(EnumeratedTestType enumeratedTestType, UaEncoder encoder) {
        encoder.encodeInt32(null, enumeratedTestType.getValue());
    }

    public static EnumeratedTestType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(EnumeratedTestType::encode, EnumeratedTestType.class);
        DelegateRegistry.registerDecoder(EnumeratedTestType::decode, EnumeratedTestType.class);
    }

}
