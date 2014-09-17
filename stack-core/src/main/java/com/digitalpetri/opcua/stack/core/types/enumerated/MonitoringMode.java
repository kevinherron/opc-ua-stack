package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum MonitoringMode implements UaEnumeration {

    Disabled(0),
    Sampling(1),
    Reporting(2);

    private final int value;

    private MonitoringMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(MonitoringMode monitoringMode, UaEncoder encoder) {
        encoder.encodeInt32(null, monitoringMode.ordinal());
    }

    public static MonitoringMode decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return MonitoringMode.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringMode::encode, MonitoringMode.class);
        DelegateRegistry.registerDecoder(MonitoringMode::decode, MonitoringMode.class);
    }

}
