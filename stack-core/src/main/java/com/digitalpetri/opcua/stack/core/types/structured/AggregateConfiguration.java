
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class AggregateConfiguration implements UaStructure {

	public static final NodeId TypeId = Identifiers.AggregateConfiguration;
	public static final NodeId BinaryEncodingId = Identifiers.AggregateConfiguration_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.AggregateConfiguration_Encoding_DefaultXml;

	protected final Boolean _useServerCapabilitiesDefaults;
	protected final Boolean _treatUncertainAsBad;
	protected final Short _percentDataBad;
	protected final Short _percentDataGood;
	protected final Boolean _useSlopedExtrapolation;

	public AggregateConfiguration(Boolean _useServerCapabilitiesDefaults, Boolean _treatUncertainAsBad, Short _percentDataBad, Short _percentDataGood, Boolean _useSlopedExtrapolation) {

		this._useServerCapabilitiesDefaults = _useServerCapabilitiesDefaults;
		this._treatUncertainAsBad = _treatUncertainAsBad;
		this._percentDataBad = _percentDataBad;
		this._percentDataGood = _percentDataGood;
		this._useSlopedExtrapolation = _useSlopedExtrapolation;
	}

	public Boolean getUseServerCapabilitiesDefaults() { return _useServerCapabilitiesDefaults; }
	public Boolean getTreatUncertainAsBad() { return _treatUncertainAsBad; }
	public Short getPercentDataBad() { return _percentDataBad; }
	public Short getPercentDataGood() { return _percentDataGood; }
	public Boolean getUseSlopedExtrapolation() { return _useSlopedExtrapolation; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(AggregateConfiguration aggregateConfiguration, UaEncoder encoder) {
		encoder.encodeBoolean("UseServerCapabilitiesDefaults", aggregateConfiguration._useServerCapabilitiesDefaults);
		encoder.encodeBoolean("TreatUncertainAsBad", aggregateConfiguration._treatUncertainAsBad);
		encoder.encodeByte("PercentDataBad", aggregateConfiguration._percentDataBad);
		encoder.encodeByte("PercentDataGood", aggregateConfiguration._percentDataGood);
		encoder.encodeBoolean("UseSlopedExtrapolation", aggregateConfiguration._useSlopedExtrapolation);
	}

	public static AggregateConfiguration decode(UaDecoder decoder) {
        Boolean _useServerCapabilitiesDefaults = decoder.decodeBoolean("UseServerCapabilitiesDefaults");
        Boolean _treatUncertainAsBad = decoder.decodeBoolean("TreatUncertainAsBad");
        Short _percentDataBad = decoder.decodeByte("PercentDataBad");
        Short _percentDataGood = decoder.decodeByte("PercentDataGood");
        Boolean _useSlopedExtrapolation = decoder.decodeBoolean("UseSlopedExtrapolation");

		return new AggregateConfiguration(_useServerCapabilitiesDefaults, _treatUncertainAsBad, _percentDataBad, _percentDataGood, _useSlopedExtrapolation);
	}

	static {
		DelegateRegistry.registerEncoder(AggregateConfiguration::encode, AggregateConfiguration.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(AggregateConfiguration::decode, AggregateConfiguration.class, BinaryEncodingId, XmlEncodingId);
	}

}
