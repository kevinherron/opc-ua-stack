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

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("ReadProcessedDetails")
public class ReadProcessedDetails extends HistoryReadDetails {

    public static final NodeId TypeId = Identifiers.ReadProcessedDetails;
    public static final NodeId BinaryEncodingId = Identifiers.ReadProcessedDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ReadProcessedDetails_Encoding_DefaultXml;

    protected final DateTime _startTime;
    protected final DateTime _endTime;
    protected final Double _processingInterval;
    protected final NodeId[] _aggregateType;
    protected final AggregateConfiguration _aggregateConfiguration;

    public ReadProcessedDetails() {
        super();
        this._startTime = null;
        this._endTime = null;
        this._processingInterval = null;
        this._aggregateType = null;
        this._aggregateConfiguration = null;
    }

    public ReadProcessedDetails(DateTime _startTime, DateTime _endTime, Double _processingInterval, NodeId[] _aggregateType, AggregateConfiguration _aggregateConfiguration) {
        super();
        this._startTime = _startTime;
        this._endTime = _endTime;
        this._processingInterval = _processingInterval;
        this._aggregateType = _aggregateType;
        this._aggregateConfiguration = _aggregateConfiguration;
    }

    public DateTime getStartTime() {
        return _startTime;
    }

    public DateTime getEndTime() {
        return _endTime;
    }

    public Double getProcessingInterval() {
        return _processingInterval;
    }

    public NodeId[] getAggregateType() {
        return _aggregateType;
    }

    public AggregateConfiguration getAggregateConfiguration() {
        return _aggregateConfiguration;
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


    public static void encode(ReadProcessedDetails readProcessedDetails, UaEncoder encoder) {
        encoder.encodeDateTime("StartTime", readProcessedDetails._startTime);
        encoder.encodeDateTime("EndTime", readProcessedDetails._endTime);
        encoder.encodeDouble("ProcessingInterval", readProcessedDetails._processingInterval);
        encoder.encodeArray("AggregateType", readProcessedDetails._aggregateType, encoder::encodeNodeId);
        encoder.encodeSerializable("AggregateConfiguration", readProcessedDetails._aggregateConfiguration != null ? readProcessedDetails._aggregateConfiguration : new AggregateConfiguration());
    }

    public static ReadProcessedDetails decode(UaDecoder decoder) {
        DateTime _startTime = decoder.decodeDateTime("StartTime");
        DateTime _endTime = decoder.decodeDateTime("EndTime");
        Double _processingInterval = decoder.decodeDouble("ProcessingInterval");
        NodeId[] _aggregateType = decoder.decodeArray("AggregateType", decoder::decodeNodeId, NodeId.class);
        AggregateConfiguration _aggregateConfiguration = decoder.decodeSerializable("AggregateConfiguration", AggregateConfiguration.class);

        return new ReadProcessedDetails(_startTime, _endTime, _processingInterval, _aggregateType, _aggregateConfiguration);
    }

    static {
        DelegateRegistry.registerEncoder(ReadProcessedDetails::encode, ReadProcessedDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ReadProcessedDetails::decode, ReadProcessedDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
