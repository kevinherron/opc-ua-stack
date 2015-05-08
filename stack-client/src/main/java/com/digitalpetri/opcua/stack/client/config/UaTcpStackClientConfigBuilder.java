package com.digitalpetri.opcua.stack.client.config;

import javax.annotation.Nullable;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpStackClientConfigBuilder {

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

    public UaTcpStackClientConfigBuilder setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public UaTcpStackClientConfigBuilder setEndpoint(EndpointDescription endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public UaTcpStackClientConfigBuilder setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
        return this;
    }

    public UaTcpStackClientConfigBuilder setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public UaTcpStackClientConfigBuilder setApplicationName(LocalizedText applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public UaTcpStackClientConfigBuilder setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
        return this;
    }

    public UaTcpStackClientConfigBuilder setProductUri(String productUri) {
        this.productUri = productUri;
        return this;
    }

    public UaTcpStackClientConfigBuilder setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
        return this;
    }

    public UaTcpStackClientConfigBuilder setChannelLifetime(UInteger channelLifetime) {
        this.channelLifetime = channelLifetime;
        return this;
    }

    public UaTcpStackClientConfigBuilder setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public UaTcpStackClientConfig build() {
        return new UaTcpStackClientConfigImpl(
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

    public static class UaTcpStackClientConfigImpl implements UaTcpStackClientConfig {

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

        public UaTcpStackClientConfigImpl(@Nullable String endpointUrl,
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

        @Override
        public Optional<String> getEndpointUrl() {
            return Optional.ofNullable(endpointUrl);
        }

        @Override
        public Optional<EndpointDescription> getEndpoint() {
            return Optional.ofNullable(endpoint);
        }

        @Override
        public Optional<KeyPair> getKeyPair() {
            return Optional.ofNullable(keyPair);
        }

        @Override
        public Optional<X509Certificate> getCertificate() {
            return Optional.ofNullable(certificate);
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
        public UInteger getChannelLifetime() {
            return channelLifetime;
        }

        @Override
        public ExecutorService getExecutor() {
            return executor;
        }

    }

}
