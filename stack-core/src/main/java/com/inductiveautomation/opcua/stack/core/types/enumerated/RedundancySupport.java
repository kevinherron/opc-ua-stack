package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum RedundancySupport implements UaEnumeration {

    None(0),
    Cold(1),
    Warm(2),
    Hot(3),
    Transparent(4),
    HotAndMirrored(5);

    private final int value;

    private RedundancySupport(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(RedundancySupport redundancySupport, UaEncoder encoder) {
        encoder.encodeInt32(null, redundancySupport.ordinal());
    }

    public static RedundancySupport decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return RedundancySupport.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(RedundancySupport::encode, RedundancySupport.class);
        DelegateRegistry.registerDecoder(RedundancySupport::decode, RedundancySupport.class);
    }

}
