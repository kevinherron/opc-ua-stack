package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class UserNameIdentityToken extends UserIdentityToken {

    public static final NodeId TypeId = Identifiers.UserNameIdentityToken;
    public static final NodeId BinaryEncodingId = Identifiers.UserNameIdentityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UserNameIdentityToken_Encoding_DefaultXml;

    protected final String _userName;
    protected final ByteString _password;
    protected final String _encryptionAlgorithm;

    public UserNameIdentityToken(String _policyId, String _userName, ByteString _password, String _encryptionAlgorithm) {
        super(_policyId);
        this._userName = _userName;
        this._password = _password;
        this._encryptionAlgorithm = _encryptionAlgorithm;
    }

    public String getUserName() {
        return _userName;
    }

    public ByteString getPassword() {
        return _password;
    }

    public String getEncryptionAlgorithm() {
        return _encryptionAlgorithm;
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


    public static void encode(UserNameIdentityToken userNameIdentityToken, UaEncoder encoder) {
        encoder.encodeString("PolicyId", userNameIdentityToken._policyId);
        encoder.encodeString("UserName", userNameIdentityToken._userName);
        encoder.encodeByteString("Password", userNameIdentityToken._password);
        encoder.encodeString("EncryptionAlgorithm", userNameIdentityToken._encryptionAlgorithm);
    }

    public static UserNameIdentityToken decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");
        String _userName = decoder.decodeString("UserName");
        ByteString _password = decoder.decodeByteString("Password");
        String _encryptionAlgorithm = decoder.decodeString("EncryptionAlgorithm");

        return new UserNameIdentityToken(_policyId, _userName, _password, _encryptionAlgorithm);
    }

    static {
        DelegateRegistry.registerEncoder(UserNameIdentityToken::encode, UserNameIdentityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UserNameIdentityToken::decode, UserNameIdentityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
