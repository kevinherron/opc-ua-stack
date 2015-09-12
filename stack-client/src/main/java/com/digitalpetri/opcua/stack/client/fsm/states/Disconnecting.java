package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionEvent;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateFsm;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class Disconnecting implements ConnectionState {

    private final ClientSecureChannel channel;

    public Disconnecting(@Nullable ClientSecureChannel channel) {
        this.channel = channel;
    }

    @Override
    public CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm) {
        if (channel != null) {
            return closeSecureChannel(fsm, channel);
        } else {
            fsm.handleEvent(ConnectionEvent.DisconnectSucceeded);
            return CF_VOID_COMPLETED;
        }
    }

    private CompletableFuture<Void> closeSecureChannel(ConnectionStateFsm fsm, ClientSecureChannel sc) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        RequestHeader requestHeader = new RequestHeader(
                NodeId.NULL_VALUE, DateTime.now(), uint(0), uint(0), null, uint(0), null);

        sc.getChannel().pipeline().addFirst(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                fsm.handleEvent(ConnectionEvent.DisconnectSucceeded);

                future.complete(null);

                super.channelInactive(ctx);
            }
        });

        CloseSecureChannelRequest request = new CloseSecureChannelRequest(requestHeader);
        sc.getChannel().pipeline().fireUserEventTriggered(request);

        return future;
    }

    @Override
    public CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm) {
        return CF_VOID_COMPLETED;
    }

    @Override
    public ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm) {
        switch (event) {
            case ConnectRequested:
                return new Connecting(new CompletableFuture<>());

            case DisconnectSucceeded:
                return new Idle();
        }

        return this;
    }

    @Override
    public CompletableFuture<ClientSecureChannel> getSecureChannel() {
        CompletableFuture<ClientSecureChannel> f = new CompletableFuture<>();
        f.completeExceptionally(new UaException(StatusCodes.Bad_ServerNotConnected, "disconnecting"));
        return f;
    }

    @Override
    public String toString() {
        return "Disconnecting{}";
    }

}
