package com.digitalpetri.opcua.stack.client;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

class ClientChannelManager {

    private static final int MAX_RECONNECT_DELAY_SECONDS = 16;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<State> state = new AtomicReference<>(new Idle());

    private final UaTcpStackClient client;

    ClientChannelManager(UaTcpStackClient client) {
        this.client = client;
    }

    public CompletableFuture<ClientSecureChannel> getChannel() {
        State currentState = state.get();

        logger.trace("getChannel(), currentState={}",
            currentState.getClass().getSimpleName());

        if (currentState instanceof Idle) {
            Connecting nextState = new Connecting();

            if (state.compareAndSet(currentState, nextState)) {
                CompletableFuture<ClientSecureChannel> connected = nextState.connected;

                connect(true, connected);

                return connected.whenCompleteAsync((sc, ex) -> {
                    if (sc != null) {
                        if (state.compareAndSet(nextState, new Connected(connected))) {
                            sc.getChannel().pipeline().addLast(new InactivityHandler());
                        }
                    } else {
                        state.compareAndSet(nextState, new Idle());
                    }
                });
            } else {
                return getChannel();
            }
        } else if (currentState instanceof Connecting) {
            return ((Connecting) currentState).connected;
        } else if (currentState instanceof Connected) {
            return ((Connected) currentState).connected;
        } else if (currentState instanceof Reconnecting) {
            return ((Reconnecting) currentState).reconnected;
        } else if (currentState instanceof Disconnecting) {
            CompletableFuture<ClientSecureChannel> future = new CompletableFuture<>();

            CompletableFuture<Void> disconnectFuture = ((Disconnecting) currentState).disconnectFuture;

            disconnectFuture.whenCompleteAsync(
                (v, ex) -> getChannel().whenCompleteAsync((sc, ex2) -> {
                    if (sc != null) future.complete(sc);
                    else future.completeExceptionally(ex2);
                }),
                client.getExecutorService()
            );

            return future;
        } else {
            throw new IllegalStateException(currentState.getClass().getSimpleName());
        }
    }

    public CompletableFuture<Void> disconnect() {
        State currentState = state.get();

        logger.trace("disconnect(), currentState={}",
            currentState.getClass().getSimpleName());

        if (currentState instanceof Idle) {
            CompletableFuture<Void> f = new CompletableFuture<>();
            f.complete(null);
            return f;
        } else if (currentState instanceof Connected) {
            Disconnecting disconnecting = new Disconnecting();

            if (state.compareAndSet(currentState, disconnecting)) {
                ((Connected) currentState).connected.whenCompleteAsync((sc, ex) -> {
                    if (sc != null) {
                        disconnect(sc, disconnecting.disconnectFuture);
                    } else {
                        disconnecting.disconnectFuture.complete(null);
                    }

                    disconnecting.disconnectFuture.whenComplete(
                        (v, ex2) -> {
                            if (state.compareAndSet(disconnecting, new Idle())) {
                                logger.debug("disconnect complete, state set to Idle");
                            }
                        });
                });

                return disconnecting.disconnectFuture;
            } else {
                return disconnect();
            }
        } else if (currentState instanceof Connecting) {
            Disconnecting disconnecting = new Disconnecting();

            if (state.compareAndSet(currentState, disconnecting)) {
                ((Connecting) currentState).connected.whenCompleteAsync((sc, ex) -> {
                    if (sc != null) {
                        disconnect(sc, disconnecting.disconnectFuture);
                    } else {
                        disconnecting.disconnectFuture.complete(null);
                    }

                    disconnecting.disconnectFuture.whenComplete(
                        (v, ex2) -> {
                            if (state.compareAndSet(disconnecting, new Idle())) {
                                logger.debug("disconnect complete, state set to Idle");
                            }
                        });
                });

                return disconnecting.disconnectFuture;
            } else {
                return disconnect();
            }
        } else if (currentState instanceof Reconnecting) {
            Disconnecting disconnecting = new Disconnecting();

            if (state.compareAndSet(currentState, disconnecting)) {
                ((Reconnecting) currentState).reconnected.whenCompleteAsync((sc, ex) -> {
                    if (sc != null) {
                        disconnect(sc, disconnecting.disconnectFuture);
                    } else {
                        disconnecting.disconnectFuture.complete(null);
                    }

                    disconnecting.disconnectFuture.whenComplete(
                        (v, ex2) -> {
                            if (state.compareAndSet(disconnecting, new Idle())) {
                                logger.debug("disconnect complete, state set to Idle");
                            }
                        });
                });

                return disconnecting.disconnectFuture;
            } else {
                return disconnect();
            }
        } else if (currentState instanceof Disconnecting) {
            return ((Disconnecting) currentState).disconnectFuture;
        } else {
            throw new IllegalStateException(currentState.getClass().getSimpleName());
        }
    }

