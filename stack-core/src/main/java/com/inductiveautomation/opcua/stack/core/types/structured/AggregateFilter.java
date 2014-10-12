package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class AggregateFilter extends MonitoringFilter {

    public static final NodeId TypeId = Identifiers.AggregateFilter;
    public static final NodeId BinaryEncodingId = Identifiers.AggregateFilter_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AggregateFilter_Encoding_DefaultXml;

    protected final DateTime _startTime;
    protected final NodeId _aggregateType;
    protected final Double _processingInterval;
    protected final AggregateConfiguration _aggregateConfiguration;

    public AggregateFilter(DateTime _startTime, NodeId _aggregateType, Double _processingInterval, AggregateConfiguration _aggregateConfiguration) {
        super();
        this._startTime = _startTime;
        this._aggregateType = _aggregateType;
        this._processingInterval = _processingInterval;
        this._aggregateConfiguration = _aggregateConfiguration;
    }

    public DateTime getStartTime() { return _startTime; }

    public NodeId getAggregateType() { return _aggregateType; }

    public Double getProcessingInterval() { return _processingInterval; }

    public AggregateConfiguration getAggregateConfiguration() { return _aggregateConfiguration; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(AggregateFilter aggregateFilter, UaEncoder encoder) {
        encoder.encodeDateTime("StartTime", aggregateFilter._startTime);
        encoder.encodeNodeId("AggregateType", aggregateFilter._aggregateType);
        encoder.encodeDouble("ProcessingInterval", aggregateFilter._processingInterval);
        encoder.encodeSerializable("AggregateConfiguration", aggregateFilter._aggregateConfiguration);
    }

    public static AggregateFilter decode(UaDecoder decoder) {
        DateTime _startTime = decoder.decodeDateTime("StartTime");
        NodeId _aggregateType = decoder.decodeNodeId("AggregateType");
        Double _processingInterval = decoder.decodeDouble("ProcessingInterval");
        AggregateConfiguration _aggregateConfiguration = decoder.decodeSerializable("AggregateConfiguration", AggregateConfiguration.class);

        return new AggregateFilter(_startTime, _aggregateType, _processingInterval, _aggregateConfiguration);
    }

    static {
        DelegateRegistry.registerEncoder(AggregateFilter::encode, AggregateFilter.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AggregateFilter::decode, AggregateFilter.class, BinaryEncodingId, XmlEncodingId);
    }

}
