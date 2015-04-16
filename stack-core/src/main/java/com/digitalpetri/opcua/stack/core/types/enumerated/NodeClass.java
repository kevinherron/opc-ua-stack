package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum NodeClass implements UaEnumeration {

    Unspecified(0),
    Object(1),
    Variable(2),
    Method(4),
    ObjectType(8),
    VariableType(16),
    ReferenceType(32),
    DataType(64),
    View(128);

    private final int value;

    private NodeClass(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, NodeClass> VALUES;

    static {
        Builder<Integer, NodeClass> builder = ImmutableMap.builder();
        for (NodeClass e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static NodeClass from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(NodeClass nodeClass, UaEncoder encoder) {
        encoder.encodeInt32(null, nodeClass.getValue());
    }

    public static NodeClass decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(NodeClass::encode, NodeClass.class);
        DelegateRegistry.registerDecoder(NodeClass::decode, NodeClass.class);
    }

}
