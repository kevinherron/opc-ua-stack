package com.digitalpetri.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.util.ArrayUtil;
import com.google.common.base.Objects.ToStringHelper;

import static com.google.common.base.Preconditions.checkArgument;

public class Variant {

    public static final Variant NullValue = new Variant(null);

    private final Object value;
    private final Optional<OverloadedType> type;

    /**
     * Create a new Variant with the given value. Use {@link #Variant(Object, OverloadedType)} for creating values
     * of a {@link OverloadedType} (UByte, UInt16, UInt32, UInt64).
     *
     * @param value the value this Variant holds.
     */
    public Variant(@Nullable Object value) {
        this(value, null);
    }

    /**
     * Create a new Variant with a given value and {@link OverloadedType}.
     * <p>
     * If a {@link OverloadedType} is specified you must ensure the value is coercible to the correct type for the
     * {@link OverloadedType} specified:
     * <pre>
     *     UByte    Short.class
     *     UInt16   Integer.class
     *     UInt32   Long.class
     *     UInt64   Long.class
     * </pre>
     *
     * @param value the value this Variant holds.
     * @param type  the {@link OverloadedType} type of {@code value}.
     */
    public Variant(@Nullable Object value, @Nullable OverloadedType type) {
        if (value != null) {
            Class<?> clazz = value.getClass().isArray() ? ArrayUtil.getType(value) : value.getClass();

            checkArgument(clazz.isArray() || !Variant.class.equals(clazz), "Variant cannot contain Variant");
            checkArgument(!DataValue.class.equals(clazz), "Variant cannot contain DataValue");
            checkArgument(!DiagnosticInfo.class.equals(clazz), "Variant cannot contain DiagnosticInfo");
        }

        if (value instanceof UaEnumeration) {
            value = ((UaEnumeration) value).getValue();
        }

        if (value instanceof UaStructure) {
            value = new ExtensionObject((UaStructure) value);
        }

        this.value = value;
        this.type = Optional.ofNullable(type);
    }

    public Object getValue() {
        return value;
    }

    public Optional<OverloadedType> getOverloadedType() {
        return type;
    }

    /**
     * @return {@code true} if this value is a {@link OverloadedType}.
     */
    public boolean isOverloadedType() {
        return type.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variant variant = (Variant) o;

        return Objects.equals(type, variant.type) && Objects.deepEquals(value, variant.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueHash(), type);
    }

    private int valueHash() {
        if (value instanceof Object[]) {
            return Arrays.deepHashCode((Object[]) value);
        } else if (value instanceof boolean[]) {
            return Arrays.hashCode((boolean[]) value);
        } else if (value instanceof byte[]) {
            return Arrays.hashCode((byte[]) value);
        } else if (value instanceof short[]) {
            return Arrays.hashCode((short[]) value);
        } else if (value instanceof int[]) {
            return Arrays.hashCode((int[]) value);
        } else if (value instanceof long[]) {
            return Arrays.hashCode((long[]) value);
        } else if (value instanceof float[]) {
            return Arrays.hashCode((float[]) value);
        } else if (value instanceof double[]) {
            return Arrays.hashCode((double[]) value);
        } else {
            return Objects.hashCode(value);
        }
    }

    @Override
    public String toString() {
        ToStringHelper helper = com.google.common.base.Objects.toStringHelper(this);

        helper.add("value", value);
        type.ifPresent(t -> helper.add("type", t));

        return helper.toString();
    }

}
