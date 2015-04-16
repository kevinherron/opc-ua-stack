package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum HistoryUpdateType implements UaEnumeration {

    Insert(1),
    Replace(2),
    Update(3),
    Delete(4);

    private final int value;

    private HistoryUpdateType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, HistoryUpdateType> VALUES;

    static {
        Builder<Integer, HistoryUpdateType> builder = ImmutableMap.builder();
        for (HistoryUpdateType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static HistoryUpdateType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(HistoryUpdateType historyUpdateType, UaEncoder encoder) {
        encoder.encodeInt32(null, historyUpdateType.getValue());
    }

    public static HistoryUpdateType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryUpdateType::encode, HistoryUpdateType.class);
        DelegateRegistry.registerDecoder(HistoryUpdateType::decode, HistoryUpdateType.class);
    }

}
