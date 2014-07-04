package com.digitalpetri.opcua.stack.core.serialization;

import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;

public interface UaResponseMessage extends UaMessage {

    ResponseHeader getResponseHeader();

}
