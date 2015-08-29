/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    BOOLEAN(1, Boolean.class),
    SBYTE(2, Byte.class),
    BYTE(3, UByte.class),
    INT16(4, Short.class),
    UINT16(5, UShort.class),
    INT32(6, Integer.class),
    UINT32(7, UInteger.class),
    INT64(8, Long.class),
    UINT64(9, ULong.class),
    FLOAT(10, Float.class),
    DOUBLE(11, Double.class),
    STRING(12, String.class),
    DATE_TIME(13, DateTime.class),
    GUID(14, UUID.class),
    BYTE_STRING(15, ByteString.class),
    XML_ELEMENT(16, XmlElement.class),
    NODE_ID(17, NodeId.class),
    EXPANDED_NODE_ID(18, ExpandedNodeId.class),
    STATUS_CODE(19, StatusCode.class),
    QUALIFIED_NAME(20, QualifiedName.class),
    LOCALIZED_TEXT(21, LocalizedText.class),
    EXTENSION_OBJECT(22, ExtensionObject.class),
    DATA_VALUE(23, DataValue.class),
    VARIANT(24, Variant.class),
    DIAGNOSTIC_INFO(25, DiagnosticInfo.class);

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
