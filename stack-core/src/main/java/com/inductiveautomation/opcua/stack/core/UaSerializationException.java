package com.inductiveautomation.opcua.stack.core;

public class UaSerializationException extends UaRuntimeException {

    public UaSerializationException(long statusCode, Throwable cause) {
        super(statusCode, cause);
    }

    public UaSerializationException(long statusCode, String message) {
        super(statusCode, message);
    }

}
