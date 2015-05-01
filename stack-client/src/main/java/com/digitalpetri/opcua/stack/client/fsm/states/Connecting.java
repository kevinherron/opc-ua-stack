package com.digitalpetri.opcua.stack.client.fsm.states;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.util.CertificateUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connecting implements ConnectionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile Channel channel;

    private final CompletableFuture<Channel> future;

    public Connecting(CompletableFuture<Channel> future) {
        this.future = future;
    }

    @Override
    public void activate(ConnectionStateEvent fromEvent, ConnectionStateContext context) {
        UaTcpStackClient client = context.getClient();

        ClientSecureChannel secureChannel = createSecureChannel(client);
        client.setSecureChannel(secureChannel);

        UaTcpStackClient.bootstrap(context.getClient()).whenComplete((channel, ex) -> {
            if (channel != null) {
                logger.debug("Connect succeeded: {}", channel);
                this.channel = channel;
                context.handleEvent(ConnectionStateEvent.CONNECT_SUCCESS);
            } else {
                context.handleEvent(ConnectionStateEvent.ERR_CONNECT_FAILED);
                future.completeExceptionally(ex);
            }
        });
    }

    private ClientSecureChannel createSecureChannel(UaTcpStackClient client) {
        return client.getEndpoint().map(endpoint -> {
            ClientSecureChannel secureChannel = new ClientSecureChannel(
                    SecurityPolicy.None, MessageSecurityMode.None);

            X509Certificate remoteCertificate = null;
            List<X509Certificate> remoteCertificateChain = null;

            if (!endpoint.getServerCertificate().isNull()) {
                try {
                    byte[] bs = endpoint.getServerCertificate().bytes();
                    remoteCertificate = CertificateUtil.decodeCertificate(bs);
                    remoteCertificateChain = CertificateUtil.decodeCertificates(bs);
                    SecurityPolicy securityPolicy = SecurityPolicy.fromUri(endpoint.getSecurityPolicyUri());

                    if (securityPolicy != SecurityPolicy.None) {
                        secureChannel = new ClientSecureChannel(
                                client.getKeyPair().orElse(null),
                                client.getCertificate().orElse(null),
                                remoteCertificate,
                                remoteCertificateChain,
                                securityPolicy,
                                endpoint.getSecurityMode());
                    }
                } catch (UaException e) {
                    logger.warn("Unable to create ClientSecureChannel: {}", e.getMessage(), e);
                }
            }

            return secureChannel;
        }).orElse(new ClientSecureChannel(SecurityPolicy.None, MessageSecurityMode.None));
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_SUCCESS:
                return new Connected(future, channel);

            case DISCONNECT_REQUESTED:
                return new Disconnecting(future);

            case ERR_CONNECT_FAILED:
                return new Disconnected();
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return future;
    }

}
