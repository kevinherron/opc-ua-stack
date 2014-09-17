package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;

public enum NodeAttributesMask implements UaEnumeration {

    None(0),
    AccessLevel(1),
    ArrayDimensions(2),
    BrowseName(4),
    ContainsNoLoops(8),
    DataType(16),
    Description(32),
    DisplayName(64),
    EventNotifier(128),
    Executable(256),
    Historizing(512),
    InverseName(1024),
    IsAbstract(2048),
    MinimumSamplingInterval(4096),
    NodeClass(8192),
    NodeId(16384),
    Symmetric(32768),
    UserAccessLevel(65536),
    UserExecutable(131072),
    UserWriteMask(262144),
    ValueRank(524288),
    WriteMask(1048576),
    Value(2097152),
    All(4194303),
    BaseNode(1335396),
    Object(1335524),
    ObjectTypeOrDataType(1337444),
    Variable(4026999),
    VariableType(3958902),
    Method(1466724),
    ReferenceType(1371236),
    View(1335532);

    private final int value;

    private NodeAttributesMask(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static void encode(NodeAttributesMask nodeAttributesMask, UaEncoder encoder) {
        encoder.encodeInt32(null, nodeAttributesMask.ordinal());
    }

    public static NodeAttributesMask decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);
        return NodeAttributesMask.values()[value];
    }

    static {
        DelegateRegistry.registerEncoder(NodeAttributesMask::encode, NodeAttributesMask.class);
        DelegateRegistry.registerDecoder(NodeAttributesMask::decode, NodeAttributesMask.class);
    }

}
