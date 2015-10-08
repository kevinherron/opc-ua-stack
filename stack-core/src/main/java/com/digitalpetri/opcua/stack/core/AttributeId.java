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

package com.digitalpetri.opcua.stack.core;

import java.util.EnumSet;
import java.util.Optional;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public enum AttributeId {

    NodeId(1),
    NodeClass(2),
    BrowseName(3),
    DisplayName(4),
    Description(5),
    WriteMask(6),
    UserWriteMask(7),
    IsAbstract(8),
    Symmetric(9),
    InverseName(10),
    ContainsNoLoops(11),
    EventNotifier(12),
    Value(13),
    DataType(14),
    ValueRank(15),
    ArrayDimensions(16),
    AccessLevel(17),
    UserAccessLevel(18),
    MinimumSamplingInterval(19),
    Historizing(20),
    Executable(21),
    UserExecutable(22);

    public static final ImmutableSet<AttributeId> BASE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            EnumSet.of(
                    NodeId, NodeClass, BrowseName, DisplayName, Description, WriteMask, UserWriteMask)
    );

    public static final ImmutableSet<AttributeId> DATA_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(IsAbstract))
    );

    public static final ImmutableSet<AttributeId> METHOD_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(Executable, UserExecutable))
    );

    public static final ImmutableSet<AttributeId> OBJECT_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(EventNotifier))
    );

    public static final ImmutableSet<AttributeId> OBJECT_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(IsAbstract))
    );

    public static final ImmutableSet<AttributeId> REFERENCE_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(IsAbstract, Symmetric, InverseName))
    );

    public static final ImmutableSet<AttributeId> VARIABLE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(Value, DataType, ValueRank, ArrayDimensions,
                            AccessLevel, UserAccessLevel, MinimumSamplingInterval, Historizing))
    );

    public static final ImmutableSet<AttributeId> VARIABLE_TYPE_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(Value, DataType, ValueRank, ArrayDimensions, IsAbstract))
    );

    public static final ImmutableSet<AttributeId> VIEW_NODE_ATTRIBUTES = ImmutableSet.copyOf(
            Sets.union(
                    BASE_NODE_ATTRIBUTES,
                    EnumSet.of(ContainsNoLoops, EventNotifier))
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
