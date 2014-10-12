package com.inductiveautomation.opcua.stack.client;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.concurrent.ExecutorService;

import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ApplicationType;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;

public class UaTcpClientBuilder {

    public static final long DEFAULT_REQUEST_TIMEOUT = 10000L;

    private LocalizedText applicationName = LocalizedText.english("client application name not configured");
    private String applicationUri = "client application uri not configured";
    private String productUri = "http://www.inductiveautomation.com/opc-ua/stack";

    private ChannelConfig channelConfig = ChannelConfig.DEFAULT;

    private long requestTimeout = DEFAULT_REQUEST_TIMEOUT;
    private ExecutorService executor = Stack.sharedExecutor();

    private KeyPair keyPair;
    private Certificate certificate;

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

    public UaTcpClientBuilder setCertificate(Certificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public UaTcpClient build(String endpointUrl) {
        ApplicationDescription application = new ApplicationDescription(
                applicationUri,
                productUri,
                applicationName,
                ApplicationType.Client,
                null, null, null
        );

        return new UaTcpClient(application, endpointUrl, requestTimeout, channelConfig, executor);
    }

    public UaTcpClient build(EndpointDescription endpoint) throws UaException {
        ApplicationDescription application = new ApplicationDescription(
                applicationUri,
                productUri,
                applicationName,
                ApplicationType.Client,
                null, null, null
        );

        return new UaTcpClient(application, endpoint, keyPair, certificate, requestTimeout, channelConfig, executor);
    }

}
