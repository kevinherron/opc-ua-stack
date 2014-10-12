package com.inductiveautomation.opcua.stack.core.channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiConsumer;

import com.inductiveautomation.opcua.stack.core.serialization.binary.BinaryDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.binary.BinaryEncoder;
import com.inductiveautomation.opcua.stack.core.util.ExecutionQueue;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.LoggerFactory;

public class SerializationQueue {

    private static final ExecutorService SerializationExecutor;

    static {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("ua-serialization-pool-%d")
                .setUncaughtExceptionHandler((t, e) ->
                        LoggerFactory.getLogger(SerializationQueue.class)
                                .error("Uncaught exception in SerializationQueue.", e))
                .build();

        SerializationExecutor = Executors.newCachedThreadPool(threadFactory);
    }

    private final BinaryEncoder binaryEncoder;
    private final BinaryDecoder binaryDecoder;

    private final ChunkEncoder chunkEncoder;
    private final ChunkDecoder chunkDecoder;

    private final ExecutionQueue<Runnable> encodingQueue;
    private final ExecutionQueue<Runnable> decodingQueue;

    private final ChannelParameters parameters;

    public SerializationQueue(ChannelParameters parameters, int maxArrayLength, int maxStringLength) {
        this.parameters = parameters;

        binaryEncoder = new BinaryEncoder(maxArrayLength, maxStringLength);
        binaryDecoder = new BinaryDecoder(maxArrayLength, maxStringLength);

        chunkEncoder = new ChunkEncoder(parameters);
        chunkDecoder = new ChunkDecoder(parameters);

        encodingQueue = new ExecutionQueue<>(ExecutionQueue.RUNNABLE_EXECUTOR, SerializationExecutor);
        decodingQueue = new ExecutionQueue<>(ExecutionQueue.RUNNABLE_EXECUTOR, SerializationExecutor);
    }

    public void encode(BiConsumer<BinaryEncoder, ChunkEncoder> consumer) {
        encodingQueue.submit(() -> consumer.accept(binaryEncoder, chunkEncoder));
    }

    public void decode(BiConsumer<BinaryDecoder, ChunkDecoder> consumer) {
        decodingQueue.submit(() -> consumer.accept(binaryDecoder, chunkDecoder));
    }

    public ChannelParameters getParameters() {
        return parameters;
    }

}
