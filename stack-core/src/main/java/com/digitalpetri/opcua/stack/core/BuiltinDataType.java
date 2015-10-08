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

package com.digitalpetri.opcua.stack.core;

import java.util.UUID;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.ULong;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.enumerated.IdType;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

public enum BuiltinDataType {

    Boolean(1, Boolean.class),
    SByte(2, Byte.class),
    Byte(3, UByte.class),
    Int16(4, Short.class),
    UInt16(5, UShort.class),
    Int32(6, Integer.class),
    UInt32(7, UInteger.class),
    Int64(8, Long.class),
    UInt64(9, ULong.class),
    Float(10, Float.class),
    Double(11, Double.class),
    String(12, String.class),
    DateTime(13, DateTime.class),
    Guid(14, UUID.class),
    ByteString(15, ByteString.class),
    XmlElement(16, XmlElement.class),
    NodeId(17, NodeId.class),
    ExpandedNodeId(18, ExpandedNodeId.class),
    StatusCode(19, StatusCode.class),
    QualifiedName(20, QualifiedName.class),
    LocalizedText(21, LocalizedText.class),
    ExtensionObject(22, ExtensionObject.class),
    DataValue(23, DataValue.class),
    Variant(24, Variant.class),
    DiagnosticInfo(25, DiagnosticInfo.class);

    private final int typeId;
    private final Class<?> backingClass;

    BuiltinDataType(int typeId, Class<?> backingClass) {
        this.typeId = typeId;
        this.backingClass = backingClass;
    }

    public int getTypeId() {
        return typeId;
    }

    public Class<?> getBackingClass() {
        return backingClass;
    }

    private static final BiMap<Integer, Class<?>> BackingClassesById;
    private static final BiMap<NodeId, Class<?>> BackingClassesByNodeId;

    static {
        ImmutableBiMap.Builder<Integer, Class<?>> builder = ImmutableBiMap.<Integer, Class<?>>builder();
        ImmutableBiMap.Builder<NodeId, Class<?>> builder2 = ImmutableBiMap.<NodeId, Class<?>>builder();

        for (BuiltinDataType dataType : values()) {
            builder.put(dataType.getTypeId(), dataType.getBackingClass());
            builder2.put(new NodeId(0, dataType.getTypeId()), dataType.getBackingClass());
        }

        BackingClassesById = builder.build();
        BackingClassesByNodeId = builder2.build();
    }

    /**
     * @param backingClass the backing {@link Class} of the builtin type.
     * @return the id of the builtin type backed by {@code backingClass}.
     */
    public static int getBuiltinTypeId(Class<?> backingClass) {
        if (backingClass.isPrimitive()) {
            if (backingClass == boolean.class) backingClass = Boolean.class;
            else if (backingClass == byte.class) backingClass = Byte.class;
            else if (backingClass == short.class) backingClass = Short.class;
            else if (backingClass == int.class) backingClass = Integer.class;
            else if (backingClass == long.class) backingClass = Long.class;
            else if (backingClass == float.class) backingClass = Float.class;
            else if (backingClass == double.class) backingClass = Double.class;
        }

        return BackingClassesById.inverse().get(backingClass);
    }

    /**
     * @param typeId the id of the builtin type.
     * @return the {@link Class} backing the builtin type.
     */
    public static Class<?> getBackingClass(int typeId) {
        return BackingClassesById.get(typeId);
    }

    public static Class<?> getBackingClass(NodeId typeId) {
        return BackingClassesByNodeId.get(typeId);
    }

    public static Class<?> getBackingClass(ExpandedNodeId typeId) {
        if (typeId.getNamespaceIndex().intValue() == 0 && typeId.getType() == IdType.Numeric) {
            Number id = (Number) typeId.getIdentifier();
            return BackingClassesById.get(id.intValue());
        }

        return null;
    }

    public static boolean isBuiltin(int typeId) {
        return BackingClassesById.containsKey(typeId);
    }

    public static boolean isBuiltin(NodeId typeId) {
        return BackingClassesByNodeId.containsKey(typeId);
    }

    public static boolean isBuiltin(ExpandedNodeId typeId) {
        return typeId.local().map(BackingClassesByNodeId::containsKey).orElse(false);
    }

}
