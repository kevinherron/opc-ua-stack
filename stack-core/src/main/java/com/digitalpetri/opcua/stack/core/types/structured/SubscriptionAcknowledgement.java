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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("SubscriptionAcknowledgement")
public class SubscriptionAcknowledgement implements UaStructure {

    public static final NodeId TypeId = Identifiers.SubscriptionAcknowledgement;
    public static final NodeId BinaryEncodingId = Identifiers.SubscriptionAcknowledgement_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SubscriptionAcknowledgement_Encoding_DefaultXml;

    protected final UInteger _subscriptionId;
    protected final UInteger _sequenceNumber;

    public SubscriptionAcknowledgement() {
        this._subscriptionId = null;
        this._sequenceNumber = null;
    }

    public SubscriptionAcknowledgement(UInteger _subscriptionId, UInteger _sequenceNumber) {
        this._subscriptionId = _subscriptionId;
        this._sequenceNumber = _sequenceNumber;
    }

    public UInteger getSubscriptionId() {
        return _subscriptionId;
    }

    public UInteger getSequenceNumber() {
        return _sequenceNumber;
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


    public static void encode(SubscriptionAcknowledgement subscriptionAcknowledgement, UaEncoder encoder) {
        encoder.encodeUInt32("SubscriptionId", subscriptionAcknowledgement._subscriptionId);
        encoder.encodeUInt32("SequenceNumber", subscriptionAcknowledgement._sequenceNumber);
    }

    public static SubscriptionAcknowledgement decode(UaDecoder decoder) {
        UInteger _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        UInteger _sequenceNumber = decoder.decodeUInt32("SequenceNumber");

        return new SubscriptionAcknowledgement(_subscriptionId, _sequenceNumber);
    }

    static {
        DelegateRegistry.registerEncoder(SubscriptionAcknowledgement::encode, SubscriptionAcknowledgement.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SubscriptionAcknowledgement::decode, SubscriptionAcknowledgement.class, BinaryEncodingId, XmlEncodingId);
    }

}
