package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("ChannelSecurityToken")
public class ChannelSecurityToken implements UaStructure {

    public static final NodeId TypeId = Identifiers.ChannelSecurityToken;
    public static final NodeId BinaryEncodingId = Identifiers.ChannelSecurityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ChannelSecurityToken_Encoding_DefaultXml;

    protected final UInteger _channelId;
    protected final UInteger _tokenId;
    protected final DateTime _createdAt;
    protected final UInteger _revisedLifetime;

    public ChannelSecurityToken() {
        this._channelId = null;
        this._tokenId = null;
        this._createdAt = null;
        this._revisedLifetime = null;
    }

    public ChannelSecurityToken(UInteger _channelId, UInteger _tokenId, DateTime _createdAt, UInteger _revisedLifetime) {
        this._channelId = _channelId;
        this._tokenId = _tokenId;
        this._createdAt = _createdAt;
        this._revisedLifetime = _revisedLifetime;
    }

    public UInteger getChannelId() {
        return _channelId;
    }

    public UInteger getTokenId() {
        return _tokenId;
    }

    public DateTime getCreatedAt() {
        return _createdAt;
    }

    public UInteger getRevisedLifetime() {
        return _revisedLifetime;
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


    public static void encode(ChannelSecurityToken channelSecurityToken, UaEncoder encoder) {
        encoder.encodeUInt32("ChannelId", channelSecurityToken._channelId);
        encoder.encodeUInt32("TokenId", channelSecurityToken._tokenId);
        encoder.encodeDateTime("CreatedAt", channelSecurityToken._createdAt);
        encoder.encodeUInt32("RevisedLifetime", channelSecurityToken._revisedLifetime);
    }

    public static ChannelSecurityToken decode(UaDecoder decoder) {
        UInteger _channelId = decoder.decodeUInt32("ChannelId");
        UInteger _tokenId = decoder.decodeUInt32("TokenId");
        DateTime _createdAt = decoder.decodeDateTime("CreatedAt");
        UInteger _revisedLifetime = decoder.decodeUInt32("RevisedLifetime");

        return new ChannelSecurityToken(_channelId, _tokenId, _createdAt, _revisedLifetime);
    }

    static {
        DelegateRegistry.registerEncoder(ChannelSecurityToken::encode, ChannelSecurityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ChannelSecurityToken::decode, ChannelSecurityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
