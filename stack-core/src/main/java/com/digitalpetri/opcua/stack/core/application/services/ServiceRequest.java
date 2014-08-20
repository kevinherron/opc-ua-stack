package com.digitalpetri.opcua.stack.core.application.services;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.UaServer;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.google.common.base.Objects;
import io.netty.util.DefaultAttributeMap;

public class ServiceRequest<ReqT extends UaRequestMessage, ResT extends UaResponseMessage> extends DefaultAttributeMap {

    private final CompletableFuture<ResT> future = new CompletableFuture<>();

    private final ReqT request;
    private final long requestId;
    private final UaServer server;
    private final ServerSecureChannel secureChannel;

    public ServiceRequest(ReqT request,
                          long requestId,
                          UaServer server,
                          ServerSecureChannel secureChannel) {

        this.request = request;
        this.requestId = requestId;
        this.server = server;
        this.secureChannel = secureChannel;
    }

    public CompletableFuture<ResT> getFuture() {
        return future;
    }

    public ReqT getRequest() {
        return request;
    }

    public long getRequestId() {
        return requestId;
    }

    public UaServer getServer() {
        return server;
    }

    public ServerSecureChannel getSecureChannel() {
        return secureChannel;
    }

    public void setResponse(ResT response) {
        future.complete(response);
    }

    public void setServiceFault(UaException exception) {
        future.completeExceptionally(exception);
    }

    public void setServiceFault(long statusCode) {
        setServiceFault(new StatusCode(statusCode));
    }

    public void setServiceFault(StatusCode statusCode) {
        future.completeExceptionally(new UaException(statusCode, "ServiceFault"));
    }

    public ResponseHeader createResponseHeader() {
        return createResponseHeader(StatusCode.Good);
    }

    public ResponseHeader createResponseHeader(long statusCode) {
        return createResponseHeader(new StatusCode(statusCode));
    }

    public ResponseHeader createResponseHeader(StatusCode serviceResult) {
        return new ResponseHeader(
                DateTime.now(),
                request.getRequestHeader().getRequestHandle(),
                serviceResult,
                null, null, null
        );
    }

    public ServiceFault createServiceFault(Throwable throwable) {
        UaException exception = (throwable instanceof UaException) ?
                (UaException) throwable : new UaException(throwable);

        ResponseHeader responseHeader = new ResponseHeader(
                DateTime.now(),
                request.getRequestHeader().getRequestHandle(),
                exception.getStatusCode(),
                null, null, null
        );

        return new ServiceFault(responseHeader);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("requestId", requestId)
                .add("request", request.getClass().getSimpleName())
                .toString();
    }

}
