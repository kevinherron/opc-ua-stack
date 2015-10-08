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

import java.util.concurrent.atomic.AtomicLong;

public class LongSequence {

    private final AtomicLong atomic;

    private final long low;
    private final long high;

    public LongSequence(long low, long high) {
        this.low = low;
        this.high = high;

        atomic = new AtomicLong(low);
    }

    /**
     * @return the current value in the sequence, followed by an increment.
     */
    public Long getAndIncrement() {
        while (true) {
            Long current = atomic.get();
            Long next = (current >= high ? low : current + 1);
            if (atomic.compareAndSet(current, next)) {
                return current;
            }
        }
    }

    /**
     * @return the current value in the sequence, without incrementing.
     */
    public Long get() {
        return atomic.get();
    }

}
