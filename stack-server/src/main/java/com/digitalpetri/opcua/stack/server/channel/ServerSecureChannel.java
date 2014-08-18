package com.digitalpetri.opcua.stack.server.channel;

import java.security.KeyPair;
import java.security.cert.Certificate;

import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import io.netty.channel.Channel;

public class ServerSecureChannel implements SecureChannel {

    private volatile Channel channel;

    private volatile long channelId = 0;
    private volatile ChannelSecurity channelSecurity;
    private volatile ByteString localNonce = ByteString.NullValue;
    private volatile ByteString remoteNonce = ByteString.NullValue;

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

    public void setLocalNonce(ByteString localNonce) {
        this.localNonce = localNonce;
    }

    public void setRemoteNonce(ByteString remoteNonce) {
        this.remoteNonce = remoteNonce;
    }

    public void setChannelSecurity(ChannelSecurity channelSecurity) {
        this.channelSecurity = channelSecurity;
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
    public ChannelSecurity getChannelSecurity() {
        return channelSecurity;
    }

    @Override
    public ChannelSecurity.SecretKeys getEncryptionKeys(ChannelSecurity.SecuritySecrets secretKeys) {
        return secretKeys.getServerKeys();
    }

    @Override
    public ChannelSecurity.SecretKeys getDecryptionKeys(ChannelSecurity.SecuritySecrets secretKeys) {
        return secretKeys.getClientKeys();
    }

    @Override
    public ByteString getLocalNonce() {
        return localNonce;
    }
    @Override
    public ByteString getRemoteNonce() {
        return remoteNonce;
    }


}
