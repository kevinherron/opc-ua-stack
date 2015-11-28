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
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;

@UaDataType("MonitoredItemCreateRequest")
public class MonitoredItemCreateRequest implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoredItemCreateRequest;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoredItemCreateRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoredItemCreateRequest_Encoding_DefaultXml;

    protected final ReadValueId _itemToMonitor;
    protected final MonitoringMode _monitoringMode;
    protected final MonitoringParameters _requestedParameters;

    public MonitoredItemCreateRequest() {
        this._itemToMonitor = null;
        this._monitoringMode = null;
        this._requestedParameters = null;
    }

    public MonitoredItemCreateRequest(ReadValueId _itemToMonitor, MonitoringMode _monitoringMode, MonitoringParameters _requestedParameters) {
        this._itemToMonitor = _itemToMonitor;
        this._monitoringMode = _monitoringMode;
        this._requestedParameters = _requestedParameters;
    }

    public ReadValueId getItemToMonitor() { return _itemToMonitor; }

    public MonitoringMode getMonitoringMode() { return _monitoringMode; }

    public MonitoringParameters getRequestedParameters() { return _requestedParameters; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(MonitoredItemCreateRequest monitoredItemCreateRequest, UaEncoder encoder) {
        encoder.encodeSerializable("ItemToMonitor", monitoredItemCreateRequest._itemToMonitor != null ? monitoredItemCreateRequest._itemToMonitor : new ReadValueId());
        encoder.encodeEnumeration("MonitoringMode", monitoredItemCreateRequest._monitoringMode);
        encoder.encodeSerializable("RequestedParameters", monitoredItemCreateRequest._requestedParameters != null ? monitoredItemCreateRequest._requestedParameters : new MonitoringParameters());
    }

    public static MonitoredItemCreateRequest decode(UaDecoder decoder) {
        ReadValueId _itemToMonitor = decoder.decodeSerializable("ItemToMonitor", ReadValueId.class);
        MonitoringMode _monitoringMode = decoder.decodeEnumeration("MonitoringMode", MonitoringMode.class);
        MonitoringParameters _requestedParameters = decoder.decodeSerializable("RequestedParameters", MonitoringParameters.class);

        return new MonitoredItemCreateRequest(_itemToMonitor, _monitoringMode, _requestedParameters);
    }

    static {
        DelegateRegistry.registerEncoder(MonitoredItemCreateRequest::encode, MonitoredItemCreateRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoredItemCreateRequest::decode, MonitoredItemCreateRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
