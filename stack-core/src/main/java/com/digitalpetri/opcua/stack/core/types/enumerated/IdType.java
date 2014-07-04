

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum IdType implements UaEnumeration {

    Numeric(0),
    String(1),
    Guid(2),
    Opaque(3); 

	public static final NodeId TypeId = Identifiers.IdType;

    private final int value;

    private IdType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(IdType idType, UaEncoder encoder) {
		encoder.encodeInt32(null, idType.ordinal());
	}

	public static IdType decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return IdType.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(IdType::encode, IdType.class);
		DelegateRegistry.registerDecoder(IdType::decode, IdType.class);
	}

}
