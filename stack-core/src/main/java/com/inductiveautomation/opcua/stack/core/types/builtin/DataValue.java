package com.inductiveautomation.opcua.stack.core.types.builtin;

import javax.annotation.Nullable;

import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class DataValue {

    private final Variant value;
    private final StatusCode status;
    private final DateTime sourceTime;
    private final DateTime serverTime;

    public DataValue(long statusCode) {
        this(new StatusCode(statusCode));
    }

    public DataValue(StatusCode statusCode) {
        this(Variant.NullValue, statusCode, DateTime.MinValue);
    }

    public DataValue(Variant value) {
        this(value, StatusCode.Good);
    }

    public DataValue(Variant value, StatusCode status) {
        this(value, status, DateTime.now());
    }

    public DataValue(Variant value, StatusCode status, @Nullable DateTime time) {
        this(value, status, time, time);
    }

    public DataValue(Variant value, StatusCode status, @Nullable DateTime sourceTime, @Nullable DateTime serverTime) {
        this.value = value;
        this.status = status;
        this.sourceTime = sourceTime;
        this.serverTime = serverTime;
    }

    public Variant getValue() {
        return value;
    }

    public StatusCode getStatusCode() {
        return status;
    }

    @Nullable
    public DateTime getSourceTime() {
        return sourceTime;
    }

    @Nullable
    public DateTime getServerTime() {
        return serverTime;
    }

    public DataValue withStatus(StatusCode status) {
        return new DataValue(value, status, sourceTime, serverTime);
    }

    public DataValue withSourceTime(@Nullable DateTime sourceTime) {
        return new DataValue(value, status, sourceTime, serverTime);
    }

    public DataValue withServerTime(@Nullable DateTime serverTime) {
        return new DataValue(value, status, sourceTime, serverTime);
    }

    /**
     * Derive a new {@link DataValue} from a given {@link DataValue}.
     *
     * @param from       the {@link DataValue} to derive from.
     * @param timestamps the timestamps to return in the derived value.
     * @return a derived {@link DataValue}.
     */
    public static DataValue derivedValue(DataValue from, TimestampsToReturn timestamps) {
        boolean includeSource = timestamps == TimestampsToReturn.Source || timestamps == TimestampsToReturn.Both;
        boolean includeServer = timestamps == TimestampsToReturn.Server || timestamps == TimestampsToReturn.Both;

        return new DataValue(
                from.value,
                from.status,
                includeSource ? from.sourceTime : null,
                includeServer ? from.serverTime : null
        );
    }

    /**
     * Derive a new {@link DataValue} from a given {@link DataValue}.
     * <p>
     * The value is assumed to be for a non-value Node attribute, and therefore the source timestamp is not returned.
     *
     * @param from       the {@link DataValue} to derive from.
     * @param timestamps the timestamps to return in the derived value.
     * @return a derived {@link DataValue}.
     */
    public static DataValue derivedNonValue(DataValue from, TimestampsToReturn timestamps) {
        boolean includeServer = timestamps == TimestampsToReturn.Server || timestamps == TimestampsToReturn.Both;

        return new DataValue(
                from.value,
                from.status,
                null,
                includeServer ? from.serverTime : null
        );
    }

}
