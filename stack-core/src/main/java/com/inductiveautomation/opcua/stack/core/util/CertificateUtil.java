package com.inductiveautomation.opcua.stack.core.util;

import java.io.ByteArrayInputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;

public class CertificateUtil {

    /**
     * Decode a DER-encoded X.509 certificate.
     *
     * @param certificateBytes DER-encoded certificate bytes
     * @return an {@link X509Certificate}
     * @throws UaException if decoding the certificate fails.
     */
    public static X509Certificate decodeCertificate(byte[] certificateBytes) throws UaException {
        Preconditions.checkNotNull(certificateBytes, "certificateBytes cannot be null");

        CertificateFactory factory;

        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new UaException(StatusCodes.Bad_InternalError, e);
        }

        try {
            return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certificateBytes));
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

}
