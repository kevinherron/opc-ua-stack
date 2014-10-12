package com.inductiveautomation.opcua.stack.core.application.services;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;

public interface ServiceRequestHandler<T extends UaRequestMessage, U extends UaResponseMessage> {

    void handle(ServiceRequest<T, U> service) throws UaException;

}
