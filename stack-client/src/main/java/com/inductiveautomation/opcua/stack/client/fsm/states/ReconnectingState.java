package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.Channel;

public class ReconnectingState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture;

    public ReconnectingState(CompletableFuture<Channel> channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case ConnectSuccess:
                return new ConnectedState(channelFuture);

            case ConnectFailure:
                CompletableFuture<Channel> reconnectFuture = UaTcpClient.bootstrap(context.getClient());

                reconnectFuture.whenCompleteAsync((ch, ex) -> {
                    if (ch != null) {
                        context.handleEvent(ConnectionStateEvent.ConnectSuccess);
                    } else {
                        context.handleEvent(ConnectionStateEvent.ConnectFailure);
                    }
                }, context.getClient().getExecutorService());

                return new ReconnectingState(reconnectFuture);

            case DisconnectRequested:
                channelFuture.thenAccept(ch -> {
                    RequestHeader requestHeader = new RequestHeader(
                            NodeId.NullValue, DateTime.now(), 0L, 0L, null, 0L, null);

                    CloseSecureChannelRequest request = new CloseSecureChannelRequest(requestHeader);

                    ch.pipeline().fireUserEventTriggered(request);
                });

                return new DisconnectedState();

            default:
                return context.getState();
        }
    }

    @Override
    public CompletableFuture<Channel> getChannelFuture() {
        return channelFuture;
    }

}
