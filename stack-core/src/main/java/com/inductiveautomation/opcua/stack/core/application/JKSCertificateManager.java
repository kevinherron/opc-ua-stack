package com.inductiveautomation.opcua.stack.core.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.security.KeyPair;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.util.CertificateUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JksCertificateManager implements CertificateManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final Map<ByteString, KeyPair> privateKeys = Maps.newConcurrentMap();
    private final Map<ByteString, X509Certificate> privateCertificates = Maps.newConcurrentMap();

    private final Set<X509Certificate> trustedCertificates = Sets.newConcurrentHashSet();
    private final Set<X509Certificate> authorityCertificates = Sets.newConcurrentHashSet();

    private final File trustedDir;
    private final File rejectedDir;
    private final File revocationDir;

    public JksCertificateManager(List<KeyPair> privateKeys,
                                 List<X509Certificate> privateCertificates,
                                 File trustedDir,
                                 File rejectedDir,
                                 File revocationDir) {

        this.trustedDir = trustedDir;
        this.rejectedDir = rejectedDir;
        this.revocationDir = revocationDir;

        createWatchServices();

        Set<X509Certificate> certificates = getCertificates(trustedDir);

        certificates.stream()
                .filter(c -> c.getBasicConstraints() == -1)
                .forEach(trustedCertificates::add);

        certificates.stream()
                .filter(c -> c.getBasicConstraints() != -1)
                .forEach(authorityCertificates::add);


    }

    private void createWatchServices() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            WatchKey key = trustedDir.toPath().register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY
            );
        } catch (IOException e) {
            logger.error("Error creating WatchService.", e);
        }
    }


    @Override
    public Optional<KeyPair> getKeyPair(ByteString thumbprint) {
        return Optional.ofNullable(privateKeys.get(thumbprint));
    }

    @Override
    public Optional<X509Certificate> getCertificate(ByteString thumbprint) {
        return Optional.ofNullable(privateCertificates.get(thumbprint));
    }

    @Override
    public Set<X509Certificate> getTrustList() {
        return trustedCertificates;
    }

    @Override
    public Set<X509Certificate> getAuthorityList() {
        return authorityCertificates;
    }

    @Override
    public void certificateRejected(X509Certificate certificate) {
        String signature = ByteBufUtil.hexDump(Unpooled.wrappedBuffer(certificate.getSignature()));
        File f = new File(rejectedDir.getAbsolutePath() + File.separator + signature + ".der");

        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(certificate.getEncoded());
            fos.flush();
            fos.close();
        } catch (CertificateEncodingException | IOException e) {
            logger.error("Error adding rejected certificate entry.", e);
        }
    }

    private Set<X509Certificate> getCertificates(File dir) {
        return Arrays.stream(dir.listFiles())
                .map(this::file2certificate)
                .filter(c -> c != null)
                .collect(Collectors.toSet());
    }

    private X509Certificate file2certificate(File f) {
        try {
            return CertificateUtil.decodeCertificate(new FileInputStream(f));
        } catch (UaException | FileNotFoundException ignored) {
            return null;
        }
    }

}
