package com.inductiveautomation.opcua.stack.core.types.builtin;

import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;

import javax.annotation.Nullable;

public class DataValue {

    private final Variant value;
    private final StatusCode status;
    private final DateTime sourceTime;
    private final DateTime serverTime;

    public DataValue(long statusCode) {
        this(new StatusCode(statusCode));
    }

    public DataValue(StatusCode statusCode) {
        this(Variant.NullValue, statusCode);
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

    public DataValue(Variant value, StatusCode status, @Nullable DateTime serverTime, @Nullable DateTime sourceTime) {
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

    /**
     * Derive a new {@link DataValue} from a given {@link DataValue}.
     *
     * @param from       the {@link DataValue} to derive from.
     * @param timestamps the timestamps to return in the derived value.
     * @return a derived {@link DataValue}.
     */
    public static DataValue derived(DataValue from, TimestampsToReturn timestamps) {
        boolean includeServer = timestamps == TimestampsToReturn.Server || timestamps == TimestampsToReturn.Both;
        boolean includeSource = timestamps == TimestampsToReturn.Source || timestamps == TimestampsToReturn.Both;

        return new DataValue(
                from.value,
                from.status,
                includeServer ? from.serverTime : null,
                includeSource ? from.sourceTime : null
        );
    }

}
