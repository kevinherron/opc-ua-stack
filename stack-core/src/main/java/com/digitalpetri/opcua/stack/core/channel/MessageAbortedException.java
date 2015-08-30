package com.digitalpetri.opcua.stack.core.channel;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class MessageAbortedException extends UaException {

    public MessageAbortedException(long statusCode, String message) {
        super(statusCode, message);
    }

    public MessageAbortedException(StatusCode statusCode, String message) {
        super(statusCode, message);
    }

}
