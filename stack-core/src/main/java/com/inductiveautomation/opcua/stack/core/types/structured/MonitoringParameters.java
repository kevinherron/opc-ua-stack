package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public class MonitoringParameters implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoringParameters;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoringParameters_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoringParameters_Encoding_DefaultXml;

    protected final UInteger _clientHandle;
    protected final Double _samplingInterval;
    protected final ExtensionObject _filter;
    protected final UInteger _queueSize;
    protected final Boolean _discardOldest;

    public MonitoringParameters(UInteger _clientHandle, Double _samplingInterval, ExtensionObject _filter, UInteger _queueSize, Boolean _discardOldest) {
        this._clientHandle = _clientHandle;
        this._samplingInterval = _samplingInterval;
        this._filter = _filter;
        this._queueSize = _queueSize;
        this._discardOldest = _discardOldest;
    }

    public UInteger getClientHandle() {
        return _clientHandle;
    }

    public Double getSamplingInterval() {
        return _samplingInterval;
    }

    public ExtensionObject getFilter() {
        return _filter;
    }

    public UInteger getQueueSize() {
        return _queueSize;
    }

    public Boolean getDiscardOldest() {
        return _discardOldest;
    }

    @Override
    public NodeId getTypeId() {
        return TypeId;
    }

    @Override
    public NodeId getBinaryEncodingId() {
        return BinaryEncodingId;
    }

    @Override
    public NodeId getXmlEncodingId() {
        return XmlEncodingId;
    }


    public static void encode(MonitoringParameters monitoringParameters, UaEncoder encoder) {
        encoder.encodeUInt32("ClientHandle", monitoringParameters._clientHandle);
        encoder.encodeDouble("SamplingInterval", monitoringParameters._samplingInterval);
        encoder.encodeExtensionObject("Filter", monitoringParameters._filter);
        encoder.encodeUInt32("QueueSize", monitoringParameters._queueSize);
        encoder.encodeBoolean("DiscardOldest", monitoringParameters._discardOldest);
    }

    public static MonitoringParameters decode(UaDecoder decoder) {
        UInteger _clientHandle = decoder.decodeUInt32("ClientHandle");
        Double _samplingInterval = decoder.decodeDouble("SamplingInterval");
        ExtensionObject _filter = decoder.decodeExtensionObject("Filter");
        UInteger _queueSize = decoder.decodeUInt32("QueueSize");
        Boolean _discardOldest = decoder.decodeBoolean("DiscardOldest");

        return new MonitoringParameters(_clientHandle, _samplingInterval, _filter, _queueSize, _discardOldest);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringParameters::encode, MonitoringParameters.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoringParameters::decode, MonitoringParameters.class, BinaryEncodingId, XmlEncodingId);
    }

}
