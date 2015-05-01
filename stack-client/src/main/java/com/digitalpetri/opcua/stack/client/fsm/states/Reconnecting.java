package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import com.digitalpetri.opcua.stack.core.Stack;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reconnecting implements ConnectionState {

    private static final int MAX_RECONNECT_DELAY_SECONDS = 16;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile Channel channel;

    private final CompletableFuture<Channel> future;
    private final long delay;

    public Reconnecting(CompletableFuture<Channel> future, long delay) {
        this.future = future;
        this.delay = delay;
    }

    @Override
    public void activate(ConnectionStateEvent fromEvent, ConnectionStateContext context) {
        logger.debug("Reconnecting in {} seconds...", delay);

        Stack.sharedScheduledExecutor().schedule(() -> {
                    logger.debug("Reconnecting...");

                    UaTcpStackClient.bootstrap(context.getClient()).whenComplete((channel, ex) -> {
                        if (channel != null) {
                            logger.debug("Reconnect succeeded: {}", channel);
                            this.channel = channel;
                            context.handleEvent(ConnectionStateEvent.CONNECT_SUCCESS);
                        } else {
                            logger.debug("Reconnect failed: {}", ex.getMessage(), ex);
                            context.handleEvent(ConnectionStateEvent.ERR_CONNECT_FAILED);
                            future.completeExceptionally(ex);
                        }
                    });
                },
                delay, TimeUnit.SECONDS);
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_SUCCESS:
                return new Connected(future, channel);

            case DISCONNECT_REQUESTED:
                return new Disconnecting(future);

            case ERR_CONNECT_FAILED:
                return new Reconnecting(new CompletableFuture<>(), nextDelay());
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return future;
    }

    private long nextDelay() {
        if (delay == 0) {
            return 1;
        } else {
            return Math.min(delay << 1, MAX_RECONNECT_DELAY_SECONDS);
        }
    }

}
