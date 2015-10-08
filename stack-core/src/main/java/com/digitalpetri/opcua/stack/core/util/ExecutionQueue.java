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

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Queues up submitted {@link java.lang.Runnable}s and executes them in serial on an
 * {@link java.util.concurrent.ExecutorService}.
 */
public class ExecutionQueue {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Object queueLock = new Object();
    private final LinkedList<Runnable> queue = new LinkedList<>();

    private volatile boolean pollSubmitted = false;
    private volatile boolean paused = false;

    private final ExecutorService service;

    public ExecutionQueue(ExecutorService service) {
        this.service = service;
    }

    /**
     * Submit a {@link Runnable} to be executed.
     *
     * @param runnable the {@link Runnable} to be executed.
     */
    public void submit(Runnable runnable) {
        synchronized (queueLock) {
            queue.add(runnable);

            maybeSubmitPoll();
        }
    }

    /**
     * Submit a {@link Runnable} to be executed at the head of the queue.
     *
     * @param runnable the {@link Runnable} to be executed.
     */
    public void submitToHead(Runnable runnable) {
        synchronized (queueLock) {
            queue.addFirst(runnable);

            maybeSubmitPoll();
        }
    }

    /**
     * Pause execution of queued {@link java.lang.Runnable}s.
     */
    public void pause() {
        synchronized (queueLock) {
            paused = true;
        }
    }

    /**
     * Resume execution of queued {@link java.lang.Runnable}s.
     */
    public void resume() {
        synchronized (queueLock) {
            paused = false;

            maybeSubmitPoll();
        }
    }

    private void maybeSubmitPoll() {
        synchronized (queueLock) {
            if (!pollSubmitted && !paused && !queue.isEmpty()) {
                service.submit(new PollAndExecute());
                pollSubmitted = true;
            }
        }
    }

    private class PollAndExecute implements Runnable {
        @Override
        public void run() {
            Runnable runnable;

            synchronized (queueLock) {
                runnable = queue.poll();
            }

            try {
                runnable.run();
            } catch (Throwable throwable) {
                log.warn("Uncaught Throwable during execution.", throwable);
            }

            synchronized (queueLock) {
                if (queue.isEmpty() || paused) {
                    pollSubmitted = false;
                } else {
                    // polling remains true
                    service.submit(new PollAndExecute());
                }
            }
        }
    }

}
