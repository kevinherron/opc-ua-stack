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
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;

@UaDataType("ModifyMonitoredItemsRequest")
public class ModifyMonitoredItemsRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.ModifyMonitoredItemsRequest;
    public static final NodeId BinaryEncodingId = Identifiers.ModifyMonitoredItemsRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ModifyMonitoredItemsRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final UInteger _subscriptionId;
    protected final TimestampsToReturn _timestampsToReturn;
    protected final MonitoredItemModifyRequest[] _itemsToModify;

    public ModifyMonitoredItemsRequest() {
        this._requestHeader = null;
        this._subscriptionId = null;
        this._timestampsToReturn = null;
        this._itemsToModify = null;
    }

    public ModifyMonitoredItemsRequest(RequestHeader _requestHeader, UInteger _subscriptionId, TimestampsToReturn _timestampsToReturn, MonitoredItemModifyRequest[] _itemsToModify) {
        this._requestHeader = _requestHeader;
        this._subscriptionId = _subscriptionId;
        this._timestampsToReturn = _timestampsToReturn;
        this._itemsToModify = _itemsToModify;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public UInteger getSubscriptionId() {
        return _subscriptionId;
    }

    public TimestampsToReturn getTimestampsToReturn() {
        return _timestampsToReturn;
    }

    public MonitoredItemModifyRequest[] getItemsToModify() {
        return _itemsToModify;
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


    public static void encode(ModifyMonitoredItemsRequest modifyMonitoredItemsRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", modifyMonitoredItemsRequest._requestHeader != null ? modifyMonitoredItemsRequest._requestHeader : new RequestHeader());
        encoder.encodeUInt32("SubscriptionId", modifyMonitoredItemsRequest._subscriptionId);
        encoder.encodeEnumeration("TimestampsToReturn", modifyMonitoredItemsRequest._timestampsToReturn);
        encoder.encodeArray("ItemsToModify", modifyMonitoredItemsRequest._itemsToModify, encoder::encodeSerializable);
    }

    public static ModifyMonitoredItemsRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        TimestampsToReturn _timestampsToReturn = decoder.decodeEnumeration("TimestampsToReturn", TimestampsToReturn.class);
        MonitoredItemModifyRequest[] _itemsToModify = decoder.decodeArray("ItemsToModify", decoder::decodeSerializable, MonitoredItemModifyRequest.class);

        return new ModifyMonitoredItemsRequest(_requestHeader, _subscriptionId, _timestampsToReturn, _itemsToModify);
    }

    static {
        DelegateRegistry.registerEncoder(ModifyMonitoredItemsRequest::encode, ModifyMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ModifyMonitoredItemsRequest::decode, ModifyMonitoredItemsRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
