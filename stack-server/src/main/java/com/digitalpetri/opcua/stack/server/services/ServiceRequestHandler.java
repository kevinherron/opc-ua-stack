package com.digitalpetri.opcua.stack.server.services;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.server.ServiceRequest;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;

public interface ServiceRequestHandler<T extends UaRequestMessage, U extends UaResponseMessage> {

    void handle(ServiceRequest<T, U> service) throws UaException;

}
