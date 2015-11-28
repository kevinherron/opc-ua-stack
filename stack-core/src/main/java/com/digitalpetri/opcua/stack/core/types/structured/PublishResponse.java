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
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("PublishResponse")
public class PublishResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.PublishResponse;
    public static final NodeId BinaryEncodingId = Identifiers.PublishResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.PublishResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final UInteger _subscriptionId;
    protected final UInteger[] _availableSequenceNumbers;
    protected final Boolean _moreNotifications;
    protected final NotificationMessage _notificationMessage;
    protected final StatusCode[] _results;
    protected final DiagnosticInfo[] _diagnosticInfos;

    public PublishResponse() {
        this._responseHeader = null;
        this._subscriptionId = null;
        this._availableSequenceNumbers = null;
        this._moreNotifications = null;
        this._notificationMessage = null;
        this._results = null;
        this._diagnosticInfos = null;
    }

    public PublishResponse(ResponseHeader _responseHeader, UInteger _subscriptionId, UInteger[] _availableSequenceNumbers, Boolean _moreNotifications, NotificationMessage _notificationMessage, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {
        this._responseHeader = _responseHeader;
        this._subscriptionId = _subscriptionId;
        this._availableSequenceNumbers = _availableSequenceNumbers;
        this._moreNotifications = _moreNotifications;
        this._notificationMessage = _notificationMessage;
        this._results = _results;
        this._diagnosticInfos = _diagnosticInfos;
    }

    public ResponseHeader getResponseHeader() { return _responseHeader; }

    public UInteger getSubscriptionId() { return _subscriptionId; }

    public UInteger[] getAvailableSequenceNumbers() { return _availableSequenceNumbers; }

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
        encoder.encodeSerializable("ResponseHeader", publishResponse._responseHeader != null ? publishResponse._responseHeader : new ResponseHeader());
        encoder.encodeUInt32("SubscriptionId", publishResponse._subscriptionId);
        encoder.encodeArray("AvailableSequenceNumbers", publishResponse._availableSequenceNumbers, encoder::encodeUInt32);
        encoder.encodeBoolean("MoreNotifications", publishResponse._moreNotifications);
        encoder.encodeSerializable("NotificationMessage", publishResponse._notificationMessage != null ? publishResponse._notificationMessage : new NotificationMessage());
        encoder.encodeArray("Results", publishResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", publishResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static PublishResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        UInteger[] _availableSequenceNumbers = decoder.decodeArray("AvailableSequenceNumbers", decoder::decodeUInt32, UInteger.class);
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
