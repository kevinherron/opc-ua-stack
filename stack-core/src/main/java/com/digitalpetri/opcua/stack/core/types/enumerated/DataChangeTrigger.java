

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public enum DataChangeTrigger implements UaEnumeration {

    Status(0),
    StatusValue(1),
    StatusValueTimestamp(2); 

	public static final NodeId TypeId = Identifiers.DataChangeTrigger;

    private final int value;

    private DataChangeTrigger(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

	public static void encode(DataChangeTrigger dataChangeTrigger, UaEncoder encoder) {
		encoder.encodeInt32(null, dataChangeTrigger.ordinal());
	}

	public static DataChangeTrigger decode(UaDecoder decoder) {
		int value = decoder.decodeInt32(null);
		return DataChangeTrigger.values()[value];
	}

	static {
		DelegateRegistry.registerEncoder(DataChangeTrigger::encode, DataChangeTrigger.class);
		DelegateRegistry.registerDecoder(DataChangeTrigger::decode, DataChangeTrigger.class);
	}

}
