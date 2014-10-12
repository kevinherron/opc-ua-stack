package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum IdType implements UaEnumeration {

    Numeric(0),
    String(1),
    Guid(2),
    Opaque(3);

    private final int value;

    private IdType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(IdType idType, UaEncoder encoder) {
        encoder.encodeInt32(null, idType.ordinal());
    }

    public static IdType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return IdType.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(IdType::encode, IdType.class);
        DelegateRegistry.registerDecoder(IdType::decode, IdType.class);
    }

}
