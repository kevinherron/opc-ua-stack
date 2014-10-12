package com.inductiveautomation.opcua.stack.server;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.util.Optional;

import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class Endpoint {

    private final URI endpointUri;
    private final SecurityPolicy securityPolicy;
    private final MessageSecurityMode messageSecurity;
    private final Optional<String> bindAddress;

    public Endpoint(@Nonnull URI endpointUri,
                    @Nonnull SecurityPolicy securityPolicy,
                    @Nonnull MessageSecurityMode messageSecurity,
                    @Nullable String bindAddress) {

        checkNotNull(endpointUri);
        checkNotNull(securityPolicy);
        checkNotNull(messageSecurity);

        this.endpointUri = endpointUri;
        this.securityPolicy = securityPolicy;
        this.messageSecurity = messageSecurity;
        this.bindAddress = Optional.ofNullable(bindAddress);
    }

    public URI getEndpointUri() {
        return endpointUri;
    }

    public SecurityPolicy getSecurityPolicy() {
        return securityPolicy;
    }

    public MessageSecurityMode getMessageSecurity() {
        return messageSecurity;
    }

    public Optional<String> getBindAddress() {
        return bindAddress;
    }

    public short getSecurityLevel() {
        short securityLevel = 0;

        switch (messageSecurity) {
            case SignAndEncrypt:
                securityLevel |= 0x80;
                break;
            case Sign:
                securityLevel |= 0x40;
                break;
            default:
                securityLevel |= 0x20;
        }

        switch(securityPolicy) {
            case Basic256Sha256:
                securityLevel |= 0x08;
                break;
            case Basic256:
                securityLevel |= 0x04;
                break;
            case Basic128Rsa15:
                securityLevel |= 0x02;
                break;
            case None:
                securityLevel |= 0x01;
                break;
        }

        return securityLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        return Objects.equal(endpointUri, endpoint.endpointUri) &&
                Objects.equal(securityPolicy, endpoint.securityPolicy) &&
                Objects.equal(messageSecurity, endpoint.messageSecurity) &&
                Objects.equal(bindAddress, endpoint.bindAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(endpointUri, securityPolicy, messageSecurity, bindAddress);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("uri", endpointUri)
                .add("securityPolicy", securityPolicy)
                .add("messageSecurity", messageSecurity)
                .add("bindAddress", bindAddress)
                .toString();
    }

}
