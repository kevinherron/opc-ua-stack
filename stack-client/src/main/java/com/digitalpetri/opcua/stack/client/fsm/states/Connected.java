package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionEvent;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateFsm;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connected implements ConnectionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile ChannelInboundHandlerAdapter inactivityListener;

    private final ClientSecureChannel secureChannel;
    private final CompletableFuture<ClientSecureChannel> channelFuture;

    public Connected(ClientSecureChannel secureChannel, CompletableFuture<ClientSecureChannel> channelFuture) {
        this.secureChannel = secureChannel;
        this.channelFuture = channelFuture;
    }

    @Override
    public CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm) {
        logger.debug("activate({}, {})", event, fsm);

        inactivityListener = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                logger.debug("channelInactive()");

                fsm.handleEvent(ConnectionEvent.ConnectionLost);

                super.channelInactive(ctx);
            }
        };

        secureChannel.getChannel().pipeline().addLast(inactivityListener);

        channelFuture.complete(secureChannel);

        return CF_VOID_COMPLETED;
    }

    @Override
    public CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm) {
        if (secureChannel != null && inactivityListener != null) {
            secureChannel.getChannel().pipeline().remove(inactivityListener);
            logger.debug("Removed inactivityListener");
        }

        return CF_VOID_COMPLETED;
    }

    @Override
    public ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm) {
        switch (event) {
            case ConnectionLost:
                return new ReconnectDelay(0L, secureChannel.getChannelId());

            case DisconnectRequested:
                return new Disconnecting(secureChannel);
        }

        return this;
    }

    @Override
    public CompletableFuture<ClientSecureChannel> getSecureChannel() {
        return channelFuture;
    }

    @Override
    public String toString() {
        return "Connected{}";
    }

}
