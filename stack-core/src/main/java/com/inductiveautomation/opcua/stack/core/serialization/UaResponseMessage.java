package com.inductiveautomation.opcua.stack.core.serialization;

import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;

public interface UaResponseMessage extends UaMessage {

    ResponseHeader getResponseHeader();

}
