package com.digitalpetri.opcua.stack.core.serialization;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface UaStructure extends UaSerializable {

    NodeId getTypeId();

    NodeId getBinaryEncodingId();

    NodeId getXmlEncodingId();

}
