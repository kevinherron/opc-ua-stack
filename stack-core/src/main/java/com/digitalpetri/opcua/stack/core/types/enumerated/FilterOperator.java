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

package com.digitalpetri.opcua.stack.core.types.enumerated;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEnumeration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public enum FilterOperator implements UaEnumeration {

    Equals(0),
    IsNull(1),
    GreaterThan(2),
    LessThan(3),
    GreaterThanOrEqual(4),
    LessThanOrEqual(5),
    Like(6),
    Not(7),
    Between(8),
    InList(9),
    And(10),
    Or(11),
    Cast(12),
    InView(13),
    OfType(14),
    RelatedTo(15),
    BitwiseAnd(16),
    BitwiseOr(17);

    private final int value;

    private FilterOperator(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, FilterOperator> VALUES;

    static {
        Builder<Integer, FilterOperator> builder = ImmutableMap.builder();
        for (FilterOperator e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static FilterOperator from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(FilterOperator filterOperator, UaEncoder encoder) {
        encoder.encodeInt32(null, filterOperator.getValue());
    }

    public static FilterOperator decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(FilterOperator::encode, FilterOperator.class);
        DelegateRegistry.registerDecoder(FilterOperator::decode, FilterOperator.class);
    }

}
