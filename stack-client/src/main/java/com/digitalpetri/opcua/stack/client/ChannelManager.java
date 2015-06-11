package com.digitalpetri.opcua.stack.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class ChannelManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<State> state = new AtomicReference<>(new Idle());

    private final UaTcpStackClient client;

    public ChannelManager(UaTcpStackClient client) {
        this.client = client;
    }

    public CompletableFuture<Channel> getChannel() {
        State currentState = state.get();

        if (currentState instanceof Idle) {
            Connecting nextState = new Connecting();

            if (state.compareAndSet(currentState, nextState)) {
                CompletableFuture<Channel> future = nextState.future;

                future.whenComplete((ch, ex) -> {
                    if (ch != null) state.set(new Connected(future));
                    else state.set(new Idle());
                });

                return connect(future, true);
            } else {
                return getChannel();
            }
        } else if (currentState instanceof Connecting) {
            return ((Connecting) currentState).future;
        } else if (currentState instanceof Connected) {
            return ((Connected) currentState).future;
        } else {
            throw new IllegalStateException(currentState.getClass().getSimpleName());
        }
    }

    private CompletableFuture<Channel> connect(CompletableFuture<Channel> future, boolean initialAttempt) {
        CompletableFuture<Channel> bootstrap = UaTcpStackClient.bootstrap(client);

        bootstrap.whenComplete((ch, ex) -> {
            if (ch != null) {
                logger.debug("Channel bootstrap succeeded: localAddress={}, remoteAddress={}",
                        ch.localAddress(), ch.remoteAddress());

                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        state.set(new Idle());

                        super.channelInactive(ctx);
                    }
                });

                future.complete(ch);
            } else {
                logger.debug("Channel bootstrap failed: {}", ex.getMessage(), ex);

                StatusCode statusCode = UaException.extract(ex)
                        .map(UaException::getStatusCode)
                        .orElse(StatusCode.BAD);

                boolean secureChannelError =
                        statusCode.getValue() == StatusCodes.Bad_TcpSecureChannelUnknown ||
                                statusCode.getValue() == StatusCodes.Bad_SecureChannelIdInvalid;

                if (initialAttempt && secureChannelError) {
                    // Try again if bootstrapping failed because we couldn't re-open the previous channel.
                    logger.debug("Previous channel unusable, retrying...");
                    connect(future, false);
                } else {
                    future.completeExceptionally(ex);
                }
            }
        });

        return future;
    }

    public void disconnect() {
        State currentState = state.get();

        if (currentState instanceof Connecting) {
            ((Connecting) currentState).future.thenAccept(this::closeSecureChannel);
        } else if (currentState instanceof Connected) {
            ((Connected) currentState).future.thenAccept(this::closeSecureChannel);
        }
    }

    private void closeSecureChannel(Channel ch) {
        RequestHeader requestHeader = new RequestHeader(
                NodeId.NULL_VALUE, DateTime.now(), uint(0), uint(0), null, uint(0), null);

        CloseSecureChannelRequest request = new CloseSecureChannelRequest(requestHeader);

        ch.pipeline().fireUserEventTriggered(request);
    }

    private static abstract class State {
    }

    private static class Idle extends State {
    }

    private static class Connecting extends State {
        private final CompletableFuture<Channel> future = new CompletableFuture<>();
    }

    private static class Connected extends State {
        private final CompletableFuture<Channel> future;

        private Connected(CompletableFuture<Channel> future) {
            this.future = future;
        }
    }

}
