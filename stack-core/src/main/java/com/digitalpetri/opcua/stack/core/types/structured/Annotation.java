/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

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
