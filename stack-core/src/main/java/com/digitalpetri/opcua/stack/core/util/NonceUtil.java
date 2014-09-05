package com.digitalpetri.opcua.stack.core.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;

public class NonceUtil {

    private static final Random random = new Random();

    private static volatile SecureRandom secureRandom;

    static {
        /*
         * The first call to a SecureRandom causes it to seed, which is potentially blocking, so don't make the
         * SecureRandom instance available until after its seeded and generated output already.
         */
        new Thread(() -> {
            SecureRandom sr;
            try {
                sr = SecureRandom.getInstanceStrong();
            } catch (NoSuchAlgorithmException e) {
                sr = new SecureRandom();
            }
            sr.nextBytes(new byte[32]);
            secureRandom = sr;
        }).start();
    }

    /**
     * @param length the length of the nonce to generate.
     * @return a nonce of the given length.
     */
    public static ByteString generateNonce(int length) {
        if (length == 0) return ByteString.NullValue;

        byte[] bs = new byte[length];

        if (secureRandom == null) {
            random.nextBytes(bs);
        } else {
            secureRandom.nextBytes(bs);
        }

        return new ByteString(bs);
    }

    /**
     * Generate a nonce for the given {@link SecurityAlgorithm}. The length is determined by the algorithm.
     * @param algorithm the algorithm to use when determined the nonce length.
     * @return a nonce of the appropriate length for the given algorithm.
     */
    public static ByteString generateNonce(SecurityAlgorithm algorithm) {
        return generateNonce(getNonceLength(algorithm));
    }

    /**
     * Get the nonce length for use with {@code algorithm}.
     *
     * @param algorithm a symmetric encryption algorithm.
     * @return the nonce length.
     */
    public static int getNonceLength(SecurityAlgorithm algorithm) {
        switch (algorithm) {
            case Aes128:
                return 16;
            case Aes256:
                return 32;
            default:
                return 0;
        }
    }

}
