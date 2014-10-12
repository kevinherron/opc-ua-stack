package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class SamplingIntervalDiagnosticsDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.SamplingIntervalDiagnosticsDataType;
    public static final NodeId BinaryEncodingId = Identifiers.SamplingIntervalDiagnosticsDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SamplingIntervalDiagnosticsDataType_Encoding_DefaultXml;

    protected final Double _samplingInterval;
    protected final Long _monitoredItemCount;
    protected final Long _maxMonitoredItemCount;
    protected final Long _disabledMonitoredItemCount;

    public SamplingIntervalDiagnosticsDataType(Double _samplingInterval, Long _monitoredItemCount, Long _maxMonitoredItemCount, Long _disabledMonitoredItemCount) {
        this._samplingInterval = _samplingInterval;
        this._monitoredItemCount = _monitoredItemCount;
        this._maxMonitoredItemCount = _maxMonitoredItemCount;
        this._disabledMonitoredItemCount = _disabledMonitoredItemCount;
    }

    public Double getSamplingInterval() { return _samplingInterval; }

    public Long getMonitoredItemCount() { return _monitoredItemCount; }

    public Long getMaxMonitoredItemCount() { return _maxMonitoredItemCount; }

    public Long getDisabledMonitoredItemCount() { return _disabledMonitoredItemCount; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SamplingIntervalDiagnosticsDataType samplingIntervalDiagnosticsDataType, UaEncoder encoder) {
        encoder.encodeDouble("SamplingInterval", samplingIntervalDiagnosticsDataType._samplingInterval);
        encoder.encodeUInt32("MonitoredItemCount", samplingIntervalDiagnosticsDataType._monitoredItemCount);
        encoder.encodeUInt32("MaxMonitoredItemCount", samplingIntervalDiagnosticsDataType._maxMonitoredItemCount);
        encoder.encodeUInt32("DisabledMonitoredItemCount", samplingIntervalDiagnosticsDataType._disabledMonitoredItemCount);
    }

    public static SamplingIntervalDiagnosticsDataType decode(UaDecoder decoder) {
        Double _samplingInterval = decoder.decodeDouble("SamplingInterval");
        Long _monitoredItemCount = decoder.decodeUInt32("MonitoredItemCount");
        Long _maxMonitoredItemCount = decoder.decodeUInt32("MaxMonitoredItemCount");
        Long _disabledMonitoredItemCount = decoder.decodeUInt32("DisabledMonitoredItemCount");

        return new SamplingIntervalDiagnosticsDataType(_samplingInterval, _monitoredItemCount, _maxMonitoredItemCount, _disabledMonitoredItemCount);
    }

    static {
        DelegateRegistry.registerEncoder(SamplingIntervalDiagnosticsDataType::encode, SamplingIntervalDiagnosticsDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SamplingIntervalDiagnosticsDataType::decode, SamplingIntervalDiagnosticsDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
