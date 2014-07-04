

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum UserTokenType implements UaEnumeration {

    Anonymous(0),
    UserName(1),
    Certificate(2),
    IssuedToken(3); 

	public static final NodeId TypeId = Identifiers.UserTokenType;

    private final int value;

    private UserTokenType(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(UserTokenType userTokenType, UaEncoder encoder) {
		encoder.encodeInt32(null, userTokenType.ordinal());
	}

	public static UserTokenType decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return UserTokenType.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(UserTokenType::encode, UserTokenType.class);
		DelegateRegistry.registerDecoder(UserTokenType::decode, UserTokenType.class);
	}

}
