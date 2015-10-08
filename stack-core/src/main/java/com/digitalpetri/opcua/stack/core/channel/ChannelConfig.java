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

package com.digitalpetri.opcua.stack.core.channel;

import com.google.common.base.Preconditions;

public class ChannelConfig {

    /** A {@link ChannelConfig} that uses the default settings. */
    public static final ChannelConfig DEFAULT = new ChannelConfig();

    /**
     * The default maximum size of a single chunk.
     */
    public static final int DEFAULT_MAX_CHUNK_SIZE = 65536;

    /**
     * The default maximum number of chunks that a message can break down into.
     */
    public static final int DEFAULT_MAX_CHUNK_COUNT = 32;

    /**
     * The default maximum size of a message after all chunks have been assembled. Given the default chunk size and
     * count this works out to 2mb.
     */
    public static final int DEFAULT_MAX_MESSAGE_SIZE = DEFAULT_MAX_CHUNK_COUNT * DEFAULT_MAX_CHUNK_SIZE;

    public static final int DEFAULT_MAX_ARRAY_LENGTH = 65536;
    public static final int DEFAULT_MAX_STRING_LENGTH = 65536;

    private final int maxChunkSize;
    private final int maxChunkCount;
    private final int maxMessageSize;
    private final int maxArrayLength;
    private final int maxStringLength;

    /**
     * Create a {@link ChannelConfig} using the default parameters.
     *
     * @see {@link ChannelConfig#DEFAULT_MAX_CHUNK_SIZE}
     * @see {@link ChannelConfig#DEFAULT_MAX_CHUNK_COUNT}
     * @see {@link ChannelConfig#DEFAULT_MAX_MESSAGE_SIZE}
     * @see {@link ChannelConfig#DEFAULT_MAX_ARRAY_LENGTH}
     * @see {@link ChannelConfig#DEFAULT_MAX_STRING_LENGTH}
     */
    public ChannelConfig() {
        this(DEFAULT_MAX_CHUNK_SIZE,
                DEFAULT_MAX_CHUNK_COUNT,
                DEFAULT_MAX_MESSAGE_SIZE,
                DEFAULT_MAX_ARRAY_LENGTH,
                DEFAULT_MAX_STRING_LENGTH);
    }

    /**
     * @param maxChunkSize   The maximum size of a single chunk. Must be greater than 8192.
     * @param maxChunkCount  The maximum number of chunks that a message can break down into.
     * @param maxMessageSize The maximum size of a message after all chunks have been assembled.
     */
    public ChannelConfig(int maxChunkSize,
                         int maxChunkCount,
                         int maxMessageSize,
                         int maxArrayLength,
                         int maxStringLength) {
        Preconditions.checkArgument(maxChunkSize > 8192,
                "maxChunkSize must be greater than 8192");

        this.maxChunkSize = maxChunkSize;
        this.maxChunkCount = maxChunkCount;
        this.maxMessageSize = maxMessageSize;
        this.maxArrayLength = maxArrayLength;
        this.maxStringLength = maxStringLength;
    }

    public int getMaxChunkSize() {
        return maxChunkSize;
    }

    public int getMaxChunkCount() {
        return maxChunkCount;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public int getMaxArrayLength() {
        return maxArrayLength;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

}
