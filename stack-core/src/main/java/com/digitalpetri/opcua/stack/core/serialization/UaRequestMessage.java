package com.digitalpetri.opcua.stack.core.serialization;

import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;

public interface UaRequestMessage extends UaMessage {

    RequestHeader getRequestHeader();

}
