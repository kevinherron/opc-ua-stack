package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum MessageSecurityMode implements UaEnumeration {

    Invalid(0),
    None(1),
    Sign(2),
    SignAndEncrypt(3);

    private final int value;

    private MessageSecurityMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(MessageSecurityMode messageSecurityMode, UaEncoder encoder) {
        encoder.encodeInt32(null, messageSecurityMode.ordinal());
    }

    public static MessageSecurityMode decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return value < values().length ? values()[value] : null;
    }

    static {
        DelegateRegistry.registerEncoder(MessageSecurityMode::encode, MessageSecurityMode.class);
        DelegateRegistry.registerDecoder(MessageSecurityMode::decode, MessageSecurityMode.class);
    }

}
