package com.inductiveautomation.opcua.stack.core.serialization;

import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;

public interface UaRequestMessage extends UaMessage {

    RequestHeader getRequestHeader();

}
