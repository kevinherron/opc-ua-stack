package com.inductiveautomation.opcua.stack.core.util;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.google.common.base.Preconditions;

public class CertificateUtil {

    /**
     * Decode a DER-encoded X.509 certificate.
     *
     * @param certificateBytes DER-encoded certificate bytes
     * @return an {@link X509Certificate}
     * @throws UaException if decoding the certificate fails.
     */
    public static X509Certificate decode(byte[] certificateBytes) throws UaException {
        Preconditions.checkNotNull(certificateBytes, "certificateBytes cannot be null");

        CertificateFactory factory;

        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new UaException(StatusCodes.Bad_InternalError, e);
        }

        try {
            return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certificateBytes));
        } catch (CertificateException e) {
            throw new UaException(StatusCodes.Bad_CertificateInvalid, e);
        }
    }

}
