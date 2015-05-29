package com.digitalpetri.opcua.stack.core.serialization;

import com.digitalpetri.opcua.stack.core.UaSerializationException;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;

public interface DataTypeEncoding {

    public static final DataTypeEncoding OPC_UA = new OpcUaDataTypeEncoding();

    ByteString encodeToByteString(Object object, NodeId encodingTypeId) throws UaSerializationException;

    Object decodeFromByteString(ByteString encoded, NodeId encodingTypeId) throws UaSerializationException;

    XmlElement encodeToXmlElement(Object object, NodeId encodingTypeId) throws UaSerializationException;

    Object decodeFromXmlElement(XmlElement encoded, NodeId encodingTypeId) throws UaSerializationException;

}
