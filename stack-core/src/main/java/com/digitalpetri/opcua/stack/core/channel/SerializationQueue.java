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

import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;

import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryDecoder;
import com.digitalpetri.opcua.stack.core.serialization.binary.BinaryEncoder;
import com.digitalpetri.opcua.stack.core.util.ExecutionQueue;

public class SerializationQueue {

    private final BinaryEncoder binaryEncoder;
    private final BinaryDecoder binaryDecoder;

    private final ChunkEncoder chunkEncoder;
    private final ChunkDecoder chunkDecoder;

    private final ExecutionQueue encodingQueue;
    private final ExecutionQueue decodingQueue;

    private final ChannelParameters parameters;

    public SerializationQueue(ExecutorService executor,
                              ChannelParameters parameters,
                              int maxArrayLength,
                              int maxStringLength) {

        this.parameters = parameters;

        binaryEncoder = new BinaryEncoder(maxArrayLength, maxStringLength);
        binaryDecoder = new BinaryDecoder(maxArrayLength, maxStringLength);

        chunkEncoder = new ChunkEncoder(parameters);
        chunkDecoder = new ChunkDecoder(parameters);

        encodingQueue = new ExecutionQueue(executor);
        decodingQueue = new ExecutionQueue(executor);
    }

    public void encode(BiConsumer<BinaryEncoder, ChunkEncoder> consumer) {
        encodingQueue.submit(() -> consumer.accept(binaryEncoder, chunkEncoder));
    }

    public void decode(BiConsumer<BinaryDecoder, ChunkDecoder> consumer) {
        decodingQueue.submit(() -> consumer.accept(binaryDecoder, chunkDecoder));
    }

    public void pause() {
        encodingQueue.pause();
        decodingQueue.pause();
    }

    public ChannelParameters getParameters() {
        return parameters;
    }

}
