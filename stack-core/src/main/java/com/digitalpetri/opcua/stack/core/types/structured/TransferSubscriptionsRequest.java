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
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("TransferSubscriptionsRequest")
public class TransferSubscriptionsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.TransferSubscriptionsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.TransferSubscriptionsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TransferSubscriptionsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger[] _subscriptionIds;
    protected final Boolean _sendInitialValues;

    public TransferSubscriptionsRequest() {
        this._requestHeader = null;
        this._subscriptionIds = null;
        this._sendInitialValues = null;
    }

    public TransferSubscriptionsRequest(RequestHeader _requestHeader, UInteger[] _subscriptionIds, Boolean _sendInitialValues) {
        this._requestHeader = _requestHeader;
        this._subscriptionIds = _subscriptionIds;
        this._sendInitialValues = _sendInitialValues;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public UInteger[] getSubscriptionIds() {
        return _subscriptionIds;
    }

    public Boolean getSendInitialValues() {
        return _sendInitialValues;
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


    public static void encode(TransferSubscriptionsRequest transferSubscriptionsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", transferSubscriptionsRequest._requestHeader != null ? transferSubscriptionsRequest._requestHeader : new RequestHeader());
        encoder.encodeArray("SubscriptionIds", transferSubscriptionsRequest._subscriptionIds, encoder::encodeUInt32);
        encoder.encodeBoolean("SendInitialValues", transferSubscriptionsRequest._sendInitialValues);
    }

    public static TransferSubscriptionsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger[] _subscriptionIds = decoder.decodeArray("SubscriptionIds", decoder::decodeUInt32, UInteger.class);
        Boolean _sendInitialValues = decoder.decodeBoolean("SendInitialValues");

        return new TransferSubscriptionsRequest(_requestHeader, _subscriptionIds, _sendInitialValues);
    }

    static {
        DelegateRegistry.registerEncoder(TransferSubscriptionsRequest::encode, TransferSubscriptionsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TransferSubscriptionsRequest::decode, TransferSubscriptionsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
