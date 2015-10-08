/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;

public class NonceUtil {

    private static volatile boolean secureRandomEnabled = false;

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
        }, "SecureRandomGetInstanceStrong").start();
    }

    public static void enableSecureRandom() {
        secureRandomEnabled = true;
    }

    public static void disableSecureRandom() {
        secureRandomEnabled = false;
    }

    public static boolean isSecureRandomEnabled() {
        return secureRandomEnabled;
    }

    /**
     * @param length the length of the nonce to generate.
     * @return a nonce of the given length.
     */
    public static ByteString generateNonce(int length) {
        if (length == 0) return ByteString.NULL_VALUE;

        byte[] bs = new byte[length];

        if (secureRandom != null && secureRandomEnabled) {
            secureRandom.nextBytes(bs);
        } else {
            ThreadLocalRandom.current().nextBytes(bs);
        }

        return new ByteString(bs);
    }

    /**
     * Generate a nonce for the given {@link SecurityAlgorithm}. The length is determined by the algorithm.
     *
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
