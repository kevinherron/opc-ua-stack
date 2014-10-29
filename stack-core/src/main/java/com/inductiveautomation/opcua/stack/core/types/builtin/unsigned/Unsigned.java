/*
 * Copyright 2014 Inductive Automation
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
package com.inductiveautomation.opcua.stack.core.types.builtin.unsigned;

import java.math.BigInteger;

/**
 * A utility class for static access to unsigned number functionality.
 * <p>
 * It essentially contains factory methods for unsigned number wrappers. In
 * future versions, it will also contain some arithmetic methods, handling
 * regular arithmetic and bitwise operations
 *
 * @author Lukas Eder
 */
public final class Unsigned {

    /**
     * Create an <code>unsigned byte</code>
     *
     * @throws NumberFormatException If <code>value</code> does not contain a
     *             parsable <code>unsigned byte</code>.
     * @see UByte#valueOf(String)
     */
    public static UByte ubyte(String value) throws NumberFormatException {
        return value == null ? null : UByte.valueOf(value);
    }

    /**
     * Create an <code>unsigned byte</code> by masking it with <code>0xFF</code>
     * i.e. <code>(byte) -1</code> becomes <code>(ubyte) 255</code>
     *
     * @see UByte#valueOf(byte)
     */
    public static UByte ubyte(byte value) {
        return UByte.valueOf(value);
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @throws NumberFormatException If <code>value</code> is not in the range
     *             of an <code>unsigned byte</code>
     * @see UByte#valueOf(short)
     */
    public static UByte ubyte(short value) throws NumberFormatException {
        return UByte.valueOf(value);
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @throws NumberFormatException If <code>value</code> is not in the range
     *             of an <code>unsigned byte</code>
     * @see UByte#valueOf(short)
     */
    public static UByte ubyte(int value) throws NumberFormatException {
        return UByte.valueOf(value);
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @throws NumberFormatException If <code>value</code> is not in the range
     *             of an <code>unsigned byte</code>
     * @see UByte#valueOf(short)
     */
    public static UByte ubyte(long value) throws NumberFormatException {
        return UByte.valueOf(value);
    }

    /**
     * Create a UByte[] from {@code values}.
     */
    public static UByte[] ubyte_a(byte... values) {
        UByte[] array = new UByte[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ubyte(values[i]);
        }
        return array;
    }

    /**
     * Create a UByte[] from {@code values}.
     */
    public static UByte[] ubyte_a(short... values) {
        UByte[] array = new UByte[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ubyte(values[i]);
        }
        return array;
    }

    /**
     * Create a UByte[] from {@code values}.
     */
    public static UByte[] ubyte_a(int... values) {
        UByte[] array = new UByte[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ubyte(values[i]);
        }
        return array;
    }

    /**
     * Create a UByte[] from {@code values}.
     */
    public static UByte[] ubyte_a(long... values) {
        UByte[] array = new UByte[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ubyte(values[i]);
        }
        return array;
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @throws NumberFormatException If <code>value</code> does not contain a
     *             parsable <code>unsigned short</code>.
     * @see UShort#valueOf(String)
     */
    public static UShort ushort(String value) throws NumberFormatException {
        return value == null ? null : UShort.valueOf(value);
    }

    /**
     * Create an <code>unsigned short</code> by masking it with
     * <code>0xFFFF</code> i.e. <code>(short) -1</code> becomes
     * <code>(ushort) 65535</code>
     *
     * @see UShort#valueOf(short)
     */
    public static UShort ushort(short value) {
        return UShort.valueOf(value);
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @throws NumberFormatException If <code>value</code> is not in the range
     *             of an <code>unsigned short</code>
     * @see UShort#valueOf(int)
     */
    public static UShort ushort(int value) throws NumberFormatException {
        return UShort.valueOf(value);
    }

    /**
     * Create a UShort[] from {@code values}.
     */
    public static UShort[] ushort_a(short... values) {
        UShort[] array = new UShort[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ushort(values[i]);
        }
        return array;
    }

    /**
     * Create a UShort[] from {@code values}.
     */
    public static UShort[] ushort_a(int... values) {
        UShort[] array = new UShort[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ushort(values[i]);
        }
        return array;
    }

    /**
     * Create an <code>unsigned int</code>
     *
     * @throws NumberFormatException If <code>value</code> does not contain a
     *             parsable <code>unsigned int</code>.
     * @see UInteger#valueOf(String)
     */
    public static UInteger uint(String value) throws NumberFormatException {
        return value == null ? null : UInteger.valueOf(value);
    }

    /**
     * Create an <code>unsigned int</code> by masking it with
     * <code>0xFFFFFFFF</code> i.e. <code>(int) -1</code> becomes
     * <code>(uint) 4294967295</code>
     *
     * @see UInteger#valueOf(int)
     */
    public static UInteger uint(int value) {
        return UInteger.valueOf(value);
    }

    /**
     * Create an <code>unsigned int</code>
     *
     * @throws NumberFormatException If <code>value</code> is not in the range
     *             of an <code>unsigned int</code>
     * @see UInteger#valueOf(long)
     */
    public static UInteger uint(long value) throws NumberFormatException {
        return UInteger.valueOf(value);
    }

    /**
     * Create a UInteger[] from {@code values}.
     */
    public static UInteger[] uint_a(int... values) {
        UInteger[] array = new UInteger[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = uint(values[i]);
        }
        return array;
    }

    /**
     * Create a UInteger[] from {@code values}.
     */
    public static UInteger[] uint_a(long... values) {
        UInteger[] array = new UInteger[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = uint(values[i]);
        }
        return array;
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @throws NumberFormatException If <code>value</code> does not contain a
     *             parsable <code>unsigned long</code>.
     * @see ULong#valueOf(String)
     */
    public static ULong ulong(String value) throws NumberFormatException {
        return value == null ? null : ULong.valueOf(value);
    }

    /**
     * Create an <code>unsigned long</code> by masking it with
     * <code>0xFFFFFFFFFFFFFFFF</code> i.e. <code>(long) -1</code> becomes
     * <code>(uint) 18446744073709551615</code>
     *
     * @see ULong#valueOf(long)
     */
    public static ULong ulong(long value) {
        return ULong.valueOf(value);
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @throws NumberFormatException If <code>value</code> is not in the range
     *             of an <code>unsigned long</code>
     * @see ULong#valueOf(BigInteger)
     */
    public static ULong ulong(BigInteger value) throws NumberFormatException {
        return ULong.valueOf(value);
    }

    /**
     * Create a ULong[] from {@code values}.
     */
    public static ULong[] ulong_a(long... values) {
        ULong[] array = new ULong[values.length];
        for (int i = 0; i < values.length; i++) {
            array[i] = ulong(values[i]);
        }
        return array;
    }

    /**
     * No instances
     */
    private Unsigned() {}
}
