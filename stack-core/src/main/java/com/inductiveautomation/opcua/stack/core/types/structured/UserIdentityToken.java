package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class UserIdentityToken implements UaStructure {

    public static final NodeId TypeId = Identifiers.UserIdentityToken;
    public static final NodeId BinaryEncodingId = Identifiers.UserIdentityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UserIdentityToken_Encoding_DefaultXml;

    protected final String _policyId;

    public UserIdentityToken(String _policyId) {
        this._policyId = _policyId;
    }

    public String getPolicyId() {
        return _policyId;
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


    public static void encode(UserIdentityToken userIdentityToken, UaEncoder encoder) {
        encoder.encodeString("PolicyId", userIdentityToken._policyId);
    }

    public static UserIdentityToken decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");

        return new UserIdentityToken(_policyId);
    }

    static {
        DelegateRegistry.registerEncoder(UserIdentityToken::encode, UserIdentityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UserIdentityToken::decode, UserIdentityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
