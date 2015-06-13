package com.digitalpetri.opcua.stack.core;

import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;

public class UaServiceFaultException extends UaException {

    private final ServiceFault serviceFault;

    public UaServiceFaultException(ServiceFault serviceFault) {
        super(serviceFault.getResponseHeader().getServiceResult());

        this.serviceFault = serviceFault;
    }

    public UaServiceFaultException(ServiceFault serviceFault, String message) {
        super(serviceFault.getResponseHeader().getServiceResult(), message);

        this.serviceFault = serviceFault;
    }

    public ServiceFault getServiceFault() {
        return serviceFault;
    }

}
