package com.digitalpetri.opcua.stack.core.channels;

import java.security.KeyPair;
import java.security.cert.Certificate;

import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;

public class ClientSecureChannel implements SecureChannel {

    private volatile long channelId = 0;
    private volatile long currentTokenId = 0;
    private volatile long previousTokenId = -1;
    private volatile ByteString localNonce = ByteString.NullValue;
    private volatile ByteString remoteNonce = ByteString.NullValue;
    private volatile ChannelSecrets channelSecrets;

    private final KeyPair keyPair;
    private final Certificate localCertificate;
    private final Certificate remoteCertificate;
    private final SecurityPolicy securityPolicy;
    private final MessageSecurityMode messageSecurityMode;

    public ClientSecureChannel(SecurityPolicy securityPolicy, MessageSecurityMode messageSecurityMode) {
        this(null, null, null, securityPolicy, messageSecurityMode);
    }

    public ClientSecureChannel(KeyPair keyPair,
                               Certificate localCertificate,
                               Certificate remoteCertificate,
                               SecurityPolicy securityPolicy,
                               MessageSecurityMode messageSecurityMode) {

        this.keyPair = keyPair;
        this.localCertificate = localCertificate;
        this.remoteCertificate = remoteCertificate;
        this.securityPolicy = securityPolicy;
        this.messageSecurityMode = messageSecurityMode;
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

    public void setChannelSecrets(ChannelSecrets channelSecrets) {
        this.channelSecrets = channelSecrets;
    }

    public void setLocalNonce(ByteString localNonce) {
        this.localNonce = localNonce;
    }

    public void setRemoteNonce(ByteString remoteNonce) {
        this.remoteNonce = remoteNonce;
    }

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
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
        return channelSecrets.getClientSecretKeys();
    }

    @Override
    public ChannelSecrets.SecretKeys getDecryptionKeys() {
        return channelSecrets.getServerSecretKeys();
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
    public Certificate getLocalCertificate() {
        return localCertificate;
    }

    @Override
    public Certificate getRemoteCertificate() {
        return remoteCertificate;
    }

}
