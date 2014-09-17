package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

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

    public static void encode(NodeIdType nodeIdType, UaEncoder encoder) {
        encoder.encodeInt32(null, nodeIdType.ordinal());
    }

    public static NodeIdType decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return NodeIdType.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(NodeIdType::encode, NodeIdType.class);
        DelegateRegistry.registerDecoder(NodeIdType::decode, NodeIdType.class);
    }

}
