package com.inductiveautomation.opcua.stack.server.tcp;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.application.CertificateManager;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;

public class UaTcpServerBuilder {

    private String serverName = "";
    private LocalizedText applicationName = LocalizedText.english("server application name not configured");
    private String applicationUri = "server application uri not configured";
    private String productUri = "http://www.inductiveautomation.com/opc-ua/stack";

    private ChannelConfig channelConfig = ChannelConfig.DEFAULT;

    private CertificateManager certificateManager;
    private ExecutorService executor = Stack.sharedExecutor();
    private List<UserTokenPolicy> userTokenPolicies = Lists.newArrayList();
    private List<SignedSoftwareCertificate> softwareCertificates = Lists.newArrayList();

    public UaTcpServerBuilder setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

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

    public UaTcpServerBuilder setCertificateManager(CertificateManager certificateManager) {
        this.certificateManager = certificateManager;
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
                serverName,
                applicationName,
                applicationUri,
                productUri,
                certificateManager,
                executor,
                userTokenPolicies,
                softwareCertificates,
                channelConfig
        );
    }

}
