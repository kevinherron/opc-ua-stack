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


import java.security.cert.X509Certificate;
import java.util.List;

import com.digitalpetri.opcua.stack.core.UaException;

public interface CertificateValidator {

    /**
     * Check that the provided certificate is valid.
     *
     * @param certificate the {@link X509Certificate} to check the validity of.
     * @throws UaException if {@code certificate} is invalid.
     */
    void validate(X509Certificate certificate) throws UaException;

    /**
     * Check that the provided certificate is trusted.
     *
     * @param certificate the {@link X509Certificate} to verify.
     * @param chain       the chain of intermediate certificates, if present, that lead to a trust anchor.
     * @throws UaException if {@code certificate} is not trusted.
     */
    void verifyTrustChain(X509Certificate certificate, List<X509Certificate> chain) throws UaException;

}
