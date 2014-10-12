package com.inductiveautomation.opcua.stack.core.serialization;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface UaStructure extends UaSerializable {

    NodeId getTypeId();

    NodeId getBinaryEncodingId();

    NodeId getXmlEncodingId();

}
