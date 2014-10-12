package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class DataChangeNotification extends NotificationData {

    public static final NodeId TypeId = Identifiers.DataChangeNotification;
    public static final NodeId BinaryEncodingId = Identifiers.DataChangeNotification_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DataChangeNotification_Encoding_DefaultXml;

    protected final MonitoredItemNotification[] _monitoredItems;
    protected final DiagnosticInfo[] _diagnosticInfos;

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
