package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("TimeZoneDataType")
public class TimeZoneDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.TimeZoneDataType;
    public static final NodeId BinaryEncodingId = Identifiers.TimeZoneDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TimeZoneDataType_Encoding_DefaultXml;

    protected final Short _offset;
    protected final Boolean _daylightSavingInOffset;

    public TimeZoneDataType() {
        this._offset = null;
        this._daylightSavingInOffset = null;
    }

    public TimeZoneDataType(Short _offset, Boolean _daylightSavingInOffset) {
        this._offset = _offset;
        this._daylightSavingInOffset = _daylightSavingInOffset;
    }

    public Short getOffset() {
        return _offset;
    }

    public Boolean getDaylightSavingInOffset() {
        return _daylightSavingInOffset;
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


    public static void encode(TimeZoneDataType timeZoneDataType, UaEncoder encoder) {
        encoder.encodeInt16("Offset", timeZoneDataType._offset);
        encoder.encodeBoolean("DaylightSavingInOffset", timeZoneDataType._daylightSavingInOffset);
    }

    public static TimeZoneDataType decode(UaDecoder decoder) {
        Short _offset = decoder.decodeInt16("Offset");
        Boolean _daylightSavingInOffset = decoder.decodeBoolean("DaylightSavingInOffset");

        return new TimeZoneDataType(_offset, _daylightSavingInOffset);
    }

    static {
        DelegateRegistry.registerEncoder(TimeZoneDataType::encode, TimeZoneDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TimeZoneDataType::decode, TimeZoneDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
