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

package com.digitalpetri.opcua.stack.core.util;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Queues up submitted items and executes them in serial on an ExecutorService.
 *
 * @param <T> Submitted item type.
 */
public class ExecutionQueue<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile boolean submitted = false;

    private final Object queueLock = new Object();
    private final LinkedList<T> queue = new LinkedList<>();

    private final ExecutionCallback<T> callback;
    private final ExecutorService service;

    public ExecutionQueue(ExecutionCallback<T> callback, ExecutorService service) {
        this.callback = callback;
        this.service = service;
    }

    /**
     * Submit an item to be delivered to the callback when it's time to execute.
     *
     * @param item Item to be delivered.
     */
    public void submit(T item) {
        synchronized (queueLock) {
            queue.add(item);

            if (!submitted) {
                service.submit(new PollAndExecute());
                submitted = true;
            }
        }
    }

    private class PollAndExecute implements Runnable {
        @Override
        public void run() {
            final T t;

            synchronized (queueLock) {
                t = queue.poll();
            }

            try {
                callback.execute(t);
            } catch (Throwable throwable) {
                logger.warn("Uncaught Throwable during execution.", throwable);
            }

            synchronized (queueLock) {
                if (!queue.isEmpty()) {
                    service.submit(new PollAndExecute());
                } else {
                    submitted = false;
                }
            }
        }
    }

    public static interface ExecutionCallback<T> {
        public void execute(T item);
    }

    /**
     * An {@link ExecutionCallback} that executes {@link Runnable}s.
     */
    public static class RunnableExecutor implements ExecutionCallback<Runnable> {
        @Override
        public void execute(Runnable item) {
            item.run();
        }
    }

    public static final RunnableExecutor RUNNABLE_EXECUTOR = new RunnableExecutor();

}
