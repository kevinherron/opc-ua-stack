package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum AxisScaleEnumeration implements UaEnumeration {

    Linear(0),
    Log(1),
    Ln(2);

    private final int value;

    private AxisScaleEnumeration(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, AxisScaleEnumeration> VALUES;

    static {
        Builder<Integer, AxisScaleEnumeration> builder = ImmutableMap.builder();
        for (AxisScaleEnumeration e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static AxisScaleEnumeration from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(AxisScaleEnumeration axisScaleEnumeration, UaEncoder encoder) {
        encoder.encodeInt32(null, axisScaleEnumeration.getValue());
    }

    public static AxisScaleEnumeration decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(AxisScaleEnumeration::encode, AxisScaleEnumeration.class);
        DelegateRegistry.registerDecoder(AxisScaleEnumeration::decode, AxisScaleEnumeration.class);
    }

}
