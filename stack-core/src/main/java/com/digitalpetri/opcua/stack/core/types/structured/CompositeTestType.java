package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class CompositeTestType implements UaStructure {

    public static final NodeId TypeId = Identifiers.CompositeTestType;
    public static final NodeId BinaryEncodingId = Identifiers.CompositeTestType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.CompositeTestType_Encoding_DefaultXml;

    protected final ScalarTestType _field1;
    protected final ArrayTestType _field2;

    public CompositeTestType(ScalarTestType _field1, ArrayTestType _field2) {
        this._field1 = _field1;
        this._field2 = _field2;
    }

    public ScalarTestType getField1() { return _field1; }

    public ArrayTestType getField2() { return _field2; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(CompositeTestType compositeTestType, UaEncoder encoder) {
        encoder.encodeSerializable("Field1", compositeTestType._field1);
        encoder.encodeSerializable("Field2", compositeTestType._field2);
    }

    public static CompositeTestType decode(UaDecoder decoder) {
        ScalarTestType _field1 = decoder.decodeSerializable("Field1", ScalarTestType.class);
        ArrayTestType _field2 = decoder.decodeSerializable("Field2", ArrayTestType.class);

        return new CompositeTestType(_field1, _field2);
    }

    static {
        DelegateRegistry.registerEncoder(CompositeTestType::encode, CompositeTestType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(CompositeTestType::decode, CompositeTestType.class, BinaryEncodingId, XmlEncodingId);
    }

}
