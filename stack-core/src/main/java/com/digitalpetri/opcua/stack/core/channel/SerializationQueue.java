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
