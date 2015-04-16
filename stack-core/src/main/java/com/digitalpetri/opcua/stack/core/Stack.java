package com.digitalpetri.opcua.stack.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.HashedWheelTimer;

public final class Stack {

    public static final String UA_TCP_BINARY_TRANSPORT_URI =
            "http://opcfoundation.org/UA-Profile/Transport/uatcp-uasc-uabinary";

    public static final int DEFAULT_PORT = 12685;

    /**
     * @return a shared {@link NioEventLoopGroup}.
     */
    public static NioEventLoopGroup sharedEventLoop() {
        return EventLoopHolder.EVENT_LOOP;
    }

    /**
     * @return a shared {@link ExecutorService}.
     */
    public static ExecutorService sharedExecutor() {
        return ExecutorHolder.EXECUTOR_SERVICE;
    }

    /**
     * @return a shared {@link ScheduledExecutorService}.
     */
    public static ScheduledExecutorService sharedScheduledExecutor() {
        return ScheduledExecutorHolder.SCHEDULED_EXECUTOR_SERVICE;
    }

    /**
     * @return a shared {@link HashedWheelTimer}.
     */
    public static HashedWheelTimer sharedWheelTimer() {
        return WheelTimerHolder.WHEEL_TIMER;
    }

    public static void releaseSharedResources() {
        sharedEventLoop().shutdownGracefully();
        sharedExecutor().shutdown();
        sharedWheelTimer().stop();
    }

    private static class EventLoopHolder {
        private static final NioEventLoopGroup EVENT_LOOP = new NioEventLoopGroup(0, new ThreadFactory() {
            private final AtomicLong threadNumber = new AtomicLong(0L);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "netty-event-loop-" + threadNumber.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    private static class ExecutorHolder {
        private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2,
                new ThreadFactory() {
                    private final AtomicLong threadNumber = new AtomicLong(0L);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r, "ua-shared-pool-" + threadNumber.getAndIncrement());
                        thread.setDaemon(true);
                        return thread;
                    }
                });
    }

    private static class ScheduledExecutorHolder {
        private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
                Executors.newSingleThreadScheduledExecutor(
                        new ThreadFactory() {
                            private final AtomicLong threadNumber = new AtomicLong(0L);

                            @Override
                            public Thread newThread(Runnable r) {
                                Thread thread = new Thread(r, "ua-shared-scheduled-executor-" + threadNumber.getAndIncrement());
                                thread.setDaemon(true);
                                return thread;
                            }
                        });
    }

    private static class WheelTimerHolder {
        private static final HashedWheelTimer WHEEL_TIMER = new HashedWheelTimer(r -> {
            Thread thread = new Thread(r, "netty-wheel-timer");
            thread.setDaemon(true);
            return thread;
        });
    }

}
