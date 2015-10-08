/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
