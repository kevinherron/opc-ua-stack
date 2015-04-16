package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.Identifiers;

@UaDataType("Range")
public class Range implements UaStructure {

    public static final NodeId TypeId = Identifiers.Range;
    public static final NodeId BinaryEncodingId = Identifiers.Range_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.Range_Encoding_DefaultXml;

    protected final Double _low;
    protected final Double _high;

    public Range() {
        this._low = null;
        this._high = null;
    }

    public Range(Double _low, Double _high) {
        this._low = _low;
        this._high = _high;
    }

    public Double getLow() {
        return _low;
    }

    public Double getHigh() {
        return _high;
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


    public static void encode(Range range, UaEncoder encoder) {
        encoder.encodeDouble("Low", range._low);
        encoder.encodeDouble("High", range._high);
    }

    public static Range decode(UaDecoder decoder) {
        Double _low = decoder.decodeDouble("Low");
        Double _high = decoder.decodeDouble("High");

        return new Range(_low, _high);
    }

    static {
        DelegateRegistry.registerEncoder(Range::encode, Range.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(Range::decode, Range.class, BinaryEncodingId, XmlEncodingId);
    }

}
