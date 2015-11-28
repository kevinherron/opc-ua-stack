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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ModifySubscriptionRequest")
public class ModifySubscriptionRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.ModifySubscriptionRequest;
    public static final NodeId BinaryEncodingId = Identifiers.ModifySubscriptionRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModifySubscriptionRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _subscriptionId;
    protected final Double _requestedPublishingInterval;
    protected final UInteger _requestedLifetimeCount;
    protected final UInteger _requestedMaxKeepAliveCount;
    protected final UInteger _maxNotificationsPerPublish;
    protected final UByte _priority;

    public ModifySubscriptionRequest() {
        this._requestHeader = null;
        this._subscriptionId = null;
        this._requestedPublishingInterval = null;
        this._requestedLifetimeCount = null;
        this._requestedMaxKeepAliveCount = null;
        this._maxNotificationsPerPublish = null;
        this._priority = null;
    }

    public ModifySubscriptionRequest(RequestHeader _requestHeader, UInteger _subscriptionId, Double _requestedPublishingInterval, UInteger _requestedLifetimeCount, UInteger _requestedMaxKeepAliveCount, UInteger _maxNotificationsPerPublish, UByte _priority) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._requestedPublishingInterval = _requestedPublishingInterval;
        this._requestedLifetimeCount = _requestedLifetimeCount;
        this._requestedMaxKeepAliveCount = _requestedMaxKeepAliveCount;
        this._maxNotificationsPerPublish = _maxNotificationsPerPublish;
        this._priority = _priority;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public UInteger getSubscriptionId() { return _subscriptionId; }

    public Double getRequestedPublishingInterval() { return _requestedPublishingInterval; }

    public UInteger getRequestedLifetimeCount() { return _requestedLifetimeCount; }

    public UInteger getRequestedMaxKeepAliveCount() { return _requestedMaxKeepAliveCount; }

    public UInteger getMaxNotificationsPerPublish() { return _maxNotificationsPerPublish; }

    public UByte getPriority() { return _priority; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ModifySubscriptionRequest modifySubscriptionRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", modifySubscriptionRequest._requestHeader != null ? modifySubscriptionRequest._requestHeader : new RequestHeader());
        encoder.encodeUInt32("SubscriptionId", modifySubscriptionRequest._subscriptionId);
        encoder.encodeDouble("RequestedPublishingInterval", modifySubscriptionRequest._requestedPublishingInterval);
        encoder.encodeUInt32("RequestedLifetimeCount", modifySubscriptionRequest._requestedLifetimeCount);
        encoder.encodeUInt32("RequestedMaxKeepAliveCount", modifySubscriptionRequest._requestedMaxKeepAliveCount);
        encoder.encodeUInt32("MaxNotificationsPerPublish", modifySubscriptionRequest._maxNotificationsPerPublish);
        encoder.encodeByte("Priority", modifySubscriptionRequest._priority);
    }

    public static ModifySubscriptionRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Double _requestedPublishingInterval = decoder.decodeDouble("RequestedPublishingInterval");
        UInteger _requestedLifetimeCount = decoder.decodeUInt32("RequestedLifetimeCount");
        UInteger _requestedMaxKeepAliveCount = decoder.decodeUInt32("RequestedMaxKeepAliveCount");
        UInteger _maxNotificationsPerPublish = decoder.decodeUInt32("MaxNotificationsPerPublish");
        UByte _priority = decoder.decodeByte("Priority");

        return new ModifySubscriptionRequest(_requestHeader, _subscriptionId, _requestedPublishingInterval, _requestedLifetimeCount, _requestedMaxKeepAliveCount, _maxNotificationsPerPublish, _priority);
    }

    static {
        DelegateRegistry.registerEncoder(ModifySubscriptionRequest::encode, ModifySubscriptionRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModifySubscriptionRequest::decode, ModifySubscriptionRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
