package com.digitalpetri.opcua.stack.server.config;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class UaTcpStackServerConfigBuilder {

    private String serverName = "";
    private LocalizedText applicationName = LocalizedText
            .english("server application name not configured");
    private String applicationUri = "server application uri not configured";
    private String productUri = "server product uri not configured";

    private ChannelConfig channelConfig = ChannelConfig.DEFAULT;

    private CertificateManager certificateManager;
    private ExecutorService executor = Stack.sharedExecutor();
    private List<UserTokenPolicy> userTokenPolicies = Lists.newArrayList();
    private List<SignedSoftwareCertificate> softwareCertificates = Lists.newArrayList();


    public UaTcpStackServerConfigBuilder setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public UaTcpStackServerConfigBuilder setApplicationName(LocalizedText applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public UaTcpStackServerConfigBuilder setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
        return this;
    }

    public UaTcpStackServerConfigBuilder setProductUri(String productUri) {
        this.productUri = productUri;
        return this;
    }

    public UaTcpStackServerConfigBuilder setCertificateManager(CertificateManager certificateManager) {
        this.certificateManager = certificateManager;
        return this;
    }

    public UaTcpStackServerConfigBuilder setUserTokenPolicies(List<UserTokenPolicy> userTokenPolicies) {
        this.userTokenPolicies = userTokenPolicies;
        return this;
    }

    public UaTcpStackServerConfigBuilder setSoftwareCertificates(List<SignedSoftwareCertificate> softwareCertificates) {
        this.softwareCertificates = softwareCertificates;
        return this;
    }

    public UaTcpStackServerConfigBuilder setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public UaTcpStackServerConfigBuilder setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
        return this;
    }

    public UaTcpStackServerConfig build() {
        Preconditions.checkNotNull(certificateManager, "certificateManager must be non-null");

        return new UaTcpStackServerConfigImpl(
                serverName,
                applicationName,
                applicationUri,
                productUri,
                channelConfig,
                certificateManager,
                executor,
                userTokenPolicies,
                softwareCertificates
        );
    }

    private static class UaTcpStackServerConfigImpl implements UaTcpStackServerConfig {

        private final String serverName;
        private final LocalizedText applicationName;
        private final String applicationUri;
        private final String productUri;

        private final ChannelConfig channelConfig;

        private final CertificateManager certificateManager;
        private final ExecutorService executor;
        private final List<UserTokenPolicy> userTokenPolicies;
        private final List<SignedSoftwareCertificate> softwareCertificates;

        public UaTcpStackServerConfigImpl(String serverName,
                                          LocalizedText applicationName,
                                          String applicationUri,
                                          String productUri,
                                          ChannelConfig channelConfig,
                                          CertificateManager certificateManager,
                                          ExecutorService executor,
                                          List<UserTokenPolicy> userTokenPolicies,
                                          List<SignedSoftwareCertificate> softwareCertificates) {

            this.serverName = serverName;
            this.applicationName = applicationName;
            this.applicationUri = applicationUri;
            this.productUri = productUri;
            this.channelConfig = channelConfig;
            this.certificateManager = certificateManager;
            this.executor = executor;
            this.userTokenPolicies = userTokenPolicies;
            this.softwareCertificates = softwareCertificates;
        }

        @Override
        public String getServerName() {
            return serverName;
        }

        @Override
        public LocalizedText getApplicationName() {
            return applicationName;
        }

        @Override
        public String getApplicationUri() {
            return applicationUri;
        }

        @Override
        public String getProductUri() {
            return productUri;
        }

        @Override
        public ChannelConfig getChannelConfig() {
            return channelConfig;
        }

        @Override
        public CertificateManager getCertificateManager() {
            return certificateManager;
        }

        @Override
        public ExecutorService getExecutor() {
            return executor;
        }

        @Override
        public List<UserTokenPolicy> getUserTokenPolicies() {
            return userTokenPolicies;
        }

        @Override
        public List<SignedSoftwareCertificate> getSoftwareCertificates() {
            return softwareCertificates;
        }

    }

}
