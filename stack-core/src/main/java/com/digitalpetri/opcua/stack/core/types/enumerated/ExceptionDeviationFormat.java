

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum ExceptionDeviationFormat implements UaEnumeration {

    AbsoluteValue(0),
    PercentOfRange(1),
    PercentOfValue(2),
    PercentOfEURange(3),
    Unknown(4); 

	public static final NodeId TypeId = Identifiers.ExceptionDeviationFormat;

    private final int value;

    private ExceptionDeviationFormat(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(ExceptionDeviationFormat exceptionDeviationFormat, UaEncoder encoder) {
		encoder.encodeInt32(null, exceptionDeviationFormat.ordinal());
	}

	public static ExceptionDeviationFormat decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return ExceptionDeviationFormat.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(ExceptionDeviationFormat::encode, ExceptionDeviationFormat.class);
		DelegateRegistry.registerDecoder(ExceptionDeviationFormat::decode, ExceptionDeviationFormat.class);
	}

}
