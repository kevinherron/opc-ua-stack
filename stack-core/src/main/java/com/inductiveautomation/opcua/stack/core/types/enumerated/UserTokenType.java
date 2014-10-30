package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum UserTokenType implements UaEnumeration {

    Anonymous(0),
    UserName(1),
    Certificate(2),
    IssuedToken(3);

    private final int value;

    private UserTokenType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, UserTokenType> VALUES;

    static {
        Builder<Integer, UserTokenType> builder = ImmutableMap.builder();
        for (UserTokenType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static UserTokenType from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(UserTokenType userTokenType, UaEncoder encoder) {
        encoder.encodeInt32(null, userTokenType.getValue());
    }

    public static UserTokenType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(UserTokenType::encode, UserTokenType.class);
        DelegateRegistry.registerDecoder(UserTokenType::decode, UserTokenType.class);
    }

}
