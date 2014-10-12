package com.inductiveautomation.opcua.stack.core.types.enumerated;

import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;

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

    public static void encode(NodeClass nodeClass, UaEncoder encoder) {
        encoder.encodeInt32(null, nodeClass.ordinal());
    }

    public static NodeClass decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return NodeClass.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(NodeClass::encode, NodeClass.class);
        DelegateRegistry.registerDecoder(NodeClass::decode, NodeClass.class);
    }

}
