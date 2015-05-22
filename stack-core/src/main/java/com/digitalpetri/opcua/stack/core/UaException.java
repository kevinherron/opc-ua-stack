package com.digitalpetri.opcua.stack.core;

import java.util.Optional;

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

    public UaException(long statusCode, Throwable cause) {
        super(cause);

        this.statusCode = new StatusCode(statusCode);
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

    /**
     * If {@code ex} is a {@link UaException}, or if a {@link UaException} can be found by walking up the exception
     * cause chain, return it.
     *
     * @param ex the {@link Throwable} to extract from.
     * @return a {@link UaException} if one was present in the exception chain.
     */
    public static Optional<UaException>  extract(Throwable ex) {
        if (ex instanceof UaException) {
            return Optional.of((UaException) ex);
        } else {
            Throwable cause = ex.getCause();
            return cause != null ?
                    extract(cause) : Optional.empty();
        }
    }

}
