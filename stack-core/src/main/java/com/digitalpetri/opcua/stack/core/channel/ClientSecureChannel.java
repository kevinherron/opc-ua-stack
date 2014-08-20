package com.digitalpetri.opcua.stack.core.channel;

import java.security.KeyPair;
import java.security.cert.Certificate;

import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import io.netty.util.DefaultAttributeMap;

public class ClientSecureChannel extends DefaultAttributeMap implements SecureChannel {

    private volatile long channelId = 0;
    private volatile ChannelSecurity channelSecurity;
    private volatile ByteString localNonce = ByteString.NullValue;
    private volatile ByteString remoteNonce = ByteString.NullValue;

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

    public void setChannelSecurity(ChannelSecurity channelSecurity) {
        this.channelSecurity = channelSecurity;
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
    public ByteString getLocalNonce() {
        return localNonce;
    }

    @Override
    public ByteString getRemoteNonce() {
        return remoteNonce;
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

    @Override
    public ChannelSecurity getChannelSecurity() {
        return channelSecurity;
    }

    @Override
    public ChannelSecurity.SecretKeys getEncryptionKeys(ChannelSecurity.SecuritySecrets secretKeys) {
        return secretKeys.getClientKeys();
    }

    @Override
    public ChannelSecurity.SecretKeys getDecryptionKeys(ChannelSecurity.SecuritySecrets secretKeys) {
        return secretKeys.getServerKeys();
    }

}
