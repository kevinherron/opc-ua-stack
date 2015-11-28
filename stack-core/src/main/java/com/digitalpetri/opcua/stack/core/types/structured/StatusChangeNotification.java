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
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

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

    public StatusCode getStatus() { return _status; }

    public DiagnosticInfo getDiagnosticInfo() { return _diagnosticInfo; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


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
