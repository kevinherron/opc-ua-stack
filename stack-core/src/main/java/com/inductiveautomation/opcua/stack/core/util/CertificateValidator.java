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

package com.inductiveautomation.opcua.stack.core.util;

import java.security.cert.CertPathBuilder;
import java.security.cert.CertStore;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertificateValidator {

    private static final Logger logger = LoggerFactory.getLogger(CertificateValidator.class);

    private static final String KEY_USAGE_OID = "2.5.29.15";

    private static final int SUBJECT_ALT_NAME_URI = 6;
    private static final int SUBJECT_ALT_NAME_DNS_NAME = 2;
    private static final int SUBJECT_ALT_NAME_IP_ADDRESS = 7;

    private final List<X509Certificate> trustList = Lists.newCopyOnWriteArrayList();
    private final List<X509Certificate> authorityList = Lists.newCopyOnWriteArrayList();

    public CertificateValidator() {
    }

    public void validate(X509Certificate certificate,
                         List<X509Certificate> chain,
                         String hostname,
                         String applicationUri) throws UaException {

        validateCertificateValidity(certificate);
        validateHostnameOrIpAddress(certificate, hostname);
        validateApplicationUri(certificate, applicationUri);
        validateApplicationCertificateUsage(certificate);

        boolean certificateTrusted = trustList.stream()
                .anyMatch(c -> Arrays.equals(certificate.getSubjectUniqueID(), c.getSubjectUniqueID()));

        if (!certificateTrusted) {
            validateChain(certificate, chain);
        }
    }


    public void validateChain(X509Certificate certificate,
                              List<X509Certificate> chain) throws UaException {

        try {
            Set<TrustAnchor> trustAnchors = new HashSet<>();
            authorityList.forEach(ca -> trustAnchors.add(new TrustAnchor(ca, null)));

            X509CertSelector selector = new X509CertSelector();
            selector.setCertificate(certificate);

            PKIXBuilderParameters params = new PKIXBuilderParameters(trustAnchors, selector);

            params.setRevocationEnabled(false);

            CertStore intermediateCertStore =
                    CertStore.getInstance("Collection", new CollectionCertStoreParameters(chain));

            params.addCertStore(intermediateCertStore);

            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");

            PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult) builder.build(params);

            logger.debug("Validated certificate chain: {}", result.getCertPath());
        } catch (Exception e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed);
        }
    }

    public void validateCertificateValidity(X509Certificate certificate) throws UaException {
        try {
            certificate.checkValidity();
        } catch (CertificateExpiredException e) {
            throw new UaException(StatusCodes.Bad_CertificateTimeInvalid,
                    String.format("certificate is expired: %s - %s",
                            certificate.getNotBefore(), certificate.getNotAfter()));
        } catch (CertificateNotYetValidException e) {
            throw new UaException(StatusCodes.Bad_CertificateTimeInvalid,
                    String.format("certificate not yet valid: %s - %s",
                            certificate.getNotBefore(), certificate.getNotAfter()));
        }
    }

    /**
     * Validate that the hostname used in the endpoint URL matches either the SubjectAltName DNSName or IPAddress in
     * the given certificate.
     *
     * @param certificate the certificate to validate against.
     * @param hostname    the hostname used in the endpoint URL.
     * @throws UaException if there is no matching DNSName or IPAddress entry.
     */
    public void validateHostnameOrIpAddress(X509Certificate certificate, String hostname) throws UaException {
        boolean dnsNameMatches =
                validateSubjectAltNameField(certificate, SUBJECT_ALT_NAME_DNS_NAME, hostname::equals);

        boolean ipAddressMatches =
                validateSubjectAltNameField(certificate, SUBJECT_ALT_NAME_IP_ADDRESS, hostname::equals);

        if (!(dnsNameMatches || ipAddressMatches)) {
            throw new UaException(StatusCodes.Bad_CertificateHostNameInvalid);
        }
    }

    /**
     * Validate that the application URI matches the SubjectAltName URI in the given certificate.
     *
     * @param certificate    the certificate to validate against.
     * @param applicationUri the URI to validate.
     * @throws UaException if the certificate is invalid, does not contain a uri, or contains a uri that does not match.
     */
    public void validateApplicationUri(X509Certificate certificate, String applicationUri) throws UaException {
        if (!validateSubjectAltNameField(certificate, SUBJECT_ALT_NAME_URI, applicationUri::equals)) {
            throw new UaException(StatusCodes.Bad_CertificateUriInvalid);
        }
    }

    public void validateApplicationCertificateUsage(X509Certificate certificate) throws UaException {
        Set<String> criticalExtensions = certificate.getCriticalExtensionOIDs();
        if (criticalExtensions == null) criticalExtensions = new HashSet<>();

        if (criticalExtensions.contains(KEY_USAGE_OID)) {
            boolean[] keyUsage = certificate.getKeyUsage();
            boolean digitalSignature = keyUsage[0];
            boolean nonRepudiation = keyUsage[1];
            boolean keyEncipherment = keyUsage[2];
            boolean dataEncipherment = keyUsage[3];

            if (!digitalSignature) {
                throw new UaException(StatusCodes.Bad_CertificateUseNotAllowed,
                        "required KeyUsage 'digitalSignature' not found");
            }

            if (!nonRepudiation) {
                throw new UaException(StatusCodes.Bad_CertificateUseNotAllowed,
                        "required KeyUsage 'nonRepudiation' not found");
            }

            if (!keyEncipherment) {
                throw new UaException(StatusCodes.Bad_CertificateUseNotAllowed,
                        "required KeyUsage 'keyEncipherment' not found");
            }

            if (!dataEncipherment) {
                throw new UaException(StatusCodes.Bad_CertificateUseNotAllowed,
                        "required KeyUsage 'dataEncipherment' not found");
            }
        }
    }

    public boolean validateSubjectAltNameField(X509Certificate certificate, int field,
                                               Predicate<Object> fieldValidator) throws UaException {

        try {
            Collection<List<?>> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) subjectAltNames = Collections.emptyList();

            for (List<?> idAndValue : subjectAltNames) {
                if (idAndValue != null && idAndValue.size() == 2) {
                    if (idAndValue.get(0).equals(field)) {
                        if (fieldValidator.test(idAndValue.get(1))) {
                            return true;
                        }
                    }
                }
            }

            return false;
        } catch (CertificateParsingException e) {
            throw new UaException(StatusCodes.Bad_CertificateInvalid, e);
        }
    }

}
