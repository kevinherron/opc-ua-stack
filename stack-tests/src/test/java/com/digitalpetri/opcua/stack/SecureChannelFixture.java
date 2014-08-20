package com.digitalpetri.opcua.stack;

import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ChannelSecurityToken;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;

import static com.digitalpetri.opcua.stack.core.util.NonceUtil.generateNonce;
import static com.digitalpetri.opcua.stack.core.util.NonceUtil.getNonceLength;

public abstract class SecureChannelFixture extends SecurityFixture {

    protected SecureChannel[] generateChannels(SecurityPolicy securityPolicy, MessageSecurityMode messageSecurity) {
        ByteString clientNonce = generateNonce(getNonceLength(securityPolicy.getSymmetricEncryptionAlgorithm()));
        ByteString serverNonce = generateNonce(getNonceLength(securityPolicy.getSymmetricEncryptionAlgorithm()));

        ClientSecureChannel clientChannel = new ClientSecureChannel(
                securityPolicy == SecurityPolicy.None ? null : clientKeyPair,
                securityPolicy == SecurityPolicy.None ? null : clientCertificate,
                securityPolicy == SecurityPolicy.None ? null : serverCertificate,
                securityPolicy,
                messageSecurity
        );

        clientChannel.setLocalNonce(clientNonce);
        clientChannel.setRemoteNonce(serverNonce);

        ServerSecureChannel serverChannel = new ServerSecureChannel();
        serverChannel.setSecurityPolicy(securityPolicy);
        serverChannel.setMessageSecurityMode(messageSecurity);
        serverChannel.setLocalNonce(serverNonce);
        serverChannel.setRemoteNonce(clientNonce);

        switch (securityPolicy) {
            case None:
                break;

            case Basic128Rsa15:
            case Basic256:
            case Basic256Sha256:
            default:
                if (messageSecurity != MessageSecurityMode.None) {
                    ChannelSecurity.SecuritySecrets clientSecrets = ChannelSecurity.generateKeyPair(
                            clientChannel,
                            clientChannel.getLocalNonce(),
                            clientChannel.getRemoteNonce()
                    );

                    ChannelSecurityToken clientToken = new ChannelSecurityToken(
                            0L, 1L, DateTime.now(), 60000L);

                    clientChannel.setChannelSecurity(new ChannelSecurity(clientSecrets, clientToken));
                }


                serverChannel.setKeyPair(serverKeyPair);
                serverChannel.setLocalCertificate(serverCertificate);
                serverChannel.setRemoteCertificate(clientCertificate);

                if (messageSecurity != MessageSecurityMode.None) {
                    ChannelSecurity.SecuritySecrets serverSecrets = ChannelSecurity.generateKeyPair(
                            serverChannel,
                            serverChannel.getRemoteNonce(),
                            serverChannel.getLocalNonce()
                    );

                    ChannelSecurityToken serverToken = new ChannelSecurityToken(
                            0L, 1L, DateTime.now(), 60000L);

                    serverChannel.setChannelSecurity(new ChannelSecurity(serverSecrets, serverToken));
                }

                break;
        }

        return new SecureChannel[]{clientChannel, serverChannel};
    }

}
