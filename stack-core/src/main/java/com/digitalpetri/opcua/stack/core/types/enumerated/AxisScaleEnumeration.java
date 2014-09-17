package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum AxisScaleEnumeration implements UaEnumeration {

    Linear(0),
    Log(1),
    Ln(2);

    private final int value;

    private AxisScaleEnumeration(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(AxisScaleEnumeration axisScaleEnumeration, UaEncoder encoder) {
        encoder.encodeInt32(null, axisScaleEnumeration.ordinal());
    }

    public static AxisScaleEnumeration decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return AxisScaleEnumeration.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(AxisScaleEnumeration::encode, AxisScaleEnumeration.class);
        DelegateRegistry.registerDecoder(AxisScaleEnumeration::decode, AxisScaleEnumeration.class);
    }

}
