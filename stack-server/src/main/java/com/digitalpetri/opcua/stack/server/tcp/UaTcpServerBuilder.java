package com.digitalpetri.opcua.stack.server.tcp;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.google.common.collect.Lists;

public class UaTcpServerBuilder {

    private LocalizedText applicationName = LocalizedText.english("server application name not configured");
    private String applicationUri = "server application uri not configured";
    private String productUri = "http://www.digitalpetri.com/opc-ua/stack";

    private ChannelConfig channelConfig = ChannelConfig.DEFAULT;

    private KeyPair keyPair;
    private Certificate certificate;
    private ExecutorService executor = Stack.EXECUTOR_SERVICE;
    private List<UserTokenPolicy> userTokenPolicies = Lists.newArrayList();
    private List<SignedSoftwareCertificate> softwareCertificates = Lists.newArrayList();

    public UaTcpServerBuilder setApplicationName(LocalizedText applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public UaTcpServerBuilder setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
        return this;
    }

    public UaTcpServerBuilder setProductUri(String productUri) {
        this.productUri = productUri;
        return this;
    }

    public UaTcpServerBuilder setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
        return this;
    }

    public UaTcpServerBuilder setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
        return this;
    }

    public UaTcpServerBuilder setCertificate(Certificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public UaTcpServerBuilder setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public UaTcpServerBuilder addUserTokenPolicy(UserTokenPolicy userTokenPolicy) {
        userTokenPolicies.add(userTokenPolicy);
        return this;
    }

    public UaTcpServerBuilder addSoftwareCertificate(SignedSoftwareCertificate softwareCertificate) {
        softwareCertificates.add(softwareCertificate);
        return this;
    }

    public UaTcpServer build() {
        return new UaTcpServer(
                applicationName,
                applicationUri,
                productUri,
                certificate,
                keyPair,
                executor,
                userTokenPolicies,
                softwareCertificates,
                channelConfig
        );
    }

}
