package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum DeadbandType implements UaEnumeration {

    None(0),
    Absolute(1),
    Percent(2);

    private final int value;

    private DeadbandType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(DeadbandType deadbandType, UaEncoder encoder) {
        encoder.encodeInt32(null, deadbandType.ordinal());
    }

    public static DeadbandType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return DeadbandType.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(DeadbandType::encode, DeadbandType.class);
        DelegateRegistry.registerDecoder(DeadbandType::decode, DeadbandType.class);
    }

}
