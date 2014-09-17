package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class PublishResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.PublishResponse;
    public static final NodeId BinaryEncodingId = Identifiers.PublishResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.PublishResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final Long _subscriptionId;
    protected final Long[] _availableSequenceNumbers;
    protected final Boolean _moreNotifications;
    protected final NotificationMessage _notificationMessage;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public PublishResponse(ResponseHeader _responseHeader, Long _subscriptionId, Long[] _availableSequenceNumbers, Boolean _moreNotifications, NotificationMessage _notificationMessage, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._subscriptionId = _subscriptionId;
        this._availableSequenceNumbers = _availableSequenceNumbers;
        this._moreNotifications = _moreNotifications;
        this._notificationMessage = _notificationMessage;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public Long getSubscriptionId() { return _subscriptionId; }

    public Long[] getAvailableSequenceNumbers() { return _availableSequenceNumbers; }

    public Boolean getMoreNotifications() { return _moreNotifications; }

    public NotificationMessage getNotificationMessage() { return _notificationMessage; }

    public StatusCode[] getResults() { return _results; }

    public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(PublishResponse publishResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", publishResponse._responseHeader);
        encoder.encodeUInt32("SubscriptionId", publishResponse._subscriptionId);
        encoder.encodeArray("AvailableSequenceNumbers", publishResponse._availableSequenceNumbers, encoder::encodeUInt32);
        encoder.encodeBoolean("MoreNotifications", publishResponse._moreNotifications);
        encoder.encodeSerializable("NotificationMessage", publishResponse._notificationMessage);
        encoder.encodeArray("Results", publishResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", publishResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static PublishResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Long[] _availableSequenceNumbers = decoder.decodeArray("AvailableSequenceNumbers", decoder::decodeUInt32, Long.class);
        Boolean _moreNotifications = decoder.decodeBoolean("MoreNotifications");
        NotificationMessage _notificationMessage = decoder.decodeSerializable("NotificationMessage", NotificationMessage.class);
        StatusCode[] _results = decoder.decodeArray("Results", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new PublishResponse(_responseHeader, _subscriptionId, _availableSequenceNumbers, _moreNotifications, _notificationMessage, _results, _diagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(PublishResponse::encode, PublishResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(PublishResponse::decode, PublishResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
