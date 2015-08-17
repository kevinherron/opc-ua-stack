package com.digitalpetri.opcua.stack.core.util;

import java.util.ArrayDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncSemaphore {

    private final ArrayDeque<CompletableFuture<SemaphorePermit>> waitQueue = new ArrayDeque<>();

    private final AtomicInteger availablePermits;

    public AsyncSemaphore(int initialPermits) {
        availablePermits = new AtomicInteger(initialPermits);
    }

    public synchronized CompletableFuture<SemaphorePermit> acquire() {
        CompletableFuture<SemaphorePermit> f = new CompletableFuture<>();

        if (availablePermits.get() > 0) {
            availablePermits.decrementAndGet();

            f.complete(new PermitImpl());
        } else {
            waitQueue.addLast(f);
        }

        return f;
    }

    public interface SemaphorePermit {

        /**
         * Releases this semaphore permit.
         */
        void release();

    }

    private final class PermitImpl implements SemaphorePermit {
        @Override
        public void release() {
            CompletableFuture<SemaphorePermit> next;

            synchronized (AsyncSemaphore.this) {
                next = waitQueue.pollFirst();
                if (next == null) availablePermits.incrementAndGet();
            }

            if (next != null) next.complete(new PermitImpl());
        }
    }

}
