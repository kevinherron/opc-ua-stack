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

package com.digitalpetri.opcua.stack.core.channel.headers;

import com.digitalpetri.opcua.stack.core.util.annotations.UInt32Primitive;
import com.google.common.base.MoreObjects;
import io.netty.buffer.ByteBuf;

public class SymmetricSecurityHeader {

    public static final int SYMMETRIC_SECURITY_HEADER_SIZE = 4;

    @UInt32Primitive
    private final long tokenId;

    /**
     * @param tokenId A unique identifier for the SecureChannel SecurityToken used to secure the Message.
     */
    public SymmetricSecurityHeader(long tokenId) {
        this.tokenId = tokenId;
    }

    public long getTokenId() {
        return tokenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SymmetricSecurityHeader that = (SymmetricSecurityHeader) o;

        return tokenId == that.tokenId;
    }

    @Override
    public int hashCode() {
        return (int) (tokenId ^ (tokenId >>> 32));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tokenId", tokenId)
                .toString();
    }

    public static ByteBuf encode(SymmetricSecurityHeader header, ByteBuf buffer) {
        buffer.writeInt((int) header.getTokenId());

        return buffer;
    }

    public static SymmetricSecurityHeader decode(ByteBuf buffer) {
        return new SymmetricSecurityHeader(buffer.readUnsignedInt());
    }

}
