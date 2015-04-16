package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("ServiceFault")
public class ServiceFault implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.ServiceFault;
    public static final NodeId BinaryEncodingId = Identifiers.ServiceFault_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ServiceFault_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;

    public ServiceFault() {
        this._responseHeader = null;
    }

    public ServiceFault(ResponseHeader _responseHeader) {
        this._responseHeader = _responseHeader;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    @Override
    public NodeId getTypeId() {
        return TypeId;
    }

    @Override
    public NodeId getBinaryEncodingId() {
        return BinaryEncodingId;
    }

    @Override
    public NodeId getXmlEncodingId() {
        return XmlEncodingId;
    }


    public static void encode(ServiceFault serviceFault, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", serviceFault._responseHeader != null ? serviceFault._responseHeader : new ResponseHeader());
    }

    public static ServiceFault decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);

        return new ServiceFault(_responseHeader);
    }

    static {
        DelegateRegistry.registerEncoder(ServiceFault::encode, ServiceFault.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ServiceFault::decode, ServiceFault.class, BinaryEncodingId, XmlEncodingId);
    }

}
