/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.digitalpetri.opcua.stack.core;

import java.util.EnumSet;
import java.util.Optional;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public enum AttributeId {

    NODE_ID(1),
    NODE_CLASS(2),
    BROWSE_NAME(3),
    DISPLAY_NAME(4),
    DESCRIPTION(5),
    WRITE_MASK(6),
    USER_WRITE_MASK(7),
    IS_ABSTRACT(8),
    SYMMETRIC(9),
    INVERSE_NAME(10),
    CONTAINS_NO_LOOPS(11),
    EVENT_NOTIFIER(12),
    VALUE(13),
    DATA_TYPE(14),
    VALUE_RANK(15),
    ARRAY_DIMENSIONS(16),
    ACCESS_LEVEL(17),
    USER_ACCESS_LEVEL(18),
    MINIMUM_SAMPLING_INTERVAL(19),
    HISTORIZING(20),
    EXECUTABLE(21),
    USER_EXECUTABLE(22);

    public static final ImmutableSet<AttributeId> BASE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            EnumSet.of(
                    NODE_ID, NODE_CLASS, BROWSE_NAME, DISPLAY_NAME, DESCRIPTION, WRITE_MASK, USER_WRITE_MASK)
    );

    public static final ImmutableSet<AttributeId> DATA_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(IS_ABSTRACT))
    );

    public static final ImmutableSet<AttributeId> METHOD_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(EXECUTABLE, USER_EXECUTABLE))
    );

    public static final ImmutableSet<AttributeId> OBJECT_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(EVENT_NOTIFIER))
    );

    public static final ImmutableSet<AttributeId> OBJECT_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(IS_ABSTRACT))
    );

    public static final ImmutableSet<AttributeId> REFERENCE_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(IS_ABSTRACT, SYMMETRIC, INVERSE_NAME))
    );

    public static final ImmutableSet<AttributeId> VARIABLE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(VALUE, DATA_TYPE, VALUE_RANK, ARRAY_DIMENSIONS,
                            ACCESS_LEVEL, USER_ACCESS_LEVEL, MINIMUM_SAMPLING_INTERVAL, HISTORIZING))
    );

    public static final ImmutableSet<AttributeId> VARIABLE_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(VALUE, DATA_TYPE, VALUE_RANK, ARRAY_DIMENSIONS, IS_ABSTRACT))
    );

    public static final ImmutableSet<AttributeId> VIEW_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(CONTAINS_NO_LOOPS, EVENT_NOTIFIER))
    );

    private final int id;

    AttributeId(int id) {
        this.id = id;
    }

    public final int id() {
        return id;
    }

    public final UInteger uid() {
        return uint(id);
    }

    public static Optional<AttributeId> from(UInteger attributeId) {
        return from(attributeId.intValue());
    }

    public static Optional<AttributeId> from(int attributeId) {
        if (attributeId > 0 && attributeId <= values().length) {
            return Optional.of(values()[attributeId - 1]);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @param attributeId the id to test for validity.
     * @return {@code true} if {@code attributeId} is valid.
     */
    public static boolean isValid(UInteger attributeId) {
        return from(attributeId).isPresent();
    }

    /**
     * @param attributeId the id to test for validity.
     * @return {@code true} if {@code attributeId} is valid.
     */
    public static boolean isValid(int attributeId) {
        return from(attributeId).isPresent();
    }

}
