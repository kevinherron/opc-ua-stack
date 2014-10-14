package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum SecurityTokenRequestType implements UaEnumeration {

    Issue(0),
    Renew(1);

    private final int value;

    private SecurityTokenRequestType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(SecurityTokenRequestType securityTokenRequestType, UaEncoder encoder) {
        encoder.encodeInt32(null, securityTokenRequestType.ordinal());
    }

    public static SecurityTokenRequestType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return value < values().length ? values()[value] : null;
    }

    static {
        DelegateRegistry.registerEncoder(SecurityTokenRequestType::encode, SecurityTokenRequestType.class);
        DelegateRegistry.registerDecoder(SecurityTokenRequestType::decode, SecurityTokenRequestType.class);
    }

}
