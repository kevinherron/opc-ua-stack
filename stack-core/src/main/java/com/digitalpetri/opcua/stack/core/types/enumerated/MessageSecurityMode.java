

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum MessageSecurityMode implements UaEnumeration {

    Invalid(0),
    None(1),
    Sign(2),
    SignAndEncrypt(3); 

	public static final NodeId TypeId = Identifiers.MessageSecurityMode;

    private final int value;

    private MessageSecurityMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(MessageSecurityMode messageSecurityMode, UaEncoder encoder) {
		encoder.encodeInt32(null, messageSecurityMode.ordinal());
	}

	public static MessageSecurityMode decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return MessageSecurityMode.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(MessageSecurityMode::encode, MessageSecurityMode.class);
		DelegateRegistry.registerDecoder(MessageSecurityMode::decode, MessageSecurityMode.class);
	}

}
