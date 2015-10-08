/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.security;

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;

public enum SecurityPolicy {

    /**
     * A suite of algorithms that do not provide any security settings.
     */
    None("http://opcfoundation.org/UA/SecurityPolicy#None",
            SecurityAlgorithm.None,
            SecurityAlgorithm.None,
            SecurityAlgorithm.None,
            SecurityAlgorithm.None,
            SecurityAlgorithm.None,
            SecurityAlgorithm.None,
            SecurityAlgorithm.None),

    /**
     * A suite of algorithms that use RSA for asymmetric encryption and AES-128 for symmetric encryption.
     */
    Basic128Rsa15("http://opcfoundation.org/UA/SecurityPolicy#Basic128Rsa15",
            SecurityAlgorithm.HmacSha1,
            SecurityAlgorithm.Aes128,
            SecurityAlgorithm.RsaSha1,
            SecurityAlgorithm.Rsa15,
            SecurityAlgorithm.KwRsa15,
            SecurityAlgorithm.PSha1,
            SecurityAlgorithm.Sha1),

    Basic256("http://opcfoundation.org/UA/SecurityPolicy#Basic256",
            SecurityAlgorithm.HmacSha1,
            SecurityAlgorithm.Aes256,
            SecurityAlgorithm.RsaSha1,
            SecurityAlgorithm.RsaOaep,
            SecurityAlgorithm.KwRsaOaep,
            SecurityAlgorithm.PSha1,
            SecurityAlgorithm.Sha1),

    Basic256Sha256("http://opcfoundation.org/UA/SecurityPolicy#Basic256Sha256",
            SecurityAlgorithm.HmacSha256,
            SecurityAlgorithm.Aes256,
            SecurityAlgorithm.RsaSha256,
            SecurityAlgorithm.RsaOaep,
            SecurityAlgorithm.KwRsaOaep,
            SecurityAlgorithm.PSha256,
            SecurityAlgorithm.Sha256);

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
