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
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("DataChangeNotification")
public class DataChangeNotification extends NotificationData {

    public static final NodeId TypeId = Identifiers.DataChangeNotification;
    public static final NodeId BinaryEncodingId = Identifiers.DataChangeNotification_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DataChangeNotification_Encoding_DefaultXml;

    protected final MonitoredItemNotification[] _monitoredItems;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public DataChangeNotification() {
        super();
        this._monitoredItems = null;
        this._diagnosticInfos = null;
    }

    public DataChangeNotification(MonitoredItemNotification[] _monitoredItems, DiagnosticInfo[] _diagnosticInfos) {
        super();
        this._monitoredItems = _monitoredItems;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public MonitoredItemNotification[] getMonitoredItems() { return _monitoredItems; }

    public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(DataChangeNotification dataChangeNotification, UaEncoder encoder) {
        encoder.encodeArray("MonitoredItems", dataChangeNotification._monitoredItems, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", dataChangeNotification._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static DataChangeNotification decode(UaDecoder decoder) {
        MonitoredItemNotification[] _monitoredItems = decoder.decodeArray("MonitoredItems", decoder::decodeSerializable, MonitoredItemNotification.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new DataChangeNotification(_monitoredItems, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(DataChangeNotification::encode, DataChangeNotification.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DataChangeNotification::decode, DataChangeNotification.class, BinaryEncodingId, XmlEncodingId);
    }

}
