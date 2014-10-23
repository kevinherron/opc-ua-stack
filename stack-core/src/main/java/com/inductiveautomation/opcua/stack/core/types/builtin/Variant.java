package com.inductiveautomation.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

import com.google.common.base.Objects.ToStringHelper;
import com.inductiveautomation.opcua.stack.core.serialization.UaEnumeration;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.util.ArrayUtil;

import static com.google.common.base.Preconditions.checkArgument;

public class Variant {

    public static final Variant NullValue = new Variant(null);

    private final Object value;

    /**
     * Create a new Variant with a given value.
     *
     * @param value the value this Variant holds.
     */
    public Variant(@Nullable Object value) {
        if (value != null) {
            Class<?> clazz = value.getClass().isArray() ? ArrayUtil.getType(value) : value.getClass();

            checkArgument(clazz.isArray() || !Variant.class.equals(clazz), "Variant cannot contain Variant");
            checkArgument(!DataValue.class.equals(clazz), "Variant cannot contain DataValue");
            checkArgument(!DiagnosticInfo.class.equals(clazz), "Variant cannot contain DiagnosticInfo");
        }

        if (value instanceof UaEnumeration) {
            value = ((UaEnumeration) value).getValue();
        }

        else if (value instanceof UaStructure) {
            value = new ExtensionObject((UaStructure) value);
        }

        else if (value instanceof UaStructure[]) {
            UaStructure[] values = (UaStructure[]) value;
            ExtensionObject[] xos = new ExtensionObject[values.length];

            for (int i = 0; i < values.length; i++) {
                xos[i] = new ExtensionObject(values[i]);
            }

            value = xos;
        }

        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return !isNull();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variant variant = (Variant) o;

        return Objects.deepEquals(value, variant.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueHash());
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

        return helper.toString();
    }

}
