package com.inductiveautomation.opcua.stack.core.types.builtin;

import com.google.common.base.Objects;

public final class StatusCode {

    private static final int SEVERITY_MASK = 0xC0000000;
    private static final int SEVERITY_GOOD = 0x00000000;
    private static final int SEVERITY_UNCERTAIN = 0x40000000;
    private static final int SEVERITY_BAD = 0x80000000;

    public static final StatusCode GOOD = new StatusCode(SEVERITY_GOOD);
    public static final StatusCode BAD = new StatusCode(SEVERITY_BAD);

    private final int value;

    public StatusCode(long value) {
        this((int) value);
    }

    public StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isGood() {
        return (value & SEVERITY_MASK) == SEVERITY_GOOD;
    }

    public boolean isBad() {
        return (value & SEVERITY_MASK) == SEVERITY_BAD;
    }

    public boolean isUncertain() {
        return (value & SEVERITY_MASK) == SEVERITY_UNCERTAIN;
    }

    /**
     * Set the DataValue InfoType and Overflow InfoBits.
     *
     * @return a new {@link StatusCode} DataValue and Overflow bits set.
     */
    public StatusCode withOverflow() {
        return new StatusCode(value | 0x480);
    }

    /**
     * Clear the DataValue InfoType and Overflow InfoBits.
     *
     * @return a new {@link StatusCode} with DataValue and Overflow bits cleared.
     */
    public StatusCode withoutOverflow() {
        return new StatusCode(value | ~0x480);
    }

    /**
     * @return {@code true} if DataValue and Overflow bits are set.
     */
    public boolean isOverflowSet() {
        return (value & 0x480) == 0x480;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusCode that = (StatusCode) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}
