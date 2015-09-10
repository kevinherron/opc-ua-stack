/*
 * Copyright 2014
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

package com.digitalpetri.opcua.stack;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryCertificateManager implements CertificateManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final KeyPair keyPair;
    private final X509Certificate certificate;
    private final Set<X509Certificate> trustList;

    public InMemoryCertificateManager(KeyPair keyPair, X509Certificate certificate, Set<X509Certificate> trustList) {
        this.keyPair = keyPair;
        this.certificate = certificate;
        this.trustList = trustList;
    }

    @Override
    public Optional<KeyPair> getKeyPair(ByteString thumbprint) {
        return Optional.of(keyPair);
    }

    @Override
    public Optional<X509Certificate> getCertificate(ByteString thumbprint) {
        return Optional.of(certificate);
    }

    @Override
    public Set<KeyPair> getKeyPairs() {
        return Sets.newHashSet(keyPair);
    }

    @Override
    public Set<X509Certificate> getCertificates() {
        return Sets.newHashSet(certificate);
    }

    @Override
    public Set<X509Certificate> getTrustList() {
        return trustList;
    }

    @Override
    public Set<X509Certificate> getAuthorityList() {
        return new HashSet<>();
    }

    @Override
    public void certificateRejected(X509Certificate certificate) {
        logger.info("Certificate rejected: {}", certificate);
    }

}
