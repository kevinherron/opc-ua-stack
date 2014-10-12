package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class ViewAttributes extends NodeAttributes {

    public static final NodeId TypeId = Identifiers.ViewAttributes;
    public static final NodeId BinaryEncodingId = Identifiers.ViewAttributes_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ViewAttributes_Encoding_DefaultXml;

    protected final Boolean _containsNoLoops;
    protected final Short _eventNotifier;

    public ViewAttributes(Long _specifiedAttributes, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, Boolean _containsNoLoops, Short _eventNotifier) {
        super(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask);
        this._containsNoLoops = _containsNoLoops;
        this._eventNotifier = _eventNotifier;
    }

    public Boolean getContainsNoLoops() { return _containsNoLoops; }

    public Short getEventNotifier() { return _eventNotifier; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ViewAttributes viewAttributes, UaEncoder encoder) {
        encoder.encodeUInt32("SpecifiedAttributes", viewAttributes._specifiedAttributes);
        encoder.encodeLocalizedText("DisplayName", viewAttributes._displayName);
        encoder.encodeLocalizedText("Description", viewAttributes._description);
        encoder.encodeUInt32("WriteMask", viewAttributes._writeMask);
        encoder.encodeUInt32("UserWriteMask", viewAttributes._userWriteMask);
        encoder.encodeBoolean("ContainsNoLoops", viewAttributes._containsNoLoops);
        encoder.encodeByte("EventNotifier", viewAttributes._eventNotifier);
    }

    public static ViewAttributes decode(UaDecoder decoder) {
        Long _specifiedAttributes = decoder.decodeUInt32("SpecifiedAttributes");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        Boolean _containsNoLoops = decoder.decodeBoolean("ContainsNoLoops");
        Short _eventNotifier = decoder.decodeByte("EventNotifier");

        return new ViewAttributes(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask, _containsNoLoops, _eventNotifier);
    }

    static {
        DelegateRegistry.registerEncoder(ViewAttributes::encode, ViewAttributes.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ViewAttributes::decode, ViewAttributes.class, BinaryEncodingId, XmlEncodingId);
    }

}
