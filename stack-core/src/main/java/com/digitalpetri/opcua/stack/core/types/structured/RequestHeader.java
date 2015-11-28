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

@UaDataType("RequestHeader")
public class RequestHeader implements UaStructure {

    public static final NodeId TypeId = Identifiers.RequestHeader;
    public static final NodeId BinaryEncodingId = Identifiers.RequestHeader_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RequestHeader_Encoding_DefaultXml;

    protected final NodeId _authenticationToken;
    protected final DateTime _timestamp;
    protected final UInteger _requestHandle;
    protected final UInteger _returnDiagnostics;
    protected final String _auditEntryId;
    protected final UInteger _timeoutHint;
    protected final ExtensionObject _additionalHeader;

    public RequestHeader() {
        this._authenticationToken = null;
        this._timestamp = null;
        this._requestHandle = null;
        this._returnDiagnostics = null;
        this._auditEntryId = null;
        this._timeoutHint = null;
        this._additionalHeader = null;
    }

    public RequestHeader(NodeId _authenticationToken, DateTime _timestamp, UInteger _requestHandle, UInteger _returnDiagnostics, String _auditEntryId, UInteger _timeoutHint, ExtensionObject _additionalHeader) {
        this._authenticationToken = _authenticationToken;
        this._timestamp = _timestamp;
        this._requestHandle = _requestHandle;
        this._returnDiagnostics = _returnDiagnostics;
        this._auditEntryId = _auditEntryId;
        this._timeoutHint = _timeoutHint;
        this._additionalHeader = _additionalHeader;
    }

    public NodeId getAuthenticationToken() { return _authenticationToken; }

    public DateTime getTimestamp() { return _timestamp; }

    public UInteger getRequestHandle() { return _requestHandle; }

    public UInteger getReturnDiagnostics() { return _returnDiagnostics; }

    public String getAuditEntryId() { return _auditEntryId; }

    public UInteger getTimeoutHint() { return _timeoutHint; }

    public ExtensionObject getAdditionalHeader() { return _additionalHeader; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RequestHeader requestHeader, UaEncoder encoder) {
        encoder.encodeNodeId("AuthenticationToken", requestHeader._authenticationToken);
        encoder.encodeDateTime("Timestamp", requestHeader._timestamp);
        encoder.encodeUInt32("RequestHandle", requestHeader._requestHandle);
        encoder.encodeUInt32("ReturnDiagnostics", requestHeader._returnDiagnostics);
        encoder.encodeString("AuditEntryId", requestHeader._auditEntryId);
        encoder.encodeUInt32("TimeoutHint", requestHeader._timeoutHint);
        encoder.encodeExtensionObject("AdditionalHeader", requestHeader._additionalHeader);
    }

    public static RequestHeader decode(UaDecoder decoder) {
        NodeId _authenticationToken = decoder.decodeNodeId("AuthenticationToken");
        DateTime _timestamp = decoder.decodeDateTime("Timestamp");
        UInteger _requestHandle = decoder.decodeUInt32("RequestHandle");
        UInteger _returnDiagnostics = decoder.decodeUInt32("ReturnDiagnostics");
        String _auditEntryId = decoder.decodeString("AuditEntryId");
        UInteger _timeoutHint = decoder.decodeUInt32("TimeoutHint");
        ExtensionObject _additionalHeader = decoder.decodeExtensionObject("AdditionalHeader");

        return new RequestHeader(_authenticationToken, _timestamp, _requestHandle, _returnDiagnostics, _auditEntryId, _timeoutHint, _additionalHeader);
    }

    static {
        DelegateRegistry.registerEncoder(RequestHeader::encode, RequestHeader.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RequestHeader::decode, RequestHeader.class, BinaryEncodingId, XmlEncodingId);
    }

}
