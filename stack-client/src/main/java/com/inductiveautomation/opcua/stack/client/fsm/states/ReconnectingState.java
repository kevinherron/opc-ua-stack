package com.inductiveautomation.opcua.stack.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.client.UaTcpStackClient;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateContext;
import com.inductiveautomation.opcua.stack.client.fsm.ConnectionStateEvent;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSecureChannelRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import io.netty.channel.Channel;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class ReconnectingState implements ConnectionState {

    private final CompletableFuture<Channel> channelFuture;

    public ReconnectingState(CompletableFuture<Channel> channelFuture) {
        this.channelFuture = channelFuture;
    }

    @Override
    public ConnectionState transition(ConnectionStateEvent event, ConnectionStateContext context) {
        switch (event) {
            case CONNECT_SUCCESS:
                return new ConnectedState(channelFuture);

            case CONNECTION_LOST:
                // If we are able to get a TCP connection and then it is subsequently lost during reconnect, the
                // server may be rejecting our attempt to resume the previous secure channel but not sending us a
                // Bad_TcpSecureChannelUnknown Error message.
                context.getClient().getSecureChannel().setChannelId(0);

                // Intentional fall through.
            case CONNECT_FAILURE:
                CompletableFuture<Channel> reconnectFuture = UaTcpStackClient.bootstrap(context.getClient());

                reconnectFuture.whenCompleteAsync((ch, ex) -> {
                    if (ch != null) {
                        context.handleEvent(ConnectionStateEvent.CONNECT_SUCCESS);
                    } else {
                        context.handleEvent(ConnectionStateEvent.CONNECT_FAILURE);
                    }
                }, context.getClient().getExecutorService());

                return new ReconnectingState(reconnectFuture);

            case DISCONNECT_REQUESTED:
                channelFuture.thenAccept(ch -> {
                    RequestHeader requestHeader = new RequestHeader(
                            NodeId.NULL_VALUE, DateTime.now(), uint(0), uint(0), null, uint(0), null);

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
