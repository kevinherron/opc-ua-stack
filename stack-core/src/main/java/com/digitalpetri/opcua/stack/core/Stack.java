package com.digitalpetri.opcua.stack.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.HashedWheelTimer;

public final class Stack {

    public static final String UA_TCP_BINARY_TRANSPORT_URI =
            "http://opcfoundation.org/UA-Profile/Transport/uatcp-uasc-uabinary";

    public static final int DEFAULT_PORT = 12685;

    public static final NioEventLoopGroup EVENT_LOOP = new NioEventLoopGroup();

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newWorkStealingPool();

    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static final HashedWheelTimer WHEEL_TIMER = new HashedWheelTimer();

}
