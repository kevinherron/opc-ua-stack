package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.UserTokenType;

@UaDataType("UserTokenPolicy")
public class UserTokenPolicy implements UaStructure {

    public static final NodeId TypeId = Identifiers.UserTokenPolicy;
    public static final NodeId BinaryEncodingId = Identifiers.UserTokenPolicy_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UserTokenPolicy_Encoding_DefaultXml;

    protected final String _policyId;
    protected final UserTokenType _tokenType;
    protected final String _issuedTokenType;
    protected final String _issuerEndpointUrl;
    protected final String _securityPolicyUri;

    public UserTokenPolicy() {
        this._policyId = null;
        this._tokenType = null;
        this._issuedTokenType = null;
        this._issuerEndpointUrl = null;
        this._securityPolicyUri = null;
    }

    public UserTokenPolicy(String _policyId, UserTokenType _tokenType, String _issuedTokenType, String _issuerEndpointUrl, String _securityPolicyUri) {
        this._policyId = _policyId;
        this._tokenType = _tokenType;
        this._issuedTokenType = _issuedTokenType;
        this._issuerEndpointUrl = _issuerEndpointUrl;
        this._securityPolicyUri = _securityPolicyUri;
    }

    public String getPolicyId() {
        return _policyId;
    }

    public UserTokenType getTokenType() {
        return _tokenType;
    }

    public String getIssuedTokenType() {
        return _issuedTokenType;
    }

    public String getIssuerEndpointUrl() {
        return _issuerEndpointUrl;
    }

    public String getSecurityPolicyUri() {
        return _securityPolicyUri;
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


    public static void encode(UserTokenPolicy userTokenPolicy, UaEncoder encoder) {
        encoder.encodeString("PolicyId", userTokenPolicy._policyId);
        encoder.encodeEnumeration("TokenType", userTokenPolicy._tokenType);
        encoder.encodeString("IssuedTokenType", userTokenPolicy._issuedTokenType);
        encoder.encodeString("IssuerEndpointUrl", userTokenPolicy._issuerEndpointUrl);
        encoder.encodeString("SecurityPolicyUri", userTokenPolicy._securityPolicyUri);
    }

    public static UserTokenPolicy decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");
        UserTokenType _tokenType = decoder.decodeEnumeration("TokenType", UserTokenType.class);
        String _issuedTokenType = decoder.decodeString("IssuedTokenType");
        String _issuerEndpointUrl = decoder.decodeString("IssuerEndpointUrl");
        String _securityPolicyUri = decoder.decodeString("SecurityPolicyUri");

        return new UserTokenPolicy(_policyId, _tokenType, _issuedTokenType, _issuerEndpointUrl, _securityPolicyUri);
    }

    static {
        DelegateRegistry.registerEncoder(UserTokenPolicy::encode, UserTokenPolicy.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UserTokenPolicy::decode, UserTokenPolicy.class, BinaryEncodingId, XmlEncodingId);
    }

}
