package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class EndpointUrlListDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.EndpointUrlListDataType;
    public static final NodeId BinaryEncodingId = Identifiers.EndpointUrlListDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EndpointUrlListDataType_Encoding_DefaultXml;

    protected final String[] _endpointUrlList;

    public EndpointUrlListDataType(String[] _endpointUrlList) {
        this._endpointUrlList = _endpointUrlList;
    }

    public String[] getEndpointUrlList() { return _endpointUrlList; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(EndpointUrlListDataType endpointUrlListDataType, UaEncoder encoder) {
        encoder.encodeArray("EndpointUrlList", endpointUrlListDataType._endpointUrlList, encoder::encodeString);
    }

    public static EndpointUrlListDataType decode(UaDecoder decoder) {
        String[] _endpointUrlList = decoder.decodeArray("EndpointUrlList", decoder::decodeString, String.class);

        return new EndpointUrlListDataType(_endpointUrlList);
    }

    static {
        DelegateRegistry.registerEncoder(EndpointUrlListDataType::encode, EndpointUrlListDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(EndpointUrlListDataType::decode, EndpointUrlListDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
