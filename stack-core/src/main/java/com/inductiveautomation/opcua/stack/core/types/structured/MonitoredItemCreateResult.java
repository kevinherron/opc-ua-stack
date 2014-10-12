package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

public class MonitoredItemCreateResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoredItemCreateResult;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoredItemCreateResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoredItemCreateResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final Long _monitoredItemId;
    protected final Double _revisedSamplingInterval;
    protected final Long _revisedQueueSize;
    protected final ExtensionObject _filterResult;

    public MonitoredItemCreateResult(StatusCode _statusCode, Long _monitoredItemId, Double _revisedSamplingInterval, Long _revisedQueueSize, ExtensionObject _filterResult) {
        this._statusCode = _statusCode;
        this._monitoredItemId = _monitoredItemId;
        this._revisedSamplingInterval = _revisedSamplingInterval;
        this._revisedQueueSize = _revisedQueueSize;
        this._filterResult = _filterResult;
    }

    public StatusCode getStatusCode() { return _statusCode; }

    public Long getMonitoredItemId() { return _monitoredItemId; }

    public Double getRevisedSamplingInterval() { return _revisedSamplingInterval; }

    public Long getRevisedQueueSize() { return _revisedQueueSize; }

    public ExtensionObject getFilterResult() { return _filterResult; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(MonitoredItemCreateResult monitoredItemCreateResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", monitoredItemCreateResult._statusCode);
        encoder.encodeUInt32("MonitoredItemId", monitoredItemCreateResult._monitoredItemId);
        encoder.encodeDouble("RevisedSamplingInterval", monitoredItemCreateResult._revisedSamplingInterval);
        encoder.encodeUInt32("RevisedQueueSize", monitoredItemCreateResult._revisedQueueSize);
        encoder.encodeExtensionObject("FilterResult", monitoredItemCreateResult._filterResult);
    }

    public static MonitoredItemCreateResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        Long _monitoredItemId = decoder.decodeUInt32("MonitoredItemId");
        Double _revisedSamplingInterval = decoder.decodeDouble("RevisedSamplingInterval");
        Long _revisedQueueSize = decoder.decodeUInt32("RevisedQueueSize");
        ExtensionObject _filterResult = decoder.decodeExtensionObject("FilterResult");

        return new MonitoredItemCreateResult(_statusCode, _monitoredItemId, _revisedSamplingInterval, _revisedQueueSize, _filterResult);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoredItemCreateResult::encode, MonitoredItemCreateResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoredItemCreateResult::decode, MonitoredItemCreateResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
