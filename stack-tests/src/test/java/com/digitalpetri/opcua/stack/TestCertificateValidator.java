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

package com.digitalpetri.opcua.stack;

import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.CertificateValidator;
import com.digitalpetri.opcua.stack.core.util.CertificateValidationUtil;
import com.google.common.collect.Sets;

public class TestCertificateValidator implements CertificateValidator {

    private final Set<X509Certificate> trustedCertificates = Sets.newConcurrentHashSet();

    public TestCertificateValidator(X509Certificate certificate) {
        trustedCertificates.add(certificate);
    }

    public TestCertificateValidator(X509Certificate... certificates) {
        Collections.addAll(trustedCertificates, certificates);
    }

    @Override
    public void validate(X509Certificate certificate) throws UaException {
        CertificateValidationUtil.validateCertificateValidity(certificate);
    }

    @Override
    public void verifyTrustChain(X509Certificate certificate, List<X509Certificate> chain) throws UaException {
        CertificateValidationUtil.validateTrustChain(
                certificate, chain, trustedCertificates, Sets.<X509Certificate>newHashSet());
    }

}
