package com.digitalpetri.opcua.stack.core.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
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

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.util.CertificateUtil;
import com.digitalpetri.opcua.stack.core.util.DigestUtil;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryCertificateManager implements CertificateManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<ByteString, KeyPair> privateKeys = Maps.newConcurrentMap();
    private final Map<ByteString, X509Certificate> certificates = Maps.newConcurrentMap();

    private final Set<X509Certificate> trustedCertificates = Sets.newConcurrentHashSet();
    private final Set<X509Certificate> authorityCertificates = Sets.newConcurrentHashSet();

    private final File trustedDir;
    private final File rejectedDir;
    private final File revocationDir;

    public DirectoryCertificateManager(File certificatesBaseDir) {
        this((KeyPair) null, null, certificatesBaseDir);
    }

    public DirectoryCertificateManager(KeyPair privateKey,
                                       X509Certificate certificate,
                                       File certificatesBaseDir) {

        this(Lists.newArrayList(privateKey), Lists.newArrayList(certificate), certificatesBaseDir);
    }

    public DirectoryCertificateManager(List<KeyPair> privateKeys,
                                       List<X509Certificate> certificates,
                                       File certificatesBaseDir) {

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

        trustedDir = new File(certificatesBaseDir.getAbsolutePath() + File.separator + "trusted");
        if (!trustedDir.exists() && !trustedDir.mkdirs()) {
            logger.warn("Could not create trusted certificate dir: {}", trustedDir);
        }

        rejectedDir = new File(certificatesBaseDir.getAbsolutePath() + File.separator + "rejected");
        if (!rejectedDir.exists() && !rejectedDir.mkdirs()) {
            logger.warn("Could not create rejected certificate dir: {}", rejectedDir);
        }

        revocationDir = new File(certificatesBaseDir.getAbsolutePath() + File.separator + "revocation");
        if (!revocationDir.exists() && !revocationDir.mkdirs()) {
            logger.warn("Could not create revocation certificate dir: {}", revocationDir);
        }

        createWatchService();

        synchronizeTrustedCertificates();
    }

    private void createWatchService() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            WatchKey trustedKey = trustedDir.toPath().register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY
            );

            Thread thread = new Thread(new Watcher(watchService, trustedKey));
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            logger.error("Error creating WatchService.", e);
        }
    }

    private synchronized void synchronizeTrustedCertificates() {
        logger.debug("Synchronizing trusted certificates...");

        trustedCertificates.clear();
        authorityCertificates.clear();

        Set<X509Certificate> certificates = getCertificates(trustedDir);

        certificates.stream()
                .filter(c -> c.getBasicConstraints() == -1)
                .forEach(trustedCertificates::add);

        certificates.stream()
                .filter(c -> c.getBasicConstraints() != -1)
                .forEach(authorityCertificates::add);

        logger.debug("trustedCertificates.size()={}, authorityCertificates.size()={}",
                trustedCertificates.size(), authorityCertificates.size());
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

    @Override
    public synchronized Set<X509Certificate> getTrustList() {
        return trustedCertificates;
    }

    @Override
    public synchronized Set<X509Certificate> getAuthorityList() {
        return authorityCertificates;
    }

    @Override
    public void certificateRejected(X509Certificate certificate) {
        try {
            String[] ss = certificate.getSubjectX500Principal().getName().split(",");
            String name = ss.length > 0 ? ss[0] : certificate.getSubjectX500Principal().getName();
            String thumbprint = ByteBufUtil.hexDump(Unpooled.wrappedBuffer(DigestUtil.sha1(certificate.getEncoded())));

            File f = new File(rejectedDir.getAbsolutePath() + File.separator + String.format("%s [%s].der", name, thumbprint));

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(certificate.getEncoded());
            fos.flush();
            fos.close();
        } catch (CertificateEncodingException | IOException e) {
            logger.error("Error adding rejected certificate entry.", e);
        }
    }

    private Set<X509Certificate> getCertificates(File dir) {
        File[] files = dir.listFiles();
        if (files == null) files = new File[0];

        return Arrays.stream(files)
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

    private class Watcher implements Runnable {

        private final WatchService watchService;
        private final WatchKey trustedKey;

        public Watcher(WatchService watchService, WatchKey trustedKey) {
            this.watchService = watchService;
            this.trustedKey = trustedKey;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    WatchKey key = watchService.take();

                    if (key == trustedKey) {
                        for (WatchEvent<?> watchEvent : key.pollEvents()) {
                            Kind<?> kind = watchEvent.kind();

                            if (kind != StandardWatchEventKinds.OVERFLOW) {
                                synchronizeTrustedCertificates();
                            }
                        }
                    }

                    if (!key.reset()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
