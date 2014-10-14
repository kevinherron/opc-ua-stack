package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum ModelChangeStructureVerbMask implements UaEnumeration {

    NodeAdded(1),
    NodeDeleted(2),
    ReferenceAdded(4),
    ReferenceDeleted(8),
    DataTypeChanged(16);

    private final int value;

    private ModelChangeStructureVerbMask(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(ModelChangeStructureVerbMask modelChangeStructureVerbMask, UaEncoder encoder) {
        encoder.encodeInt32(null, modelChangeStructureVerbMask.ordinal());
    }

    public static ModelChangeStructureVerbMask decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return value < values().length ? values()[value] : null;
    }

    static {
        DelegateRegistry.registerEncoder(ModelChangeStructureVerbMask::encode, ModelChangeStructureVerbMask.class);
        DelegateRegistry.registerDecoder(ModelChangeStructureVerbMask::decode, ModelChangeStructureVerbMask.class);
    }

}
