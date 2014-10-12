package com.inductiveautomation.opcua.stack.core.util;

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
