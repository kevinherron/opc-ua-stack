package com.inductiveautomation.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;
import java.util.Arrays;

import com.google.common.base.Objects;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;

public final class ByteString {

    public static final ByteString NULL_VALUE = new ByteString(null);

    private final byte[] bytes;

    public ByteString(@Nullable byte[] bytes) {
        this.bytes = bytes;
    }

    public int length() {
        return bytes != null ? bytes.length : 0;
    }

    public boolean isNull() {
        return bytes == null;
    }

    public boolean isNotNull() {
        return bytes != null;
    }

    @Nullable
    public byte[] bytes() {
        return bytes;
    }

    @Nullable
    public UByte[] uBytes() {
        if (bytes == null) return null;

        UByte[] bs = new UByte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bs[i] = ubyte(bytes[i]);
        }
        return bs;
    }

    public byte byteAt(int index) {
        if (bytes == null) throw new IndexOutOfBoundsException("index=" + index);

        return bytes[index];
    }

    public UByte uByteAt(int index) {
        return ubyte(byteAt(index));
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

    public static ByteString of(byte[] bs) {
        return new ByteString(bs);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("bytes", Arrays.toString(bytes))
                .toString();
    }

}
