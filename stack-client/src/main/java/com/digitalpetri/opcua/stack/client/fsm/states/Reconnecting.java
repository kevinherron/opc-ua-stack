package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionEvent;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateFsm;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reconnecting implements ConnectionState {

    private static final int MAX_RECONNECT_DELAY_SECONDS = 16;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompletableFuture<ClientSecureChannel> channelFuture = new CompletableFuture<>();

    private volatile ScheduledFuture<?> scheduledFuture;

    private volatile ClientSecureChannel secureChannel;

    private final long delaySeconds;
    private volatile long secureChannelId;

    public Reconnecting(long delaySeconds, long secureChannelId) {
        this.delaySeconds = delaySeconds;
        this.secureChannelId = secureChannelId;
    }

    @Override
    public CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Runnable connect = () -> connect(fsm, true, new CompletableFuture<>()).whenComplete((sc, ex) -> {
            if (sc != null) {
                secureChannel = sc;
                fsm.handleEvent(ConnectionEvent.ReconnectSucceeded);
            } else {
                channelFuture.completeExceptionally(ex);
                fsm.handleEvent(ConnectionEvent.ReconnectFailed);
            }

            future.complete(null);
        });

        if (scheduledFuture == null || (scheduledFuture != null && scheduledFuture.cancel(false))) {
            logger.debug("Reactivating in {} seconds...", delaySeconds);

            scheduledFuture = Stack.sharedScheduledExecutor().schedule(connect, delaySeconds, TimeUnit.SECONDS);
        }

        return future;
    }

    private CompletableFuture<ClientSecureChannel> connect(ConnectionStateFsm fsm,
                                                           boolean initialAttempt,
                                                           CompletableFuture<ClientSecureChannel> future) {

        logger.debug("Reconnecting...");

        UaTcpStackClient.bootstrap(fsm.getClient(), secureChannelId).whenComplete((sc, ex) -> {
            if (sc != null) {
                logger.debug("Channel bootstrap succeeded: localAddress={}, remoteAddress={}",
                        sc.getChannel().localAddress(), sc.getChannel().remoteAddress());

                future.complete(sc);
            } else {
                logger.debug("Channel bootstrap failed: {}", ex.getMessage(), ex);

                StatusCode statusCode = UaException.extract(ex)
                        .map(UaException::getStatusCode)
                        .orElse(StatusCode.BAD);

                boolean secureChannelError =
                        statusCode.getValue() == StatusCodes.Bad_TcpSecureChannelUnknown ||
                                statusCode.getValue() == StatusCodes.Bad_SecureChannelClosed ||
                                statusCode.getValue() == StatusCodes.Bad_SecureChannelIdInvalid ||
                                statusCode.getValue() == StatusCodes.Bad_ConnectionClosed;

                if (initialAttempt && secureChannelError) {
                    // Try again if bootstrapping failed because we couldn't re-open the previous channel.
                    logger.debug("Previous channel unusable, retrying...");

                    secureChannelId = 0L;

                    connect(fsm, false, future);
                } else {
                    future.completeExceptionally(ex);
                }
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm) {
        return CF_VOID_COMPLETED;
    }

    @Override
    public ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm) {
        switch (event) {
            case ReconnectFailed:
                return new Reconnecting(nextDelay(), secureChannelId);

            case ReconnectSucceeded:
                return new Connected(secureChannel, channelFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<ClientSecureChannel> getSecureChannel() {
        return channelFuture;
    }

    private long nextDelay() {
        if (delaySeconds == 0) {
            return 1;
        } else {
            return Math.min(delaySeconds << 1, MAX_RECONNECT_DELAY_SECONDS);
        }
    }

    @Override
    public String toString() {
        return "Reconnecting{" +
                "delaySeconds=" + delaySeconds +
                ", secureChannelId=" + secureChannelId +
                '}';
    }

}
