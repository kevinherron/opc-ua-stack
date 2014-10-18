package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

public enum NodeIdType implements UaEnumeration {

    TwoByte(0),
    FourByte(1),
    Numeric(2),
    String(3),
    Guid(4),
    ByteString(5);

    private final int value;

    private NodeIdType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, NodeIdType> VALUES;

    static {
        Builder<Integer, NodeIdType> builder = ImmutableMap.builder();
        for (NodeIdType e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static void encode(NodeIdType nodeIdType, UaEncoder encoder) {
        encoder.encodeInt32(null, nodeIdType.getValue());
    }

    public static NodeIdType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(NodeIdType::encode, NodeIdType.class);
        DelegateRegistry.registerDecoder(NodeIdType::decode, NodeIdType.class);
    }

}
