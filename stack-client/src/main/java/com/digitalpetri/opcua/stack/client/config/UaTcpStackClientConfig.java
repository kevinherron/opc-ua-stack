package com.digitalpetri.opcua.stack.client.config;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;

public interface UaTcpStackClientConfig {

    /**
     * The endpoint url to connect to.
     *
     * @return an {@link Optional} containing the endpoint url to connect to. A {@link UaTcpStackClientConfig} must
     * have either an endpoint url or endpoint configured.
     */
    Optional<String> getEndpointUrl();

    /**
     * The {@link EndpointDescription} to connect to. May be absent if the connection will only be used for retrieving
     * endpoints, must be present if the connection will be used beyond that.
     *
     * @return an {@link Optional} containing the {@link EndpointDescription} to connect to. A {@link UaTcpStackClient}
     * must have either an endpoint url or endpoint configured.
     */
    Optional<EndpointDescription> getEndpoint();

    /**
     * Get the {@link KeyPair} to use. May be absent if connecting without security, must be present if connecting with
     * security.
     *
     * @return an {@link Optional} containing the {@link KeyPair} to use.
     */
    Optional<KeyPair> getKeyPair();

    /**
     * Get the {@link X509Certificate} to use. May be absent if connecting without security, must be present if
     * connecting with security.
     *
     * @return an {@link Optional} containing the {@link X509Certificate} to use.
     */
    Optional<X509Certificate> getCertificate();

    /**
     * @return the name of the client application, as a {@link LocalizedText}.
     */
    LocalizedText getApplicationName();

    /**
     * @return a URI for the client's application instance. This should be the same as the URI in the client certificate, if
     * present.
     */
    String getApplicationUri();

    /**
     * @return the URI for the client's application product.
     */
    String getProductUri();

    /**
     * @return the {@link ChannelConfig} to use when creating secure channels.
     */
    ChannelConfig getChannelConfig();

    /**
     * @return the secure channel lifetime to request, in milliseconds.
     */
    UInteger getChannelLifetime();

    /**
     * @return the {@link ExecutorService} the {@link UaTcpStackClient} will use.
     */
    ExecutorService getExecutor();

    static UaTcpStackClientConfigBuilder builder() {
        return new UaTcpStackClientConfigBuilder();
    }

}
