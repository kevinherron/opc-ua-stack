

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum PerformUpdateType implements UaEnumeration {

    Insert(1),
    Replace(2),
    Update(3),
    Remove(4); 

	public static final NodeId TypeId = Identifiers.PerformUpdateType;

    private final int value;

    private PerformUpdateType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(PerformUpdateType performUpdateType, UaEncoder encoder) {
		encoder.encodeInt32(null, performUpdateType.ordinal());
	}

	public static PerformUpdateType decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return PerformUpdateType.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(PerformUpdateType::encode, PerformUpdateType.class);
		DelegateRegistry.registerDecoder(PerformUpdateType::decode, PerformUpdateType.class);
	}

}
