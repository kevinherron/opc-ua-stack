
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class DataTypeAttributes extends NodeAttributes {

	public static final NodeId TypeId = Identifiers.DataTypeAttributes;
	public static final NodeId BinaryEncodingId = Identifiers.DataTypeAttributes_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.DataTypeAttributes_Encoding_DefaultXml;

	protected final Boolean _isAbstract;

	public DataTypeAttributes(Long _specifiedAttributes, LocalizedText _displayName, LocalizedText _description, Long _writeMask, Long _userWriteMask, Boolean _isAbstract) {
        super(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask);

		this._isAbstract = _isAbstract;
	}

	public Boolean getIsAbstract() { return _isAbstract; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(DataTypeAttributes dataTypeAttributes, UaEncoder encoder) {
		encoder.encodeUInt32("SpecifiedAttributes", dataTypeAttributes._specifiedAttributes);
		encoder.encodeLocalizedText("DisplayName", dataTypeAttributes._displayName);
		encoder.encodeLocalizedText("Description", dataTypeAttributes._description);
		encoder.encodeUInt32("WriteMask", dataTypeAttributes._writeMask);
		encoder.encodeUInt32("UserWriteMask", dataTypeAttributes._userWriteMask);
		encoder.encodeBoolean("IsAbstract", dataTypeAttributes._isAbstract);
	}

	public static DataTypeAttributes decode(UaDecoder decoder) {
        Long _specifiedAttributes = decoder.decodeUInt32("SpecifiedAttributes");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        Long _writeMask = decoder.decodeUInt32("WriteMask");
        Long _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        Boolean _isAbstract = decoder.decodeBoolean("IsAbstract");

		return new DataTypeAttributes(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask, _isAbstract);
	}

	static {
		DelegateRegistry.registerEncoder(DataTypeAttributes::encode, DataTypeAttributes.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(DataTypeAttributes::decode, DataTypeAttributes.class, BinaryEncodingId, XmlEncodingId);
	}

}
