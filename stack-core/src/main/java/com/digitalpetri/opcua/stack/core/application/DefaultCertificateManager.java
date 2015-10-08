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

package com.digitalpetri.opcua.stack.core.application;

import java.security.KeyPair;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.util.DigestUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.collect.Lists.newArrayList;

public class DefaultCertificateManager implements CertificateManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<ByteString, KeyPair> privateKeys = Maps.newConcurrentMap();
    private final Map<ByteString, X509Certificate> certificates = Maps.newConcurrentMap();

    public DefaultCertificateManager() {
        this((KeyPair) null, null);
    }

    public DefaultCertificateManager(KeyPair privateKey, X509Certificate certificate) {
        this(newArrayList(privateKey), newArrayList(certificate));
    }

    public DefaultCertificateManager(List<KeyPair> privateKeys,
                                     List<X509Certificate> certificates) {

        Preconditions.checkState(privateKeys.size() == certificates.size(),
                "privateKeys.size() and certificates.size() must be equal");

        for (int i = 0; i < privateKeys.size(); i++) {
            KeyPair privateKey = privateKeys.get(0);
            X509Certificate certificate = certificates.get(0);

            if (privateKey != null && certificate != null) {
                try {
                    ByteString thumbprint = ByteString.of(DigestUtil.sha1(certificate.getEncoded()));

                    this.privateKeys.put(thumbprint, privateKey);
                    this.certificates.put(thumbprint, certificate);
                } catch (CertificateEncodingException e) {
                    logger.error("Error getting certificate thumbprint.", e);
                }
            }
        }
    }

    @Override
    public Optional<KeyPair> getKeyPair(ByteString thumbprint) {
        return Optional.ofNullable(privateKeys.get(thumbprint));
    }

    @Override
    public Optional<X509Certificate> getCertificate(ByteString thumbprint) {
        return Optional.ofNullable(certificates.get(thumbprint));
    }

    @Override
    public Set<KeyPair> getKeyPairs() {
        return Sets.newHashSet(privateKeys.values());
    }

    @Override
    public Set<X509Certificate> getCertificates() {
        return Sets.newHashSet(certificates.values());
    }

}
