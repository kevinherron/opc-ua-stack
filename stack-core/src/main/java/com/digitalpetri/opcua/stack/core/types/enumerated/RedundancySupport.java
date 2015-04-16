package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum RedundancySupport implements UaEnumeration {

    None(0),
    Cold(1),
    Warm(2),
    Hot(3),
    Transparent(4),
    HotAndMirrored(5);

    private final int value;

    private RedundancySupport(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, RedundancySupport> VALUES;

    static {
        Builder<Integer, RedundancySupport> builder = ImmutableMap.builder();
        for (RedundancySupport e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static RedundancySupport from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(RedundancySupport redundancySupport, UaEncoder encoder) {
        encoder.encodeInt32(null, redundancySupport.getValue());
    }

    public static RedundancySupport decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(RedundancySupport::encode, RedundancySupport.class);
        DelegateRegistry.registerDecoder(RedundancySupport::decode, RedundancySupport.class);
    }

}
