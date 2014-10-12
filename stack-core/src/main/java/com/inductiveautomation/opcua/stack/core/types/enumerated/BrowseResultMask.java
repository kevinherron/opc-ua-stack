package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum BrowseResultMask implements UaEnumeration {

    None(0),
    ReferenceTypeId(1),
    IsForward(2),
    NodeClass(4),
    BrowseName(8),
    DisplayName(16),
    TypeDefinition(32),
    All(63),
    ReferenceTypeInfo(3),
    TargetInfo(60);

    private final int value;

    private BrowseResultMask(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(BrowseResultMask browseResultMask, UaEncoder encoder) {
        encoder.encodeInt32(null, browseResultMask.ordinal());
    }

    public static BrowseResultMask decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return BrowseResultMask.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(BrowseResultMask::encode, BrowseResultMask.class);
        DelegateRegistry.registerDecoder(BrowseResultMask::decode, BrowseResultMask.class);
    }

}
