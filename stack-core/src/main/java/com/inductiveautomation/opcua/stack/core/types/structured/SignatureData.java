package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

@UaDataType("SignatureData")
public class SignatureData implements UaStructure {

    public static final NodeId TypeId = Identifiers.SignatureData;
    public static final NodeId BinaryEncodingId = Identifiers.SignatureData_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SignatureData_Encoding_DefaultXml;

    protected final String _algorithm;
    protected final ByteString _signature;

    public SignatureData() {
        this._algorithm = null;
        this._signature = null;
    }

    public SignatureData(String _algorithm, ByteString _signature) {
        this._algorithm = _algorithm;
        this._signature = _signature;
    }

    public String getAlgorithm() {
        return _algorithm;
    }

    public ByteString getSignature() {
        return _signature;
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


    public static void encode(SignatureData signatureData, UaEncoder encoder) {
        encoder.encodeString("Algorithm", signatureData._algorithm);
        encoder.encodeByteString("Signature", signatureData._signature);
    }

    public static SignatureData decode(UaDecoder decoder) {
        String _algorithm = decoder.decodeString("Algorithm");
        ByteString _signature = decoder.decodeByteString("Signature");

        return new SignatureData(_algorithm, _signature);
    }

    static {
        DelegateRegistry.registerEncoder(SignatureData::encode, SignatureData.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SignatureData::decode, SignatureData.class, BinaryEncodingId, XmlEncodingId);
    }

}
