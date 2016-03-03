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

package com.digitalpetri.opcua.stack.core.types.builtin;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.util.ArrayUtil;
import com.digitalpetri.opcua.stack.core.util.TypeUtil;
import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

import static com.google.common.base.Preconditions.checkArgument;

public final class Variant {

    public static final Variant NULL_VALUE = new Variant(null);

    private final Object value;

    /**
     * Create a new Variant with a given value.
     *
     * @param value the value this Variant holds.
     */
    public Variant(@Nullable Object value) {
        if (value != null) {
            boolean clazzIsArray = value.getClass().isArray();

            Class<?> componentClazz = clazzIsArray ?
                    ArrayUtil.getType(value) : value.getClass();

            checkArgument(clazzIsArray || !Variant.class.equals(componentClazz), "Variant cannot contain Variant");
            checkArgument(!DataValue.class.equals(componentClazz), "Variant cannot contain DataValue");
            checkArgument(!DiagnosticInfo.class.equals(componentClazz), "Variant cannot contain DiagnosticInfo");
        }

        this.value = value;
    }

    public Optional<NodeId> getDataType() {
        if (value == null) return Optional.empty();

        if (value instanceof UaStructure) {
            return Optional.of(((UaStructure) value).getTypeId());
        } else if (value instanceof UaEnumeration) {
            return Optional.of(Identifiers.Int32);
        } else {
            Class<?> clazz = value.getClass().isArray() ?
                    ArrayUtil.getType(value) : value.getClass();

            int typeId = TypeUtil.getBuiltinTypeId(clazz);

            return typeId == -1 ?
                    Optional.empty() : Optional.of(new NodeId(0, typeId));
        }
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
        ToStringHelper helper = MoreObjects.toStringHelper(this);

        helper.add("value", value);

        return helper.toString();
    }

}
