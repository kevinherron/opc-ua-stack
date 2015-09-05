package com.digitalpetri.opcua.stack;

import com.google.common.collect.Lists;
import com.digitalpetri.opcua.stack.core.channel.ChannelSecurity;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ChannelSecurityToken;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.digitalpetri.opcua.stack.core.util.NonceUtil.generateNonce;
import static com.digitalpetri.opcua.stack.core.util.NonceUtil.getNonceLength;

public abstract class SecureChannelFixture extends SecurityFixture {

    protected SecureChannel[] generateChannels(SecurityPolicy securityPolicy, MessageSecurityMode messageSecurity) throws Exception {
        super.setUp();

        ByteString clientNonce = generateNonce(getNonceLength(securityPolicy.getSymmetricEncryptionAlgorithm()));
        ByteString serverNonce = generateNonce(getNonceLength(securityPolicy.getSymmetricEncryptionAlgorithm()));

        ClientSecureChannel clientChannel = new ClientSecureChannel(
                securityPolicy == SecurityPolicy.NONE ? null : clientKeyPair,
                securityPolicy == SecurityPolicy.NONE ? null : clientCertificate,
                securityPolicy == SecurityPolicy.NONE ? null : serverCertificate,
                securityPolicy == SecurityPolicy.NONE ? null : Lists.newArrayList(serverCertificate),
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
            case NONE:
                break;

            case BASIC_128_RSA_15:
            case BASIC_256:
            case BASIC_256_SHA256:
            default:
                if (messageSecurity != MessageSecurityMode.None) {
                    ChannelSecurity.SecuritySecrets clientSecrets = ChannelSecurity.generateKeyPair(
                            clientChannel,
                            clientChannel.getLocalNonce(),
                            clientChannel.getRemoteNonce()
                    );

                    ChannelSecurityToken clientToken = new ChannelSecurityToken(
                            uint(0), uint(1), DateTime.now(), uint(60000));

                    clientChannel.setChannelSecurity(new ChannelSecurity(clientSecrets, clientToken));
                }


                serverChannel.setKeyPair(serverKeyPair);
                serverChannel.setLocalCertificate(serverCertificate);
                serverChannel.setRemoteCertificate(clientCertificateBytes);

                if (messageSecurity != MessageSecurityMode.None) {
                    ChannelSecurity.SecuritySecrets serverSecrets = ChannelSecurity.generateKeyPair(
                            serverChannel,
                            serverChannel.getRemoteNonce(),
                            serverChannel.getLocalNonce()
                    );

                    ChannelSecurityToken serverToken = new ChannelSecurityToken(
                            uint(0), uint(1), DateTime.now(), uint(60000));

                    serverChannel.setChannelSecurity(new ChannelSecurity(serverSecrets, serverToken));
                }

                break;
        }

        return new SecureChannel[]{clientChannel, serverChannel};
    }

}
