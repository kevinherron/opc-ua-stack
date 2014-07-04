package com.digitalpetri.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;
import java.util.Arrays;

import com.google.common.base.Objects;

public class ByteString {

    public static final ByteString NullValue = new ByteString(null);

    private final byte[] bytes;

    public ByteString(@Nullable byte[] bytes) {
        this.bytes = bytes;
    }

    @Nullable
    public byte[] bytes() {
        return bytes;
    }

    public int length() {
        return bytes != null ? bytes.length : 0;
    }

    public boolean isNull() {
        return bytes == null;
    }

    public static ByteString of(byte[] bs) {
        return new ByteString(bs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteString that = (ByteString) o;

        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        return bytes != null ? Arrays.hashCode(bytes) : 0;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("bytes", Arrays.toString(bytes))
                .toString();
    }

}
