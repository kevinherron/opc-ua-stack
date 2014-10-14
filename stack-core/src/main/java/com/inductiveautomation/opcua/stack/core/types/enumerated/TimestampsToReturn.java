package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum TimestampsToReturn implements UaEnumeration {

    Source(0),
    Server(1),
    Both(2),
    Neither(3);

    private final int value;

    private TimestampsToReturn(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(TimestampsToReturn timestampsToReturn, UaEncoder encoder) {
        encoder.encodeInt32(null, timestampsToReturn.ordinal());
    }

    public static TimestampsToReturn decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return value < values().length ? values()[value] : null;
    }

    static {
        DelegateRegistry.registerEncoder(TimestampsToReturn::encode, TimestampsToReturn.class);
        DelegateRegistry.registerDecoder(TimestampsToReturn::decode, TimestampsToReturn.class);
    }

}
