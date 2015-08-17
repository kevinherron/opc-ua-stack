package com.digitalpetri.opcua.stack.client.fsm;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;

public interface ConnectionState {

    CompletableFuture<Void> CF_VOID_COMPLETED = CompletableFuture.completedFuture(null);

    CompletableFuture<Void> activate(ConnectionEvent event, ConnectionStateFsm fsm);

    CompletableFuture<Void> deactivate(ConnectionEvent event, ConnectionStateFsm fsm);

    ConnectionState transition(ConnectionEvent event, ConnectionStateFsm fsm);

    CompletableFuture<ClientSecureChannel> getSecureChannel();

}
