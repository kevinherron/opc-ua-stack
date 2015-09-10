package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum DataChangeTrigger implements UaEnumeration {

    Status(0),
    StatusValue(1),
    StatusValueTimestamp(2);

    private final int value;

    private DataChangeTrigger(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, DataChangeTrigger> VALUES;

    static {
        Builder<Integer, DataChangeTrigger> builder = ImmutableMap.builder();
        for (DataChangeTrigger e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static DataChangeTrigger from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(DataChangeTrigger dataChangeTrigger, UaEncoder encoder) {
        encoder.encodeInt32(null, dataChangeTrigger.getValue());
    }

    public static DataChangeTrigger decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(DataChangeTrigger::encode, DataChangeTrigger.class);
        DelegateRegistry.registerDecoder(DataChangeTrigger::decode, DataChangeTrigger.class);
    }

}
