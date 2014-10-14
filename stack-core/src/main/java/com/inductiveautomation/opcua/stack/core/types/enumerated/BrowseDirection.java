package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum BrowseDirection implements UaEnumeration {

    Forward(0),
    Inverse(1),
    Both(2);

    private final int value;

    private BrowseDirection(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(BrowseDirection browseDirection, UaEncoder encoder) {
        encoder.encodeInt32(null, browseDirection.ordinal());
    }

    public static BrowseDirection decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return value < values().length ? values()[value] : null;
    }

    static {
        DelegateRegistry.registerEncoder(BrowseDirection::encode, BrowseDirection.class);
        DelegateRegistry.registerDecoder(BrowseDirection::decode, BrowseDirection.class);
    }

}
