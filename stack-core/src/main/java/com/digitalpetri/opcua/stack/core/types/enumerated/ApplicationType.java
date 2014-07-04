

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum ApplicationType implements UaEnumeration {

    Server(0),
    Client(1),
    ClientAndServer(2),
    DiscoveryServer(3); 

	public static final NodeId TypeId = Identifiers.ApplicationType;

    private final int value;

    private ApplicationType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(ApplicationType applicationType, UaEncoder encoder) {
		encoder.encodeInt32(null, applicationType.ordinal());
	}

	public static ApplicationType decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return ApplicationType.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(ApplicationType::encode, ApplicationType.class);
		DelegateRegistry.registerDecoder(ApplicationType::decode, ApplicationType.class);
	}

}
