package com.inductiveautomation.opcua.stack.client;

import javax.annotation.Nullable;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpClientConfig {

    private final String endpointUrl;
    private final EndpointDescription endpoint;
    private final KeyPair keyPair;
    private final X509Certificate certificate;

    private final LocalizedText applicationName;
    private final String applicationUri;
    private final String productUri;

    private final ChannelConfig channelConfig;
    private final UInteger channelLifetime;
    private final ExecutorService executor;

    public UaTcpClientConfig(@Nullable String endpointUrl,
                             @Nullable EndpointDescription endpoint,
                             @Nullable KeyPair keyPair,
                             @Nullable X509Certificate certificate,
                             LocalizedText applicationName,
                             String applicationUri,
                             String productUri,
                             ChannelConfig channelConfig,
                             UInteger channelLifetime,
                             ExecutorService executor) {

        this.endpointUrl = endpointUrl;
        this.endpoint = endpoint;
        this.keyPair = keyPair;
        this.certificate = certificate;
        this.applicationName = applicationName;
        this.applicationUri = applicationUri;
        this.productUri = productUri;
        this.channelConfig = channelConfig;
        this.channelLifetime = channelLifetime;
        this.executor = executor;
    }

    public Optional<String> getEndpointUrl() {
        return Optional.ofNullable(endpointUrl);
    }

    public Optional<EndpointDescription> getEndpoint() {
        return Optional.ofNullable(endpoint);
    }

    public Optional<KeyPair> getKeyPair() {
        return Optional.ofNullable(keyPair);
    }

    public Optional<X509Certificate> getCertificate() {
        return Optional.ofNullable(certificate);
    }

    public LocalizedText getApplicationName() {
        return applicationName;
    }

    public String getApplicationUri() {
        return applicationUri;
    }

    public String getProductUri() {
        return productUri;
    }

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    public UInteger getChannelLifetime() {
        return channelLifetime;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public static UaTcpClientConfigBuilder builder() {
        return new UaTcpClientConfigBuilder();
    }

    public static class UaTcpClientConfigBuilder {

        private String endpointUrl;
        private EndpointDescription endpoint;
        private KeyPair keyPair;
        private X509Certificate certificate;

        private LocalizedText applicationName = LocalizedText.english("client application name not configured");
        private String applicationUri = "client application uri not configured";
        private String productUri = "client product uri not configured";

        private ChannelConfig channelConfig = ChannelConfig.DEFAULT;
        private UInteger channelLifetime = uint(60 * 60 * 1000);
        private ExecutorService executor = Stack.sharedExecutor();


        public UaTcpClientConfigBuilder setEndpointUrl(String endpointUrl) {
            this.endpointUrl = endpointUrl;
            return this;
        }

        public UaTcpClientConfigBuilder setEndpoint(EndpointDescription endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public UaTcpClientConfigBuilder setKeyPair(KeyPair keyPair) {
            this.keyPair = keyPair;
            return this;
        }

        public UaTcpClientConfigBuilder setCertificate(X509Certificate certificate) {
            this.certificate = certificate;
            return this;
        }

        public UaTcpClientConfigBuilder setApplicationName(LocalizedText applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public UaTcpClientConfigBuilder setApplicationUri(String applicationUri) {
            this.applicationUri = applicationUri;
            return this;
        }

        public UaTcpClientConfigBuilder setProductUri(String productUri) {
            this.productUri = productUri;
            return this;
        }

        public UaTcpClientConfigBuilder setChannelConfig(ChannelConfig channelConfig) {
            this.channelConfig = channelConfig;
            return this;
        }

        public UaTcpClientConfigBuilder setChannelLifetime(UInteger channelLifetime) {
            this.channelLifetime = channelLifetime;
            return this;
        }

        public UaTcpClientConfigBuilder setExecutor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public UaTcpClientConfig build() {
            return new UaTcpClientConfig(
                    endpointUrl,
                    endpoint,
                    keyPair,
                    certificate,
                    applicationName,
                    applicationUri,
                    productUri,
                    channelConfig,
                    channelLifetime,
                    executor);
        }

    }

}
