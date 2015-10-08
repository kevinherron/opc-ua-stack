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

package com.digitalpetri.opcua.stack.core.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.google.common.base.Preconditions;

public class CertificateUtil {

    public static final int SUBJECT_ALT_NAME_URI = 6;
    public static final int SUBJECT_ALT_NAME_DNS_NAME = 2;
    public static final int SUBJECT_ALT_NAME_IP_ADDRESS = 7;

    /**
     * Decode a DER-encoded X.509 certificate.
     *
     * @param certificateBytes DER-encoded certificate bytes.
     * @return an {@link X509Certificate}
     * @throws UaException if decoding the certificate fails.
     */
    public static X509Certificate decodeCertificate(byte[] certificateBytes) throws UaException {
        Preconditions.checkNotNull(certificateBytes, "certificateBytes cannot be null");

        return decodeCertificate(new ByteArrayInputStream(certificateBytes));
    }

    /**
     * Decode a DER-encoded X.509 certificate.
     *
     * @param inputStream {@link InputStream} containing DER-encoded certificate bytes.
     * @return an {@link X509Certificate}
     * @throws UaException if decoding the certificate fails.
     */
    public static X509Certificate decodeCertificate(InputStream inputStream) throws UaException {
        Preconditions.checkNotNull(inputStream, "inputStream cannot be null");

        CertificateFactory factory;

        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new UaException(StatusCodes.Bad_InternalError, e);
        }

        try {
            return (X509Certificate) factory.generateCertificate(inputStream);
        } catch (CertificateException | ClassCastException e) {
            throw new UaException(StatusCodes.Bad_CertificateInvalid, e);
        }
    }

    /**
     * Decode a {@link CertPath}.
     *
     * @param certificateBytes the byte[] to decode from.
     * @return a {@link CertPath}
     * @throws UaException if decoding the {@link CertPath} fails.
     */
    public static List<X509Certificate> decodeCertificates(byte[] certificateBytes) throws UaException {
        Preconditions.checkNotNull(certificateBytes, "certificateBytes cannot be null");

        CertificateFactory factory;

        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new UaException(StatusCodes.Bad_InternalError, e);
        }

        try {
            Collection<? extends Certificate> certificates =
                    factory.generateCertificates(new ByteArrayInputStream(certificateBytes));

            return certificates.stream()
                    .map(X509Certificate.class::cast)
                    .collect(Collectors.toList());
        } catch (CertificateException e) {
            throw new UaException(StatusCodes.Bad_CertificateInvalid, e);
        }
    }

    /**
     * Extract the value of a given SubjectAltName field from a {@link X509Certificate}.
     *
     * @param certificate the certificate.
     * @param field       the field number.
     * @return an {@link Optional} containing the value in the field.
     * @see #SUBJECT_ALT_NAME_IP_ADDRESS
     * @see #SUBJECT_ALT_NAME_DNS_NAME
     * @see #SUBJECT_ALT_NAME_URI
     */
    public static Optional<Object> getSubjectAltNameField(X509Certificate certificate, int field) {
        try {
            Collection<List<?>> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) subjectAltNames = Collections.emptyList();

            for (List<?> idAndValue : subjectAltNames) {
                if (idAndValue != null && idAndValue.size() == 2) {
                    if (idAndValue.get(0).equals(field)) {
                        return Optional.ofNullable(idAndValue.get(1));
                    }
                }
            }

            return Optional.empty();
        } catch (CertificateParsingException e) {
            return Optional.empty();
        }
    }

}
