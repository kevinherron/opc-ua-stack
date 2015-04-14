/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.inductiveautomation.opcua.stack.core.util;

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
