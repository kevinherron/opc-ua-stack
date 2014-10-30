package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum ComplianceLevel implements UaEnumeration {

    Untested(0),
    Partial(1),
    SelfTested(2),
    Certified(3);

    private final int value;

    private ComplianceLevel(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, ComplianceLevel> VALUES;

    static {
        Builder<Integer, ComplianceLevel> builder = ImmutableMap.builder();
        for (ComplianceLevel e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static ComplianceLevel from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(ComplianceLevel complianceLevel, UaEncoder encoder) {
        encoder.encodeInt32(null, complianceLevel.getValue());
    }

    public static ComplianceLevel decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(ComplianceLevel::encode, ComplianceLevel.class);
        DelegateRegistry.registerDecoder(ComplianceLevel::decode, ComplianceLevel.class);
    }

}
