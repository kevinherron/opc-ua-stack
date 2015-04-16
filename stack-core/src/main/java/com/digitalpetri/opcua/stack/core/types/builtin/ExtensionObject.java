package com.digitalpetri.opcua.stack.core.types.builtin;

import javax.annotation.Nonnull;

import com.digitalpetri.opcua.stack.core.serialization.UaSerializable;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public final class ExtensionObject {

    private final Object object;
    private final NodeId dataTypeEncodingId;

    public ExtensionObject(@Nonnull UaStructure structure) {
        this.object = structure;
        this.dataTypeEncodingId = structure.getBinaryEncodingId();
    }

    public ExtensionObject(UaSerializable serializable, NodeId dataTypeEncodingId) {
        this.object = serializable;
        this.dataTypeEncodingId = dataTypeEncodingId;
    }

    public ExtensionObject(ByteString byteString, NodeId dataTypeEncodingId) {
        this.object = byteString;
        this.dataTypeEncodingId = dataTypeEncodingId;
    }

    public ExtensionObject(XmlElement xmlElement, NodeId dataTypeEncodingId) {
        this.object = xmlElement;
        this.dataTypeEncodingId = dataTypeEncodingId;
    }

    public Object getObject() {
        return object;
    }

    public NodeId getDataTypeEncodingId() {
        return dataTypeEncodingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtensionObject that = (ExtensionObject) o;

        return Objects.equal(object, that.object) &&
                Objects.equal(dataTypeEncodingId, that.dataTypeEncodingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(object, dataTypeEncodingId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("object", object)
                .add("dataTypeEncodingId", dataTypeEncodingId)
                .toString();
    }

}
