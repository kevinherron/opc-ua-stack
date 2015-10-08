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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("NotificationMessage")
public class NotificationMessage implements UaStructure {

    public static final NodeId TypeId = Identifiers.NotificationMessage;
    public static final NodeId BinaryEncodingId = Identifiers.NotificationMessage_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.NotificationMessage_Encoding_DefaultXml;

    protected final UInteger _sequenceNumber;
    protected final DateTime _publishTime;
    protected final ExtensionObject[] _notificationData;

    public NotificationMessage() {
        this._sequenceNumber = null;
        this._publishTime = null;
        this._notificationData = null;
    }

    public NotificationMessage(UInteger _sequenceNumber, DateTime _publishTime, ExtensionObject[] _notificationData) {
        this._sequenceNumber = _sequenceNumber;
        this._publishTime = _publishTime;
        this._notificationData = _notificationData;
    }

    public UInteger getSequenceNumber() {
        return _sequenceNumber;
    }

    public DateTime getPublishTime() {
        return _publishTime;
    }

    public ExtensionObject[] getNotificationData() {
        return _notificationData;
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


    public static void encode(NotificationMessage notificationMessage, UaEncoder encoder) {
        encoder.encodeUInt32("SequenceNumber", notificationMessage._sequenceNumber);
        encoder.encodeDateTime("PublishTime", notificationMessage._publishTime);
        encoder.encodeArray("NotificationData", notificationMessage._notificationData, encoder::encodeExtensionObject);
    }

    public static NotificationMessage decode(UaDecoder decoder) {
        UInteger _sequenceNumber = decoder.decodeUInt32("SequenceNumber");
        DateTime _publishTime = decoder.decodeDateTime("PublishTime");
        ExtensionObject[] _notificationData = decoder.decodeArray("NotificationData", decoder::decodeExtensionObject, ExtensionObject.class);

        return new NotificationMessage(_sequenceNumber, _publishTime, _notificationData);
    }

    static {
        DelegateRegistry.registerEncoder(NotificationMessage::encode, NotificationMessage.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(NotificationMessage::decode, NotificationMessage.class, BinaryEncodingId, XmlEncodingId);
    }

}
