package com.digitalpetri.opcua.stack.core.channels;

import java.security.KeyPair;
import java.security.cert.Certificate;

import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import io.netty.channel.Channel;

public class ServerSecureChannel implements SecureChannel {

    private volatile Channel channel;

    private volatile long channelId = 0;
    private volatile long currentTokenId = 0;
    private volatile long previousTokenId = -1;
    private volatile ByteString localNonce = ByteString.NullValue;
    private volatile ByteString remoteNonce = ByteString.NullValue;
    private volatile ChannelSecrets channelSecrets;

    private volatile KeyPair keyPair;
    private volatile Certificate localCertificate;
    private volatile Certificate remoteCertificate;
    private volatile SecurityPolicy securityPolicy;
    private volatile MessageSecurityMode messageSecurityMode;

    public void bind(Channel channel) {
        this.channel = channel;
    }

    public void unbind() {
        this.channel = null;
    }

    public Channel getBoundChannel() {
        return channel;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public void setCurrentTokenId(long currentTokenId) {
        this.currentTokenId = currentTokenId;
    }

    public void setPreviousTokenId(long previousTokenId) {
        this.previousTokenId = previousTokenId;
    }

    public void setLocalNonce(ByteString localNonce) {
        this.localNonce = localNonce;
    }

    public void setRemoteNonce(ByteString remoteNonce) {
        this.remoteNonce = remoteNonce;
    }

    public void setChannelSecrets(ChannelSecrets channelSecrets) {
        this.channelSecrets = channelSecrets;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public void setLocalCertificate(Certificate localCertificate) {
        this.localCertificate = localCertificate;
    }

    public void setRemoteCertificate(Certificate remoteCertificate) {
        this.remoteCertificate = remoteCertificate;
    }

    public void setSecurityPolicy(SecurityPolicy securityPolicy) {
        this.securityPolicy = securityPolicy;
    }

    public void setMessageSecurityMode(MessageSecurityMode messageSecurityMode) {
        this.messageSecurityMode = messageSecurityMode;
    }

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public Certificate getLocalCertificate() {
        return localCertificate;
    }

    @Override
    public Certificate getRemoteCertificate() {
        return remoteCertificate;
    }

    @Override
    public SecurityPolicy getSecurityPolicy() {
        return securityPolicy;
    }

    @Override
    public MessageSecurityMode getMessageSecurityMode() {
        return messageSecurityMode;
    }

    @Override
    public long getChannelId() {
        return channelId;
    }

    @Override
    public long getCurrentTokenId() {
        return currentTokenId;
    }

    @Override
    public long getPreviousTokenId() {
        return previousTokenId;
    }

    @Override
    public ByteString getLocalNonce() {
        return localNonce;
    }

    @Override
    public ByteString getRemoteNonce() {
        return remoteNonce;
    }

    @Override
    public ChannelSecrets.SecretKeys getEncryptionKeys() {
        return channelSecrets.getServerSecretKeys();
    }

    @Override
    public ChannelSecrets.SecretKeys getDecryptionKeys() {
        return channelSecrets.getClientSecretKeys();
    }

}
