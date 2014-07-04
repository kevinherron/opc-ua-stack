

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum AttributeWriteMask implements UaEnumeration {

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
    ValueForVariableType(2097152); 

	public static final NodeId TypeId = Identifiers.AttributeWriteMask;

    private final int value;

    private AttributeWriteMask(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(AttributeWriteMask attributeWriteMask, UaEncoder encoder) {
		encoder.encodeInt32(null, attributeWriteMask.ordinal());
	}

	public static AttributeWriteMask decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return AttributeWriteMask.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(AttributeWriteMask::encode, AttributeWriteMask.class);
		DelegateRegistry.registerDecoder(AttributeWriteMask::decode, AttributeWriteMask.class);
	}

}
