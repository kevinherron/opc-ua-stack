package com.digitalpetri.opcua.stack.core.channel;

import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.util.PShaUtil;

public class ChannelSecrets {

    private final SecretKeys clientSecretKeys;
    private final SecretKeys serverSecretKeys;

    public ChannelSecrets(SecretKeys clientSecretKeys, SecretKeys serverSecretKeys) {
        this.clientSecretKeys = clientSecretKeys;
        this.serverSecretKeys = serverSecretKeys;
    }

    public SecretKeys getClientSecretKeys() {
        return clientSecretKeys;
    }

    public SecretKeys getServerSecretKeys() {
        return serverSecretKeys;
    }

    public static ChannelSecrets forChannel(SecureChannel channel,
                                            ByteString clientNonce,
                                            ByteString serverNonce) {

        SecurityAlgorithm keyDerivation = channel.getSecurityPolicy().getKeyDerivationAlgorithm();

        int signatureKeySize = channel.getSymmetricSignatureKeySize();
        int encryptionKeySize = channel.getSymmetricEncryptionKeySize();
        int cipherTextBlockSize = channel.getSymmetricCipherTextBlockSize();

        assert (clientNonce != null);
        assert (serverNonce != null);

        byte[] clientSignatureKey = (keyDerivation == SecurityAlgorithm.PSha1) ?
                PShaUtil.createPSha1Key(serverNonce.bytes(), clientNonce.bytes(),  0, signatureKeySize) :
                PShaUtil.createPSha256Key(serverNonce.bytes(), clientNonce.bytes(), 0, signatureKeySize);

        byte[] clientEncryptionKey = (keyDerivation == SecurityAlgorithm.PSha1) ?
                PShaUtil.createPSha1Key(serverNonce.bytes(), clientNonce.bytes(), signatureKeySize, encryptionKeySize) :
                PShaUtil.createPSha256Key(serverNonce.bytes(), clientNonce.bytes(), signatureKeySize, encryptionKeySize);

        byte[] clientInitializationVector = (keyDerivation == SecurityAlgorithm.PSha1) ?
                PShaUtil.createPSha1Key(serverNonce.bytes(), clientNonce.bytes(), signatureKeySize + encryptionKeySize, cipherTextBlockSize) :
                PShaUtil.createPSha256Key(serverNonce.bytes(), clientNonce.bytes(), signatureKeySize + encryptionKeySize, cipherTextBlockSize);

        byte[] serverSignatureKey = (keyDerivation == SecurityAlgorithm.PSha1) ?
                PShaUtil.createPSha1Key(clientNonce.bytes(), serverNonce.bytes(), 0, signatureKeySize) :
                PShaUtil.createPSha256Key(clientNonce.bytes(), serverNonce.bytes(), 0, signatureKeySize);

        byte[] serverEncryptionKey = (keyDerivation == SecurityAlgorithm.PSha1) ?
                PShaUtil.createPSha1Key(clientNonce.bytes(), serverNonce.bytes(), signatureKeySize, encryptionKeySize) :
                PShaUtil.createPSha256Key(clientNonce.bytes(), serverNonce.bytes(), signatureKeySize, encryptionKeySize);

        byte[] serverInitializationVector = (keyDerivation == SecurityAlgorithm.PSha1) ?
                PShaUtil.createPSha1Key(clientNonce.bytes(), serverNonce.bytes(), signatureKeySize + encryptionKeySize, cipherTextBlockSize) :
                PShaUtil.createPSha256Key(clientNonce.bytes(), serverNonce.bytes(), signatureKeySize + encryptionKeySize, cipherTextBlockSize);


        return new ChannelSecrets(
                new SecretKeys(clientSignatureKey, clientEncryptionKey, clientInitializationVector),
                new SecretKeys(serverSignatureKey, serverEncryptionKey, serverInitializationVector)
        );
    }

    public static class SecretKeys {
        private final byte[] signatureKey;
        private final byte[] encryptionKey;
        private final byte[] initializationVector;

        public SecretKeys(byte[] signatureKey, byte[] encryptionKey, byte[] initializationVector) {
            this.signatureKey = signatureKey;
            this.encryptionKey = encryptionKey;
            this.initializationVector = initializationVector;
        }

        public byte[] getSignatureKey() {
            return signatureKey;
        }

        public byte[] getEncryptionKey() {
            return encryptionKey;
        }

        public byte[] getInitializationVector() {
            return initializationVector;
        }
    }

}
