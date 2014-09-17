package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;

public class EndpointDescription implements UaStructure {

    public static final NodeId TypeId = Identifiers.EndpointDescription;
    public static final NodeId BinaryEncodingId = Identifiers.EndpointDescription_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EndpointDescription_Encoding_DefaultXml;

    protected final String _endpointUrl;
    protected final ApplicationDescription _server;
    protected final ByteString _serverCertificate;
    protected final MessageSecurityMode _securityMode;
    protected final String _securityPolicyUri;
    protected final UserTokenPolicy[] _userIdentityTokens;
    protected final String _transportProfileUri;
    protected final Short _securityLevel;

    public EndpointDescription(String _endpointUrl, ApplicationDescription _server, ByteString _serverCertificate, MessageSecurityMode _securityMode, String _securityPolicyUri, UserTokenPolicy[] _userIdentityTokens, String _transportProfileUri, Short _securityLevel) {
        this._endpointUrl = _endpointUrl;
        this._server = _server;
        this._serverCertificate = _serverCertificate;
        this._securityMode = _securityMode;
        this._securityPolicyUri = _securityPolicyUri;
        this._userIdentityTokens = _userIdentityTokens;
        this._transportProfileUri = _transportProfileUri;
        this._securityLevel = _securityLevel;
    }

    public String getEndpointUrl() { return _endpointUrl; }

    public ApplicationDescription getServer() { return _server; }

    public ByteString getServerCertificate() { return _serverCertificate; }

    public MessageSecurityMode getSecurityMode() { return _securityMode; }

    public String getSecurityPolicyUri() { return _securityPolicyUri; }

    public UserTokenPolicy[] getUserIdentityTokens() { return _userIdentityTokens; }

    public String getTransportProfileUri() { return _transportProfileUri; }

    public Short getSecurityLevel() { return _securityLevel; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(EndpointDescription endpointDescription, UaEncoder encoder) {
        encoder.encodeString("EndpointUrl", endpointDescription._endpointUrl);
        encoder.encodeSerializable("Server", endpointDescription._server);
        encoder.encodeByteString("ServerCertificate", endpointDescription._serverCertificate);
        encoder.encodeSerializable("SecurityMode", endpointDescription._securityMode);
        encoder.encodeString("SecurityPolicyUri", endpointDescription._securityPolicyUri);
        encoder.encodeArray("UserIdentityTokens", endpointDescription._userIdentityTokens, encoder::encodeSerializable);
        encoder.encodeString("TransportProfileUri", endpointDescription._transportProfileUri);
        encoder.encodeByte("SecurityLevel", endpointDescription._securityLevel);
    }

    public static EndpointDescription decode(UaDecoder decoder) {
        String _endpointUrl = decoder.decodeString("EndpointUrl");
        ApplicationDescription _server = decoder.decodeSerializable("Server", ApplicationDescription.class);
        ByteString _serverCertificate = decoder.decodeByteString("ServerCertificate");
        MessageSecurityMode _securityMode = decoder.decodeSerializable("SecurityMode", MessageSecurityMode.class);
        String _securityPolicyUri = decoder.decodeString("SecurityPolicyUri");
        UserTokenPolicy[] _userIdentityTokens = decoder.decodeArray("UserIdentityTokens", decoder::decodeSerializable, UserTokenPolicy.class);
        String _transportProfileUri = decoder.decodeString("TransportProfileUri");
        Short _securityLevel = decoder.decodeByte("SecurityLevel");

        return new EndpointDescription(_endpointUrl, _server, _serverCertificate, _securityMode, _securityPolicyUri, _userIdentityTokens, _transportProfileUri, _securityLevel);
    }

    static {
        DelegateRegistry.registerEncoder(EndpointDescription::encode, EndpointDescription.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(EndpointDescription::decode, EndpointDescription.class, BinaryEncodingId, XmlEncodingId);
    }

}
