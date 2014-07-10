package com.digitalpetri.opcua.stack.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import io.netty.channel.Channel;

public abstract class AbstractChannelManager {

    private final AtomicReference<State> state = new AtomicReference<>(new Idle());

    public CompletableFuture<Channel> getChannel() {
        State s = state.get();

        if (s instanceof Idle) {
            Connecting nextState = new Connecting();

            if (state.compareAndSet(s, nextState)) return _connect(nextState.future);
            else return getChannel();
        } else if (s instanceof Connecting) {
            return ((Connecting) s).future;
        } else if (s instanceof Connected) {
            return CompletableFuture.completedFuture(((Connected) s).channel);
        } else {
            throw new IllegalStateException("illegal state: " + s);
        }
    }

    private CompletableFuture<Channel> _connect(CompletableFuture<Channel> future) {
        future.whenComplete((ch, ex) -> {
            if (ch != null) {
                state.set(new Connected(ch));
                ch.closeFuture().addListener(closeFuture -> state.set(new Idle()));
            }
        });

        return connect(future);
    }

    protected abstract CompletableFuture<Channel> connect(CompletableFuture<Channel> future);

    public boolean isConnected() {
        return state.get() instanceof Connected;
    }

    private static interface State {}

    private static class Idle implements State {}

    private static class Connecting implements State {
        private final CompletableFuture<Channel> future = new CompletableFuture<>();
    }

    private static class Connected implements State {
        private final Channel channel;

        private Connected(Channel channel) {
            this.channel = channel;
        }
    }

}
