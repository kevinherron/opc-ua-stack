package com.digitalpetri.opcua.stack.core.util;

import java.util.UUID;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.OverloadedType;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import com.google.common.collect.ImmutableMap;

public class TypeUtil {

    private static final ImmutableMap<Class<?>, Integer> BuiltinTypeIds =
            ImmutableMap.<Class<?>, Integer>builder()
                    .put(Boolean.class, 1)
                    .put(Byte.class, 2)
                    .put(Short.class, 4)
                    .put(Integer.class, 6)
                    .put(Long.class, 8)
                    .put(Float.class, 10)
                    .put(Double.class, 11)
                    .put(String.class, 12)
                    .put(DateTime.class, 13)
                    .put(UUID.class, 14)
                    .put(ByteString.class, 15)
                    .put(XmlElement.class, 16)
                    .put(NodeId.class, 17)
                    .put(ExpandedNodeId.class, 18)
                    .put(StatusCode.class, 19)
                    .put(QualifiedName.class, 20)
                    .put(LocalizedText.class, 21)
                    .put(ExtensionObject.class, 22)
                    .put(DataValue.class, 23)
                    .put(Variant.class, 24)
                    .put(DiagnosticInfo.class, 25)
                    .build();

    private static final ImmutableMap<Class<?>, Integer> PrimitiveBuiltinTypeIds =
            ImmutableMap.<Class<?>, Integer>builder()
                    .put(boolean.class, 1)
                    .put(byte.class, 2)
                    .put(short.class, 4)
                    .put(int.class, 6)
                    .put(long.class, 8)
                    .put(float.class, 10)
                    .put(double.class, 11)
                    .build();

    private static final ImmutableMap<OverloadedType, Integer> OverloadedTypeIds =
            ImmutableMap.<OverloadedType, Integer>builder()
                    .put(OverloadedType.UByte, 3)
                    .put(OverloadedType.UInt16, 5)
                    .put(OverloadedType.UInt32, 7)
                    .put(OverloadedType.UInt64, 9)
                    .build();

    private static final ImmutableMap<Integer, Class<?>> BackingClasses =
            ImmutableMap.<Integer, Class<?>>builder()
                    .put(1, Boolean.class)  // Boolean
                    .put(2, Byte.class)     // SByte
                    .put(3, Integer.class)  // UByte
                    .put(4, Short.class)    // Int16
                    .put(5, Integer.class)  // UInt16
                    .put(6, Integer.class)  // Int32
                    .put(7, Long.class)     // UInt32
                    .put(8, Long.class)     // Int64
                    .put(9, Long.class)     // UInt64
                    .put(10, Float.class)
                    .put(11, Double.class)
                    .put(12, String.class)
                    .put(13, DateTime.class)
                    .put(14, UUID.class)
                    .put(15, ByteString.class)
                    .put(16, XmlElement.class)
                    .put(17, NodeId.class)
                    .put(18, ExpandedNodeId.class)
                    .put(19, StatusCode.class)
                    .put(20, QualifiedName.class)
                    .put(21, LocalizedText.class)
                    .put(23, ExtensionObject.class)
                    .put(24, DataValue.class)
                    .put(25, DiagnosticInfo.class)
                    .build();

    /**
     * @param backingType the backing {@link Class} of the builtin type.
     * @return the id of the builtin type backed by {@code backingType}.
     */
    public static int getBuiltinTypeId(Class<?> backingType) {
        if (backingType.isPrimitive()) {
            return PrimitiveBuiltinTypeIds.get(backingType);
        } else {
            if (!BuiltinTypeIds.containsKey(backingType)) {
                throw new RuntimeException();
            }
            return BuiltinTypeIds.get(backingType);
        }
    }

    /**
     * @param overloadedType the {@link OverloadedType} of the builtin type.
     * @return the id of the builtin type backed by {@code overloadedType}.
     */
    public static int getBuiltinTypeId(OverloadedType overloadedType) {
        return OverloadedTypeIds.get(overloadedType);
    }

    /**
     * @param typeId the id of the builtin type in question.
     * @return {@code true} if this typeId is backed by a overloaded type.
     */
    public static boolean isOverloadedType(int typeId) {
        return typeId == 3 || typeId == 5 || typeId == 7 || typeId == 9;
    }

    /**
     * @param typeId the id of the builtin type.
     * @return the {@link OverloadedType} backing the builtin type for {@code typeId}.
     */
    public static OverloadedType getOverloadedType(int typeId) {
        switch (typeId) {
            case 3:
                return OverloadedType.UByte;
            case 5:
                return OverloadedType.UInt16;
            case 7:
                return OverloadedType.UInt32;
            case 9:
                return OverloadedType.UInt64;
            default:
                throw new IllegalArgumentException("not an OverloadedType id: " + typeId);
        }
    }

    /**
     * @param typeId the id of the builtin type.
     * @return the {@link Class} backing the builtin type.
     */
    public static Class<?> getBackingClass(int typeId) {
        return BackingClasses.get(typeId);
    }

}
