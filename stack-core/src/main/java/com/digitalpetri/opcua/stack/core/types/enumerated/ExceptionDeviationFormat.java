package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum ExceptionDeviationFormat implements UaEnumeration {

    AbsoluteValue(0),
    PercentOfRange(1),
    PercentOfValue(2),
    PercentOfEURange(3),
    Unknown(4);

    private final int value;

    private ExceptionDeviationFormat(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, ExceptionDeviationFormat> VALUES;

    static {
        Builder<Integer, ExceptionDeviationFormat> builder = ImmutableMap.builder();
        for (ExceptionDeviationFormat e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static ExceptionDeviationFormat from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(ExceptionDeviationFormat exceptionDeviationFormat, UaEncoder encoder) {
        encoder.encodeInt32(null, exceptionDeviationFormat.getValue());
    }

    public static ExceptionDeviationFormat decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(ExceptionDeviationFormat::encode, ExceptionDeviationFormat.class);
        DelegateRegistry.registerDecoder(ExceptionDeviationFormat::decode, ExceptionDeviationFormat.class);
    }

}