    private void connect(boolean initialAttempt, CompletableFuture<ClientSecureChannel> future) {
        UaTcpStackClient.bootstrap(client, Optional.empty()).whenCompleteAsync((sc, ex) -> {
            if (sc != null) {
                logger.debug(
                    "Channel bootstrap succeeded: localAddress={}, remoteAddress={}",
                    sc.getChannel().localAddress(), sc.getChannel().remoteAddress());

                future.complete(sc);
            } else {
                logger.debug("Channel bootstrap failed: {}", ex.getMessage(), ex);

                StatusCode statusCode = UaException.extract(ex)
                    .map(UaException::getStatusCode)
                    .orElse(StatusCode.BAD);

                boolean secureChannelError =
                    statusCode.getValue() == StatusCodes.Bad_SecureChannelIdInvalid ||
                        statusCode.getValue() == StatusCodes.Bad_SecurityChecksFailed ||
                        statusCode.getValue() == StatusCodes.Bad_TcpSecureChannelUnknown;

                if (initialAttempt && secureChannelError) {
                    // Try again if bootstrapping failed because we couldn't re-open the previous channel.
                    logger.debug("Previous channel unusable, retrying...");

                    connect(false, future);
                } else {
                    future.completeExceptionally(ex);
                }
            }
        });
    }

    private void reconnect(Reconnecting reconnectState, long delaySeconds) {
        logger.debug("Scheduling reconnect for +{} seconds...", delaySeconds);

        Stack.sharedScheduledExecutor().schedule(() -> {
            logger.debug("{} seconds elapsed; reconnecting...", delaySeconds);

            CompletableFuture<ClientSecureChannel> reconnected = reconnectState.reconnected;

            connect(true, reconnected);

            reconnected.whenCompleteAsync((sc, ex) -> {
                if (sc != null) {
                    logger.debug("Reconnect succeeded, channelId={}", sc.getChannelId());

                    if (state.compareAndSet(reconnectState, new Connected(reconnected))) {
                        sc.getChannel().pipeline().addLast(new InactivityHandler());
                    }
                } else {
                    logger.debug("Reconnect failed: {}", ex.getMessage(), ex);

                    Reconnecting nextState = new Reconnecting();
                    if (state.compareAndSet(reconnectState, nextState)) {
                        reconnect(nextState, nextDelay(delaySeconds));
                    }
                }
            });
        }, delaySeconds, TimeUnit.SECONDS);
    }

    private void disconnect(ClientSecureChannel secureChannel, CompletableFuture<Void> disconnected) {
        RequestHeader requestHeader = new RequestHeader(
            NodeId.NULL_VALUE, DateTime.now(), uint(0), uint(0), null, uint(0), null);

        secureChannel.getChannel().pipeline().addFirst(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                logger.debug("channelInactive(), disconnect complete");
                disconnected.complete(null);
            }
        });

        logger.debug("Sending CloseSecureChannelRequest...");
        CloseSecureChannelRequest request = new CloseSecureChannelRequest(requestHeader);
        secureChannel.getChannel().pipeline().fireUserEventTriggered(request);
    }

    private static long nextDelay(long delaySeconds) {
        if (delaySeconds == 0) {
            return 1;
        } else {
            return Math.min(delaySeconds << 1, MAX_RECONNECT_DELAY_SECONDS);
        }
    }

    private class InactivityHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            State currentState = state.get();

            if (currentState instanceof Disconnecting) {
                Idle nextState = new Idle();

                state.compareAndSet(currentState, nextState);
                ((Disconnecting) currentState).disconnectFuture.complete(null);
            } else {
                Reconnecting nextState = new Reconnecting();

                if (state.compareAndSet(currentState, nextState)) {
                    if (currentState instanceof Connected &&
                        !client.getConfig().isSecureChannelReauthenticationEnabled()) {

                        ((Connected) currentState).connected
                            .thenAccept(sc -> sc.setChannelId(0));
                    }

                    reconnect(nextState, 0);
                }
            }

            super.channelInactive(ctx);
        }
    }

    private interface State {
    }

    private class Idle implements State {

    }

    private class Connecting implements State {
        final CompletableFuture<ClientSecureChannel> connected = new CompletableFuture<>();
    }

    private class Connected implements State {
        final CompletableFuture<ClientSecureChannel> connected;

        Connected(CompletableFuture<ClientSecureChannel> connected) {
            this.connected = connected;
        }
    }

    private class Reconnecting implements State {
        final CompletableFuture<ClientSecureChannel> reconnected = new CompletableFuture<>();
    }

    private class Disconnecting implements State {
        final CompletableFuture<Void> disconnectFuture = new CompletableFuture<>();
    }

}
