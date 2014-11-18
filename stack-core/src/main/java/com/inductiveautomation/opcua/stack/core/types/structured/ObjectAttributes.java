package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ObjectAttributes")
public class ObjectAttributes extends NodeAttributes {

    public static final NodeId TypeId = Identifiers.ObjectAttributes;
    public static final NodeId BinaryEncodingId = Identifiers.ObjectAttributes_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ObjectAttributes_Encoding_DefaultXml;

    protected final UByte _eventNotifier;

    public ObjectAttributes() {
        super(null, null, null, null, null);
        this._eventNotifier = null;
    }

    public ObjectAttributes(UInteger _specifiedAttributes, LocalizedText _displayName, LocalizedText _description, UInteger _writeMask, UInteger _userWriteMask, UByte _eventNotifier) {
        super(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask);
        this._eventNotifier = _eventNotifier;
    }

    public UByte getEventNotifier() {
        return _eventNotifier;
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


    public static void encode(ObjectAttributes objectAttributes, UaEncoder encoder) {
        encoder.encodeUInt32("SpecifiedAttributes", objectAttributes._specifiedAttributes);
        encoder.encodeLocalizedText("DisplayName", objectAttributes._displayName);
        encoder.encodeLocalizedText("Description", objectAttributes._description);
        encoder.encodeUInt32("WriteMask", objectAttributes._writeMask);
        encoder.encodeUInt32("UserWriteMask", objectAttributes._userWriteMask);
        encoder.encodeByte("EventNotifier", objectAttributes._eventNotifier);
    }

    public static ObjectAttributes decode(UaDecoder decoder) {
        UInteger _specifiedAttributes = decoder.decodeUInt32("SpecifiedAttributes");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        UInteger _writeMask = decoder.decodeUInt32("WriteMask");
        UInteger _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        UByte _eventNotifier = decoder.decodeByte("EventNotifier");

        return new ObjectAttributes(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask, _eventNotifier);
    }

    static {
        DelegateRegistry.registerEncoder(ObjectAttributes::encode, ObjectAttributes.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ObjectAttributes::decode, ObjectAttributes.class, BinaryEncodingId, XmlEncodingId);
    }

}
