package com.inductiveautomation.opcua.stack.client;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;

import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ApplicationType;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaTcpClientBuilder {

    public static final long DEFAULT_REQUEST_TIMEOUT_MS = 10000L;
    public static final UInteger DEFAULT_CHANNEL_LIFETIME_MS = uint(60 * 60 * 1000);

    private LocalizedText applicationName = LocalizedText.english("client application name not configured");
    private String applicationUri = "client application uri not configured";
    private String productUri = "http://www.inductiveautomation.com/opc-ua/stack";

    private ChannelConfig channelConfig = ChannelConfig.DEFAULT;

    private long requestTimeout = DEFAULT_REQUEST_TIMEOUT_MS;
    private UInteger channelLifetime = DEFAULT_CHANNEL_LIFETIME_MS;
    private ExecutorService executor = Stack.sharedExecutor();

    private KeyPair keyPair;
    private X509Certificate certificate;

    public UaTcpClientBuilder setApplicationName(LocalizedText applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public UaTcpClientBuilder setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
        return this;
    }

    public UaTcpClientBuilder setProductUri(String productUri) {
        this.productUri = productUri;
        return this;
    }

    public UaTcpClientBuilder setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
        return this;
    }

    public UaTcpClientBuilder setChannelLifetime(UInteger channelLifetime) {
        this.channelLifetime = channelLifetime;
        return this;
    }

    public UaTcpClientBuilder setRequestTimeout(long requestTimeout) {
        this.requestTimeout = requestTimeout;
        return this;
    }

    public UaTcpClientBuilder setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public UaTcpClientBuilder setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
        return this;
    }

    public UaTcpClientBuilder setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
        return this;
    }

    /**
     * Build a {@link UaTcpClient} using only a URL; this is only used for no security connections to gather
     * {@link EndpointDescription}s.
     */
    UaTcpClient build(String endpointUrl) {
        ApplicationDescription application = new ApplicationDescription(
                applicationUri,
                productUri,
                applicationName,
                ApplicationType.Client,
                null, null, null);

        return new UaTcpClient(endpointUrl, application, requestTimeout, channelConfig, channelLifetime, executor);
    }

    public UaTcpClient build(EndpointDescription endpoint) throws UaException {
        ApplicationDescription application = new ApplicationDescription(
                applicationUri,
                productUri,
                applicationName,
                ApplicationType.Client,
                null, null, null);

        return new UaTcpClient(
                endpoint,
                application,
                keyPair,
                certificate,
                requestTimeout,
                channelConfig,
                channelLifetime,
                executor);
    }

}
