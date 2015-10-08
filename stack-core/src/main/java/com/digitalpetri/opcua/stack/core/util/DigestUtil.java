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
    public static byte[] sha1(byte[] input) {
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
