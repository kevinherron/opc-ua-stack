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

package com.digitalpetri.opcua.stack.core.application;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.Set;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;

public interface CertificateManager {

    Optional<KeyPair> getKeyPair(ByteString thumbprint);

    Optional<X509Certificate> getCertificate(ByteString thumbprint);

    Set<KeyPair> getKeyPairs();

    Set<X509Certificate> getCertificates();

    Set<X509Certificate> getTrustList();

    Set<X509Certificate> getAuthorityList();

    void certificateRejected(X509Certificate certificate);

}
