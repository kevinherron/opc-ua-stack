package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;

public class RedundantServerDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.RedundantServerDataType;
    public static final NodeId BinaryEncodingId = Identifiers.RedundantServerDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RedundantServerDataType_Encoding_DefaultXml;

    protected final String _serverId;
    protected final Short _serviceLevel;
    protected final ServerState _serverState;

    public RedundantServerDataType(String _serverId, Short _serviceLevel, ServerState _serverState) {
        this._serverId = _serverId;
        this._serviceLevel = _serviceLevel;
        this._serverState = _serverState;
    }

    public String getServerId() { return _serverId; }

    public Short getServiceLevel() { return _serviceLevel; }

    public ServerState getServerState() { return _serverState; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RedundantServerDataType redundantServerDataType, UaEncoder encoder) {
        encoder.encodeString("ServerId", redundantServerDataType._serverId);
        encoder.encodeByte("ServiceLevel", redundantServerDataType._serviceLevel);
        encoder.encodeSerializable("ServerState", redundantServerDataType._serverState);
    }

    public static RedundantServerDataType decode(UaDecoder decoder) {
        String _serverId = decoder.decodeString("ServerId");
        Short _serviceLevel = decoder.decodeByte("ServiceLevel");
        ServerState _serverState = decoder.decodeSerializable("ServerState", ServerState.class);

        return new RedundantServerDataType(_serverId, _serviceLevel, _serverState);
    }

    static {
        DelegateRegistry.registerEncoder(RedundantServerDataType::encode, RedundantServerDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RedundantServerDataType::decode, RedundantServerDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
