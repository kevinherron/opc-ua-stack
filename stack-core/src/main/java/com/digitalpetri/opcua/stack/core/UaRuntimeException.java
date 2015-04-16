package com.digitalpetri.opcua.stack.core;

public class UaRuntimeException extends RuntimeException {

    private final long statusCode;

    public UaRuntimeException(Throwable cause) {
        super(cause);

        this.statusCode = StatusCodes.Bad_InternalError;
    }

    public UaRuntimeException(long statusCode) {
        this.statusCode = statusCode;
    }

    public UaRuntimeException(long statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    public UaRuntimeException(long statusCode, Throwable cause) {
        super(cause);

        this.statusCode = statusCode;
    }

    public long getStatusCode() {
        return statusCode;
    }

}
