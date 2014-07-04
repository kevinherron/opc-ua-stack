

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum ComplianceLevel implements UaEnumeration {

    Untested(0),
    Partial(1),
    SelfTested(2),
    Certified(3); 

	public static final NodeId TypeId = Identifiers.ComplianceLevel;

    private final int value;

    private ComplianceLevel(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(ComplianceLevel complianceLevel, UaEncoder encoder) {
		encoder.encodeInt32(null, complianceLevel.ordinal());
	}

	public static ComplianceLevel decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return ComplianceLevel.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(ComplianceLevel::encode, ComplianceLevel.class);
		DelegateRegistry.registerDecoder(ComplianceLevel::decode, ComplianceLevel.class);
	}

}
