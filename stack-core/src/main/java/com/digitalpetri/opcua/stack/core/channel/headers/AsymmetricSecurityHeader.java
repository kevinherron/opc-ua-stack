package com.digitalpetri.opcua.stack.core.channel.headers;

import javax.annotation.Nonnull;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;

public class AsymmetricSecurityHeader {

    private final String securityPolicyUri;
    private final ByteString senderCertificate;
    private final ByteString receiverThumbprint;

    /**
     * @param securityPolicyUri  the URI of the Security Policy used to secure the Message.
     * @param senderCertificate  the DER-encoded X509v3 Certificate assigned to the sending Application
     *                           Instance. This field shall be null if the Message is not signed.
     * @param receiverThumbprint the thumbprint of the X509v3 Certificate assigned to the receiving Application
     *                           Instance. The thumbprint is the SHA1 digest of the DER encoded form of the
     *                           Certificate. This indicates what public key was used to encrypt the MessageChunk. This
     *                           field shall be null if the Message is not encrypted.
     */
    public AsymmetricSecurityHeader(@Nonnull String securityPolicyUri,
                                    @Nonnull ByteString senderCertificate,
                                    @Nonnull ByteString receiverThumbprint) {

        Preconditions.checkNotNull(securityPolicyUri);
        Preconditions.checkArgument(securityPolicyUri.getBytes().length <= 255,
                "securityPolicyUri length cannot be greater than 255 bytes");

        Preconditions.checkArgument(receiverThumbprint.bytes() == null || receiverThumbprint.length() == 20,
                "receiverThumbprint length must be either null or exactly 20 bytes");

        this.securityPolicyUri = securityPolicyUri;
        this.senderCertificate = senderCertificate;
        this.receiverThumbprint = receiverThumbprint;
    }

    @Nonnull
    public String getSecurityPolicyUri() {
        return securityPolicyUri;
    }

    @Nonnull
    public ByteString getSenderCertificate() {
        return senderCertificate;
    }

    @Nonnull
    public ByteString getReceiverThumbprint() {
        return receiverThumbprint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AsymmetricSecurityHeader that = (AsymmetricSecurityHeader) o;

        return receiverThumbprint.equals(that.receiverThumbprint) &&
                securityPolicyUri.equals(that.securityPolicyUri) &&
                senderCertificate.equals(that.senderCertificate);
    }

    @Override
    public int hashCode() {
        int result = securityPolicyUri.hashCode();
        result = 31 * result + senderCertificate.hashCode();
        result = 31 * result + receiverThumbprint.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("securityPolicyUri", securityPolicyUri)
                .add("senderCertificate", senderCertificate)
                .add("receiverThumbprint", receiverThumbprint)
                .toString();
    }

    public static void encode(AsymmetricSecurityHeader header, ByteBuf buffer) {
        String securityPolicy = header.getSecurityPolicyUri();
        buffer.writeInt(securityPolicy.length());
        buffer.writeBytes(securityPolicy.getBytes());

        ByteString senderCertificate = header.getSenderCertificate();
        if (senderCertificate.isNull()) {
            buffer.writeInt(-1);
        } else {
            buffer.writeInt(senderCertificate.length());
            buffer.writeBytes(senderCertificate.bytes());
        }

        ByteString receiverThumbprint = header.getReceiverThumbprint();
        if (receiverThumbprint.isNull()) {
            buffer.writeInt(-1);
        } else {
            buffer.writeInt(receiverThumbprint.length());
            buffer.writeBytes(receiverThumbprint.bytes());
        }
    }

    public static AsymmetricSecurityHeader decode(ByteBuf buffer) {
        /* SecurityPolicyUri */
        int securityPolicyUriLength = buffer.readInt();
        String securityPolicyUri = new String(buffer.readBytes(securityPolicyUriLength).array());

        /* SenderCertificate */
        int senderCertificateLength = buffer.readInt();
        byte[] senderCertificate = senderCertificateLength >= 0 ?
                buffer.readBytes(senderCertificateLength).array() : null;

        /* ReceiverCertificateThumbprint */
        int thumbprintLength = buffer.readInt();
        byte[] receiverCertificateThumbprint = thumbprintLength >= 0 ?
                buffer.readBytes(thumbprintLength).array() : null;

        return new AsymmetricSecurityHeader(
                securityPolicyUri,
                new ByteString(senderCertificate),
                new ByteString(receiverCertificateThumbprint)
        );
    }

}
