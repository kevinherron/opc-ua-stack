package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum MonitoringMode implements UaEnumeration {

    Disabled(0),
    Sampling(1),
    Reporting(2);

    private final int value;

    private MonitoringMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, MonitoringMode> VALUES;

    static {
        Builder<Integer, MonitoringMode> builder = ImmutableMap.builder();
        for (MonitoringMode e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static void encode(MonitoringMode monitoringMode, UaEncoder encoder) {
        encoder.encodeInt32(null, monitoringMode.getValue());
    }

    public static MonitoringMode decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringMode::encode, MonitoringMode.class);
        DelegateRegistry.registerDecoder(MonitoringMode::decode, MonitoringMode.class);
    }

}
