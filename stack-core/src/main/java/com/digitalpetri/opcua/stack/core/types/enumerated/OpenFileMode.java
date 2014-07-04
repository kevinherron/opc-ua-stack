

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum OpenFileMode implements UaEnumeration {

    Read(1),
    Write(2),
    EraseExisiting(4),
    Append(8); 

	public static final NodeId TypeId = Identifiers.OpenFileMode;

    private final int value;

    private OpenFileMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(OpenFileMode openFileMode, UaEncoder encoder) {
		encoder.encodeInt32(null, openFileMode.ordinal());
	}

	public static OpenFileMode decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return OpenFileMode.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(OpenFileMode::encode, OpenFileMode.class);
		DelegateRegistry.registerDecoder(OpenFileMode::decode, OpenFileMode.class);
	}

}
