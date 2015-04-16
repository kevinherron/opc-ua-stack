package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

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

    private static final ImmutableMap<Integer, SecurityTokenRequestType> VALUES;

    static {
        Builder<Integer, SecurityTokenRequestType> builder = ImmutableMap.builder();
        for (SecurityTokenRequestType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static SecurityTokenRequestType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(SecurityTokenRequestType securityTokenRequestType, UaEncoder encoder) {
        encoder.encodeInt32(null, securityTokenRequestType.getValue());
    }

    public static SecurityTokenRequestType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(SecurityTokenRequestType::encode, SecurityTokenRequestType.class);
        DelegateRegistry.registerDecoder(SecurityTokenRequestType::decode, SecurityTokenRequestType.class);
    }

}
