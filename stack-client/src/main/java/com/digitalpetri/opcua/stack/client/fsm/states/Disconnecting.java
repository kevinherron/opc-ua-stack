package com.digitalpetri.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.client.fsm.ConnectionState;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateContext;
import com.digitalpetri.opcua.stack.client.fsm.ConnectionStateEvent;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.Channel;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class Disconnecting implements ConnectionState {

    private final CompletableFuture<Channel> future;

    public Disconnecting(CompletableFuture<Channel> future) {
        this.future = future;
    }

    @Override
    public void activate(ConnectionStateEvent fromEvent, ConnectionStateContext context) {
        if (future.isDone()) {
            future.whenComplete((channel, ex) -> {
                if (channel != null) {
                    RequestHeader requestHeader = new RequestHeader(
                            NodeId.NULL_VALUE, DateTime.now(), uint(0), uint(0), null, uint(0), null);

                    CloseSecureChannelRequest request = new CloseSecureChannelRequest(requestHeader);

                    channel.pipeline().fireUserEventTriggered(request);
                }

                context.handleEvent(ConnectionStateEvent.DISCONNECT_SUCCESS);
            });
        } else {
            future.completeExceptionally(new UaException(StatusCodes.Bad_ServerNotConnected));

            context.handleEvent(ConnectionStateEvent.DISCONNECT_SUCCESS);
        }
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_REQUESTED:
                return new Connecting(new CompletableFuture<>());

            case DISCONNECT_SUCCESS:
            case ERR_CONNECTION_LOST:
                return new Disconnected();
        }

        return this;
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return future;
    }

}
