package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class NodeAttributes implements UaStructure {

    public static final NodeId TypeId = Identifiers.NodeAttributes;
    public static final NodeId BinaryEncodingId = Identifiers.NodeAttributes_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.NodeAttributes_Encoding_DefaultXml;

    protected final Long _specifiedAttributes;
    protected final LocalizedText _displayName;
    protected final LocalizedText _description;
    protected final Long _writeMask;
    protected final Long _userWriteMask;

    public NodeAttributes(Long _specifiedAttributes, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask) {
        this._specifiedAttributes = _specifiedAttributes;
        this._displayName = _displayName;
        this._description = _description;
        this._writeMask = _writeMask;
        this._userWriteMask = _userWriteMask;
    }

    public Long getSpecifiedAttributes() { return _specifiedAttributes; }

    public LocalizedText getDisplayName() { return _displayName; }

    public LocalizedText getDescription() { return _description; }

    public Long getWriteMask() { return _writeMask; }

    public Long getUserWriteMask() { return _userWriteMask; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(NodeAttributes nodeAttributes, UaEncoder encoder) {
        encoder.encodeUInt32("SpecifiedAttributes", nodeAttributes._specifiedAttributes);
        encoder.encodeLocalizedText("DisplayName", nodeAttributes._displayName);
        encoder.encodeLocalizedText("Description", nodeAttributes._description);
        encoder.encodeUInt32("WriteMask", nodeAttributes._writeMask);
        encoder.encodeUInt32("UserWriteMask", nodeAttributes._userWriteMask);
    }

    public static NodeAttributes decode(UaDecoder decoder) {
        Long _specifiedAttributes = decoder.decodeUInt32("SpecifiedAttributes");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");

        return new NodeAttributes(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask);
    }

    static {
        DelegateRegistry.registerEncoder(NodeAttributes::encode, NodeAttributes.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(NodeAttributes::decode, NodeAttributes.class, BinaryEncodingId, XmlEncodingId);
    }

}
