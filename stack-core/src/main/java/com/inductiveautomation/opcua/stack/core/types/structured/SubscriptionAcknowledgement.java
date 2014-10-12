package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class SubscriptionAcknowledgement implements UaStructure {

    public static final NodeId TypeId = Identifiers.SubscriptionAcknowledgement;
    public static final NodeId BinaryEncodingId = Identifiers.SubscriptionAcknowledgement_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SubscriptionAcknowledgement_Encoding_DefaultXml;

    protected final Long _subscriptionId;
    protected final Long _sequenceNumber;

    public SubscriptionAcknowledgement(Long _subscriptionId, Long _sequenceNumber) {
        this._subscriptionId = _subscriptionId;
        this._sequenceNumber = _sequenceNumber;
    }

    public Long getSubscriptionId() { return _subscriptionId; }

    public Long getSequenceNumber() { return _sequenceNumber; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SubscriptionAcknowledgement subscriptionAcknowledgement, UaEncoder encoder) {
        encoder.encodeUInt32("SubscriptionId", subscriptionAcknowledgement._subscriptionId);
        encoder.encodeUInt32("SequenceNumber", subscriptionAcknowledgement._sequenceNumber);
    }

    public static SubscriptionAcknowledgement decode(UaDecoder decoder) {
        Long _subscriptionId = decoder.decodeUInt32("SubscriptionId");
        Long _sequenceNumber = decoder.decodeUInt32("SequenceNumber");

        return new SubscriptionAcknowledgement(_subscriptionId, _sequenceNumber);
    }

    static {
        DelegateRegistry.registerEncoder(SubscriptionAcknowledgement::encode, SubscriptionAcknowledgement.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SubscriptionAcknowledgement::decode, SubscriptionAcknowledgement.class, BinaryEncodingId, XmlEncodingId);
    }

}
