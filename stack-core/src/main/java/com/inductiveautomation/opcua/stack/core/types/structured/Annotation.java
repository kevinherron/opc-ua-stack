package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

@UaDataType("Annotation")
public class Annotation implements UaStructure {

    public static final NodeId TypeId = Identifiers.Annotation;
    public static final NodeId BinaryEncodingId = Identifiers.Annotation_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.Annotation_Encoding_DefaultXml;

    protected final String _message;
    protected final String _userName;
    protected final DateTime _annotationTime;

    public Annotation() {
        this._message = null;
        this._userName = null;
        this._annotationTime = null;
    }

    public Annotation(String _message, String _userName, DateTime _annotationTime) {
        this._message = _message;
        this._userName = _userName;
        this._annotationTime = _annotationTime;
    }

    public String getMessage() {
        return _message;
    }

    public String getUserName() {
        return _userName;
    }

    public DateTime getAnnotationTime() {
        return _annotationTime;
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


    public static void encode(Annotation annotation, UaEncoder encoder) {
        encoder.encodeString("Message", annotation._message);
        encoder.encodeString("UserName", annotation._userName);
        encoder.encodeDateTime("AnnotationTime", annotation._annotationTime);
    }

    public static Annotation decode(UaDecoder decoder) {
        String _message = decoder.decodeString("Message");
        String _userName = decoder.decodeString("UserName");
        DateTime _annotationTime = decoder.decodeDateTime("AnnotationTime");

        return new Annotation(_message, _userName, _annotationTime);
    }

    static {
        DelegateRegistry.registerEncoder(Annotation::encode, Annotation.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(Annotation::decode, Annotation.class, BinaryEncodingId, XmlEncodingId);
    }

}
