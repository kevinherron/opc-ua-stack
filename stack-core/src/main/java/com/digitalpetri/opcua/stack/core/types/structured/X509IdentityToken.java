package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class X509IdentityToken extends UserIdentityToken {

    public static final NodeId TypeId = Identifiers.X509IdentityToken;
    public static final NodeId BinaryEncodingId = Identifiers.X509IdentityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.X509IdentityToken_Encoding_DefaultXml;

    protected final ByteString _certificateData;

    public X509IdentityToken(String _policyId, ByteString _certificateData) {
        super(_policyId);
        this._certificateData = _certificateData;
    }

    public ByteString getCertificateData() { return _certificateData; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(X509IdentityToken x509IdentityToken, UaEncoder encoder) {
        encoder.encodeString("PolicyId", x509IdentityToken._policyId);
        encoder.encodeByteString("CertificateData", x509IdentityToken._certificateData);
    }

    public static X509IdentityToken decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");
        ByteString _certificateData = decoder.decodeByteString("CertificateData");

        return new X509IdentityToken(_policyId, _certificateData);
    }

    static {
        DelegateRegistry.registerEncoder(X509IdentityToken::encode, X509IdentityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(X509IdentityToken::decode, X509IdentityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
