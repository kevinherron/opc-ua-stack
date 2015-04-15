package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.inductiveautomation.opcua.stack.client.UaTcpStackClient;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import com.inductiveautomation.opcua.stack.core.Stack;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReconnectingState implements ConnectionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompletableFuture<Channel> channelFuture = new CompletableFuture<>();
    private volatile Channel channel;

    private final long connectDelay;

    public ReconnectingState(long connectDelay) {
        this.connectDelay = connectDelay;
    }

    @Override
    public void activate(ConnectionStateEvent event, ConnectionStateContext context) {
        logger.debug("Reconnecting in {} seconds...", connectDelay);

        Stack.sharedScheduledExecutor().schedule(() -> {
            CompletableFuture<Channel> future = UaTcpStackClient.bootstrap(context.getClient());

            future.whenComplete((channel, ex) -> {
                if (channel != null) {
                    this.channel = channel;
                    context.handleEvent(ConnectionStateEvent.CONNECT_SUCCESS);
                } else {
                    this.channelFuture.completeExceptionally(ex);
                    context.handleEvent(ConnectionStateEvent.CONNECT_FAILURE);
                }
            });
        }, connectDelay, TimeUnit.SECONDS);
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_SUCCESS:
                return new ConnectedState(channel, channelFuture);

            case CONNECTION_LOST:
                // If we are able to get a TCP connection and then it is subsequently lost during reconnect, the
                // server may be rejecting our attempt to resume the previous secure channel but not sending us a
                // Bad_TcpSecureChannelUnknown Error message.
                context.getClient().getSecureChannel().setChannelId(0);

                return this; // returning 'this' prevents activate() from being called.

                // Intentional fall through.
            case CONNECT_FAILURE:
                return new ReconnectingState(nextConnectDelay());

            case DISCONNECT_REQUESTED:
                return new DisconnectingState(channelFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

    private long nextConnectDelay() {
        if (connectDelay == 0) {
            return 1;
        } else {
            return Math.min(connectDelay << 1, 16);
        }
    }

}
