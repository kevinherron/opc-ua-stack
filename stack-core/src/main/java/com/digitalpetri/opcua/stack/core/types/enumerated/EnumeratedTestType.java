

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum EnumeratedTestType implements UaEnumeration {

    Red(1),
    Yellow(4),
    Green(5); 

	public static final NodeId TypeId = Identifiers.EnumeratedTestType;

    private final int value;

    private EnumeratedTestType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(EnumeratedTestType enumeratedTestType, UaEncoder encoder) {
		encoder.encodeInt32(null, enumeratedTestType.ordinal());
	}

	public static EnumeratedTestType decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return EnumeratedTestType.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(EnumeratedTestType::encode, EnumeratedTestType.class);
		DelegateRegistry.registerDecoder(EnumeratedTestType::decode, EnumeratedTestType.class);
	}

}
