package com.digitalpetri.opcua.stack.core.security;

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;

public enum SecurityPolicy {

    /**
     * A suite of algorithms that do not provide any security settings.
     */
    NONE("http://opcfoundation.org/UA/SecurityPolicy#None",
            SecurityAlgorithm.NONE,
            SecurityAlgorithm.NONE,
            SecurityAlgorithm.NONE,
            SecurityAlgorithm.NONE,
            SecurityAlgorithm.NONE,
            SecurityAlgorithm.NONE,
            SecurityAlgorithm.NONE),

    /**
     * A suite of algorithms that use RSA for asymmetric encryption and AES-128 for symmetric encryption.
     */
    BASIC_128_RSA_15("http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15",
            SecurityAlgorithm.HMAC_SHA1,
            SecurityAlgorithm.AES128,
            SecurityAlgorithm.RSA_SHA1,
            SecurityAlgorithm.RSA_15,
            SecurityAlgorithm.KW_RSA_15,
            SecurityAlgorithm.P_SHA1,
            SecurityAlgorithm.SHA1),

    BASIC_256("http://opcfoundation.org/UA/SecurityPolicy#Basic256",
            SecurityAlgorithm.HMAC_SHA1,
            SecurityAlgorithm.AES256,
            SecurityAlgorithm.RSA_SHA1,
            SecurityAlgorithm.RSA_OAEP,
            SecurityAlgorithm.KW_RSA_OAEP,
            SecurityAlgorithm.P_SHA1,
            SecurityAlgorithm.SHA1),

    BASIC_256_SHA256("http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256",
            SecurityAlgorithm.HMAC_SHA256,
            SecurityAlgorithm.AES256,
            SecurityAlgorithm.RSA_SHA256,
            SecurityAlgorithm.RSA_OAEP,
            SecurityAlgorithm.KW_RSA_OAEP,
            SecurityAlgorithm.P_SHA256,
            SecurityAlgorithm.SHA256);

    private final String securityPolicyUri;
    private final SecurityAlgorithm symmetricSignatureAlgorithm;
    private final SecurityAlgorithm symmetricEncryptionAlgorithm;
    private final SecurityAlgorithm asymmetricSignatureAlgorithm;
    private final SecurityAlgorithm asymmetricEncryptionAlgorithm;
    private final SecurityAlgorithm asymmetricKeyWrapAlgorithm;
    private final SecurityAlgorithm keyDerivationAlgorithm;
    private final SecurityAlgorithm certificateSignatureAlgorithm;

    SecurityPolicy(String securityPolicyUri,
                   SecurityAlgorithm symmetricSignatureAlgorithm,
                   SecurityAlgorithm symmetricEncryptionAlgorithm,
                   SecurityAlgorithm asymmetricSignatureAlgorithm,
                   SecurityAlgorithm asymmetricEncryptionAlgorithm,
                   SecurityAlgorithm asymmetricKeyWrapAlgorithm,
                   SecurityAlgorithm keyDerivationAlgorithm,
                   SecurityAlgorithm certificateSignatureAlgorithm) {

        this.securityPolicyUri = securityPolicyUri;
        this.symmetricSignatureAlgorithm = symmetricSignatureAlgorithm;
        this.symmetricEncryptionAlgorithm = symmetricEncryptionAlgorithm;
        this.asymmetricSignatureAlgorithm = asymmetricSignatureAlgorithm;
        this.asymmetricEncryptionAlgorithm = asymmetricEncryptionAlgorithm;
        this.asymmetricKeyWrapAlgorithm = asymmetricKeyWrapAlgorithm;
        this.keyDerivationAlgorithm = keyDerivationAlgorithm;
        this.certificateSignatureAlgorithm = certificateSignatureAlgorithm;
    }

    public String getSecurityPolicyUri() {
        return securityPolicyUri;
    }

    public SecurityAlgorithm getSymmetricSignatureAlgorithm() {
        return symmetricSignatureAlgorithm;
    }

    public SecurityAlgorithm getSymmetricEncryptionAlgorithm() {
        return symmetricEncryptionAlgorithm;
    }

    public SecurityAlgorithm getAsymmetricSignatureAlgorithm() {
        return asymmetricSignatureAlgorithm;
    }

    public SecurityAlgorithm getAsymmetricEncryptionAlgorithm() {
        return asymmetricEncryptionAlgorithm;
    }

    public SecurityAlgorithm getAsymmetricKeyWrapAlgorithm() {
        return asymmetricKeyWrapAlgorithm;
    }

    public SecurityAlgorithm getKeyDerivationAlgorithm() {
        return keyDerivationAlgorithm;
    }

    public SecurityAlgorithm getCertificateSignatureAlgorithm() {
        return certificateSignatureAlgorithm;
    }

    public static SecurityPolicy fromUri(String securityPolicyUri) throws UaException {
        for (SecurityPolicy securityPolicy : values()) {
            if (securityPolicy.getSecurityPolicyUri().equals(securityPolicyUri)) {
                return securityPolicy;
            }
        }

        throw new UaException(StatusCodes.Bad_SecurityPolicyRejected,
                "unknown securityPolicyUri: " + securityPolicyUri);
    }

    public static Optional<SecurityPolicy> fromUriSafe(String securityPolicyUri) {
        try {
            return Optional.of(fromUri(securityPolicyUri));
        } catch (Throwable t) {
            return Optional.empty();
        }
    }

}
