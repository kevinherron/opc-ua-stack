package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("StatusChangeNotification")
public class StatusChangeNotification extends NotificationData {

    public static final NodeId TypeId = Identifiers.StatusChangeNotification;
    public static final NodeId BinaryEncodingId = Identifiers.StatusChangeNotification_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.StatusChangeNotification_Encoding_DefaultXml;

    protected final StatusCode _status;
    protected final DiagnosticInfo _diagnosticInfo;

    public StatusChangeNotification() {
        super();
        this._status = null;
        this._diagnosticInfo = null;
    }

    public StatusChangeNotification(StatusCode _status, DiagnosticInfo _diagnosticInfo) {
        super();
        this._status = _status;
        this._diagnosticInfo = _diagnosticInfo;
    }

    public StatusCode getStatus() {
        return _status;
    }

    public DiagnosticInfo getDiagnosticInfo() {
        return _diagnosticInfo;
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


    public static void encode(StatusChangeNotification statusChangeNotification, UaEncoder encoder) {
        encoder.encodeStatusCode("Status", statusChangeNotification._status);
        encoder.encodeDiagnosticInfo("DiagnosticInfo", statusChangeNotification._diagnosticInfo);
    }

    public static StatusChangeNotification decode(UaDecoder decoder) {
        StatusCode _status = decoder.decodeStatusCode("Status");
        DiagnosticInfo _diagnosticInfo = decoder.decodeDiagnosticInfo("DiagnosticInfo");

        return new StatusChangeNotification(_status, _diagnosticInfo);
    }

    static {
        DelegateRegistry.registerEncoder(StatusChangeNotification::encode, StatusChangeNotification.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(StatusChangeNotification::decode, StatusChangeNotification.class, BinaryEncodingId, XmlEncodingId);
    }

}
