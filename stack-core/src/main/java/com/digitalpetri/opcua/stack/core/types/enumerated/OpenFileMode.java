package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum OpenFileMode implements UaEnumeration {

    Read(1),
    Write(2),
    EraseExisiting(4),
    Append(8);

    private final int value;

    private OpenFileMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, OpenFileMode> VALUES;

    static {
        Builder<Integer, OpenFileMode> builder = ImmutableMap.builder();
        for (OpenFileMode e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static OpenFileMode from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(OpenFileMode openFileMode, UaEncoder encoder) {
        encoder.encodeInt32(null, openFileMode.getValue());
    }

    public static OpenFileMode decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(OpenFileMode::encode, OpenFileMode.class);
        DelegateRegistry.registerDecoder(OpenFileMode::decode, OpenFileMode.class);
    }

}
