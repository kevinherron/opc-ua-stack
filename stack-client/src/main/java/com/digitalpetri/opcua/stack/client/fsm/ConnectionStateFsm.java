package com.digitalpetri.opcua.stack.client.fsm;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.fsm.states.Idle;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.util.AsyncSemaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionStateFsm {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AsyncSemaphore semaphore = new AsyncSemaphore(1);

    private final AtomicReference<ConnectionState> state = new AtomicReference<>(new Idle());

    private final UaTcpStackClient client;

    public ConnectionStateFsm(UaTcpStackClient client) {
        this.client = client;
    }

    public UaTcpStackClient getClient() {
        return client;
    }

    public CompletableFuture<ClientSecureChannel> getChannel() {
        return handleEvent(ConnectionEvent.ConnectRequested)
                .thenCompose(ConnectionState::getSecureChannel);
    }

    public synchronized CompletableFuture<ConnectionState> getState() {
        CompletableFuture<ConnectionState> future = new CompletableFuture<>();

        semaphore.acquire().thenAccept(permit -> {
            ConnectionState cs = state.get();
            permit.release();
            future.complete(cs);
        });

        return future;
    }

    public synchronized CompletableFuture<ConnectionState> handleEvent(ConnectionEvent event) {
        logger.debug("handleEvent({})", event);
        CompletableFuture<ConnectionState> future = new CompletableFuture<>();

        semaphore.acquire().thenAccept(permit -> {
            logger.debug("semaphore acquired - handleEvent({})", event);
            CompletableFuture<ConnectionState> f = handleEvent0(event);

            f.whenComplete((s, t) -> {
                future.complete(s);
                permit.release();
            });
        });

        return future;
    }

    private CompletableFuture<ConnectionState> handleEvent0(ConnectionEvent event) {
        final ConnectionState currState = state.get();
        final ConnectionState nextState = currState.transition(event, this);

        logger.debug("S({}) x E({}) = S'({})", currState, event, nextState);

        if (currState != nextState) {
            logger.debug("deactivating S({})", currState);

            return currState.deactivate(event, this).thenCompose(vd -> {
                logger.debug("deactivated S({})", currState);
                logger.debug("activating S({})", nextState);

                return nextState.activate(event, this);
            }).thenApply(vd -> {
                logger.debug("activated S({})", nextState);

                state.set(nextState);

                return nextState;
            });
        } else {
            return CompletableFuture.completedFuture(currState);
        }
    }

}
