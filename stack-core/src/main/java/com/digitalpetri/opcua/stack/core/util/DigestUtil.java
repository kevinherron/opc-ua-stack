package com.digitalpetri.opcua.stack.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;

public class DigestUtil {

    private static final ThreadLocal<MessageDigest> sha1Digest = new ThreadLocal<>();

    /**
     * Compute the SHA1 digest for a given input.
     *
     * @param input the input to compute the digest for.
     * @return the SHA1 digest of {@code input}.
     */
    public static byte[] sha1(byte[] input) throws UaRuntimeException {
        MessageDigest messageDigest = sha1Digest.get();

        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance("SHA1");
                sha1Digest.set(messageDigest);
            } catch (NoSuchAlgorithmException e) {
                throw new UaRuntimeException(StatusCodes.Bad_InternalError, e);
            }
        }

        return messageDigest.digest(input);
    }

}
