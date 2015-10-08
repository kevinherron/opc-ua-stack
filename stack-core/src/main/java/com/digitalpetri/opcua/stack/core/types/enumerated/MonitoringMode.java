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

public enum MonitoringMode implements UaEnumeration {

    Disabled(0),
    Sampling(1),
    Reporting(2);

    private final int value;

    private MonitoringMode(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, MonitoringMode> VALUES;

    static {
        Builder<Integer, MonitoringMode> builder = ImmutableMap.builder();
        for (MonitoringMode e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static MonitoringMode from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(MonitoringMode monitoringMode, UaEncoder encoder) {
        encoder.encodeInt32(null, monitoringMode.getValue());
    }

    public static MonitoringMode decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringMode::encode, MonitoringMode.class);
        DelegateRegistry.registerDecoder(MonitoringMode::decode, MonitoringMode.class);
    }

}
