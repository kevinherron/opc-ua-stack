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

@UaDataType("AggregateFilterResult")
public class AggregateFilterResult extends MonitoringFilterResult {

    public static final NodeId TypeId = Identifiers.AggregateFilterResult;
    public static final NodeId BinaryEncodingId = Identifiers.AggregateFilterResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AggregateFilterResult_Encoding_DefaultXml;

    protected final DateTime _revisedStartTime;
    protected final Double _revisedProcessingInterval;
    protected final AggregateConfiguration _revisedAggregateConfiguration;

    public AggregateFilterResult() {
        super();
        this._revisedStartTime = null;
        this._revisedProcessingInterval = null;
        this._revisedAggregateConfiguration = null;
    }

    public AggregateFilterResult(DateTime _revisedStartTime, Double _revisedProcessingInterval, AggregateConfiguration _revisedAggregateConfiguration) {
        super();
        this._revisedStartTime = _revisedStartTime;
        this._revisedProcessingInterval = _revisedProcessingInterval;
        this._revisedAggregateConfiguration = _revisedAggregateConfiguration;
    }

    public DateTime getRevisedStartTime() {
        return _revisedStartTime;
    }

    public Double getRevisedProcessingInterval() {
        return _revisedProcessingInterval;
    }

    public AggregateConfiguration getRevisedAggregateConfiguration() {
        return _revisedAggregateConfiguration;
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


    public static void encode(AggregateFilterResult aggregateFilterResult, UaEncoder encoder) {
        encoder.encodeDateTime("RevisedStartTime", aggregateFilterResult._revisedStartTime);
        encoder.encodeDouble("RevisedProcessingInterval", aggregateFilterResult._revisedProcessingInterval);
        encoder.encodeSerializable("RevisedAggregateConfiguration", aggregateFilterResult._revisedAggregateConfiguration != null ? aggregateFilterResult._revisedAggregateConfiguration : new AggregateConfiguration());
    }

    public static AggregateFilterResult decode(UaDecoder decoder) {
        DateTime _revisedStartTime = decoder.decodeDateTime("RevisedStartTime");
        Double _revisedProcessingInterval = decoder.decodeDouble("RevisedProcessingInterval");
        AggregateConfiguration _revisedAggregateConfiguration = decoder.decodeSerializable("RevisedAggregateConfiguration", AggregateConfiguration.class);

        return new AggregateFilterResult(_revisedStartTime, _revisedProcessingInterval, _revisedAggregateConfiguration);
    }

    static {
        DelegateRegistry.registerEncoder(AggregateFilterResult::encode, AggregateFilterResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AggregateFilterResult::decode, AggregateFilterResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
