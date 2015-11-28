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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.DataChangeTrigger;

@UaDataType("DataChangeFilter")
public class DataChangeFilter extends MonitoringFilter {

    public static final NodeId TypeId = Identifiers.DataChangeFilter;
    public static final NodeId BinaryEncodingId = Identifiers.DataChangeFilter_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DataChangeFilter_Encoding_DefaultXml;

    protected final DataChangeTrigger _trigger;
    protected final UInteger _deadbandType;
    protected final Double _deadbandValue;

    public DataChangeFilter() {
        super();
        this._trigger = null;
        this._deadbandType = null;
        this._deadbandValue = null;
    }

    public DataChangeFilter(DataChangeTrigger _trigger, UInteger _deadbandType, Double _deadbandValue) {
        super();
        this._trigger = _trigger;
        this._deadbandType = _deadbandType;
        this._deadbandValue = _deadbandValue;
    }

    public DataChangeTrigger getTrigger() { return _trigger; }

    public UInteger getDeadbandType() { return _deadbandType; }

    public Double getDeadbandValue() { return _deadbandValue; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(DataChangeFilter dataChangeFilter, UaEncoder encoder) {
        encoder.encodeEnumeration("Trigger", dataChangeFilter._trigger);
        encoder.encodeUInt32("DeadbandType", dataChangeFilter._deadbandType);
        encoder.encodeDouble("DeadbandValue", dataChangeFilter._deadbandValue);
    }

    public static DataChangeFilter decode(UaDecoder decoder) {
        DataChangeTrigger _trigger = decoder.decodeEnumeration("Trigger", DataChangeTrigger.class);
        UInteger _deadbandType = decoder.decodeUInt32("DeadbandType");
        Double _deadbandValue = decoder.decodeDouble("DeadbandValue");

        return new DataChangeFilter(_trigger, _deadbandType, _deadbandValue);
    }

    static {
        DelegateRegistry.registerEncoder(DataChangeFilter::encode, DataChangeFilter.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DataChangeFilter::decode, DataChangeFilter.class, BinaryEncodingId, XmlEncodingId);
    }

}
