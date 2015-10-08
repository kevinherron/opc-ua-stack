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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("RepublishResponse")
public class RepublishResponse implements UaResponseMessage {

    public static final NodeId TypeId = Identifiers.RepublishResponse;
    public static final NodeId BinaryEncodingId = Identifiers.RepublishResponse_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RepublishResponse_Encoding_DefaultXml;

    protected final ResponseHeader _responseHeader;
    protected final NotificationMessage _notificationMessage;

    public RepublishResponse() {
        this._responseHeader = null;
        this._notificationMessage = null;
    }

    public RepublishResponse(ResponseHeader _responseHeader, NotificationMessage _notificationMessage) {
        this._responseHeader = _responseHeader;
        this._notificationMessage = _notificationMessage;
    }

    public ResponseHeader getResponseHeader() {
        return _responseHeader;
    }

    public NotificationMessage getNotificationMessage() {
        return _notificationMessage;
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


    public static void encode(RepublishResponse republishResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", republishResponse._responseHeader != null ? republishResponse._responseHeader : new ResponseHeader());
        encoder.encodeSerializable("NotificationMessage", republishResponse._notificationMessage != null ? republishResponse._notificationMessage : new NotificationMessage());
    }

    public static RepublishResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        NotificationMessage _notificationMessage = decoder.decodeSerializable("NotificationMessage", NotificationMessage.class);

        return new RepublishResponse(_responseHeader, _notificationMessage);
    }

    static {
        DelegateRegistry.registerEncoder(RepublishResponse::encode, RepublishResponse.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RepublishResponse::decode, RepublishResponse.class, BinaryEncodingId, XmlEncodingId);
    }

}
