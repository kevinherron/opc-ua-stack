package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum IdType implements UaEnumeration {

    Numeric(0),
    String(1),
    Guid(2),
    Opaque(3);

    private final int value;

    private IdType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, IdType> VALUES;

    static {
        Builder<Integer, IdType> builder = ImmutableMap.builder();
        for (IdType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static IdType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(IdType idType, UaEncoder encoder) {
        encoder.encodeInt32(null, idType.getValue());
    }

    public static IdType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(IdType::encode, IdType.class);
        DelegateRegistry.registerDecoder(IdType::decode, IdType.class);
    }

}
