package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

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

    public static void encode(HistoryUpdateType historyUpdateType, UaEncoder encoder) {
        encoder.encodeInt32(null, historyUpdateType.ordinal());
    }

    public static HistoryUpdateType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return HistoryUpdateType.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(HistoryUpdateType::encode, HistoryUpdateType.class);
        DelegateRegistry.registerDecoder(HistoryUpdateType::decode, HistoryUpdateType.class);
    }

}
