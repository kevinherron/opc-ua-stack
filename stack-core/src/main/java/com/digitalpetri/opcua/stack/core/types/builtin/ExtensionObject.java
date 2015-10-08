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

package com.digitalpetri.opcua.stack.core.types.builtin;

import com.digitalpetri.opcua.stack.core.UaSerializationException;
import com.digitalpetri.opcua.stack.core.serialization.DataTypeEncoding;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public final class ExtensionObject {

    public enum BodyType {
        ByteString,
        XmlElement
    }

    private volatile Object decoded;

    private final BodyType bodyType;

    private final Object encoded;
    private final NodeId encodingTypeId;

    public ExtensionObject(ByteString encoded, NodeId encodingTypeId) {
        this.encoded = encoded;
        this.encodingTypeId = encodingTypeId;

        bodyType = BodyType.ByteString;
    }

    public ExtensionObject(XmlElement encoded, NodeId encodingTypeId) {
        this.encoded = encoded;
        this.encodingTypeId = encodingTypeId;

        bodyType = BodyType.XmlElement;
    }

    public Object getEncoded() {
        return encoded;
    }

    public NodeId getEncodingTypeId() {
        return encodingTypeId;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public <T> T decode() throws UaSerializationException {
        return decode(DataTypeEncoding.OPC_UA);
    }

    public <T> T decode(DataTypeEncoding context) throws UaSerializationException {
        if (decoded != null) return (T) decoded;

        switch (bodyType) {
            case ByteString: {
                ByteString bs = (ByteString) encoded;
                if (bs == null || bs.isNull()) {
                    return null;
                } else {
                    decoded = context.decodeFromByteString((ByteString) encoded, encodingTypeId);
                    return (T) decoded;
                }
            }

            case XmlElement: {
                XmlElement e = (XmlElement) encoded;
                if (e == null || e.isNull()) {
                    return null;
                } else {
                    decoded = context.decodeFromXmlElement((XmlElement) encoded, encodingTypeId);
                    return (T) decoded;
                }
            }
        }

        throw new RuntimeException("encodingType=" + bodyType);
    }

    public static ExtensionObject encode(UaStructure structure) throws UaSerializationException {
        return encodeAsByteString(structure, structure.getBinaryEncodingId());
    }

    public static ExtensionObject encodeAsByteString(Object object, NodeId encodingTypeId) throws UaSerializationException {
        return encodeAsByteString(object, encodingTypeId, DataTypeEncoding.OPC_UA);
    }

    public static ExtensionObject encodeAsXmlElement(Object object, NodeId encodingTypeId) throws UaSerializationException {
        return encodeAsXmlElement(object, encodingTypeId, DataTypeEncoding.OPC_UA);
    }

    public static ExtensionObject encodeAsByteString(Object object,
                                                     NodeId encodingTypeId,
                                                     DataTypeEncoding context) throws UaSerializationException {

        ByteString encoded = context.encodeToByteString(object, encodingTypeId);

        return new ExtensionObject(encoded, encodingTypeId);
    }

    public static ExtensionObject encodeAsXmlElement(Object object,
                                                     NodeId encodingTypeId,
                                                     DataTypeEncoding context) throws UaSerializationException {

        XmlElement encoded = context.encodeToXmlElement(object, encodingTypeId);

        return new ExtensionObject(encoded, encodingTypeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtensionObject that = (ExtensionObject) o;

        return Objects.equal(encoded, that.encoded) &&
                Objects.equal(encodingTypeId, that.encodingTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(encoded, encodingTypeId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("encoded", encoded)
                .add("encodingTypeId", encodingTypeId)
                .toString();
    }

}
