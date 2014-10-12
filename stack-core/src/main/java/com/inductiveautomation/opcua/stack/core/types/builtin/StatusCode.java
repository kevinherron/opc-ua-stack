package com.inductiveautomation.opcua.stack.core.types.builtin;

import com.google.common.base.Objects;

public class StatusCode {

    private static final int SeverityMask = 0xC0000000;
    private static final int SeverityGood = 0x00000000;
    private static final int SeverityUncertain = 0x40000000;
    private static final int SeverityBad = 0x80000000;

    public static final StatusCode Good = new StatusCode(SeverityGood);
    public static final StatusCode Bad = new StatusCode(SeverityBad);

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
        return (value & SeverityMask) == SeverityGood;
    }

    public boolean isBad() {
        return (value & SeverityMask) == SeverityBad;
    }

    public boolean isUncertain() {
        return (value & SeverityMask) == SeverityUncertain;
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
