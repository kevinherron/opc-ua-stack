package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ServiceCounterDataType")
public class ServiceCounterDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.ServiceCounterDataType;
    public static final NodeId BinaryEncodingId = Identifiers.ServiceCounterDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ServiceCounterDataType_Encoding_DefaultXml;

    protected final UInteger _totalCount;
    protected final UInteger _errorCount;

    public ServiceCounterDataType() {
        this._totalCount = null;
        this._errorCount = null;
    }

    public ServiceCounterDataType(UInteger _totalCount, UInteger _errorCount) {
        this._totalCount = _totalCount;
        this._errorCount = _errorCount;
    }

    public UInteger getTotalCount() {
        return _totalCount;
    }

    public UInteger getErrorCount() {
        return _errorCount;
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


    public static void encode(ServiceCounterDataType serviceCounterDataType, UaEncoder encoder) {
        encoder.encodeUInt32("TotalCount", serviceCounterDataType._totalCount);
        encoder.encodeUInt32("ErrorCount", serviceCounterDataType._errorCount);
    }

    public static ServiceCounterDataType decode(UaDecoder decoder) {
        UInteger _totalCount = decoder.decodeUInt32("TotalCount");
        UInteger _errorCount = decoder.decodeUInt32("ErrorCount");

        return new ServiceCounterDataType(_totalCount, _errorCount);
    }

    static {
        DelegateRegistry.registerEncoder(ServiceCounterDataType::encode, ServiceCounterDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ServiceCounterDataType::decode, ServiceCounterDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
