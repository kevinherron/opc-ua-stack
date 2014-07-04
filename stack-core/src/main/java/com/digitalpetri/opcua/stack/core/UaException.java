package com.digitalpetri.opcua.stack.core;

import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class UaException extends Exception {

    private final StatusCode statusCode;

    public UaException(long statusCode) {
        this.statusCode = new StatusCode(statusCode);
    }

    public UaException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public UaException(Throwable cause) {
        super(cause);

        this.statusCode = new StatusCode(StatusCodes.Bad_InternalError);
    }

    public UaException(long statusCode, String message) {
        super(message);

        this.statusCode = new StatusCode(statusCode);
    }

    public UaException(StatusCode statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

}
