package com.digitalpetri.opcua.stack.core.types.builtin;

import javax.annotation.Nonnull;
import javax.xml.bind.DatatypeConverter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.digitalpetri.opcua.stack.core.types.enumerated.IdType;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt16;
import com.digitalpetri.opcua.stack.core.util.annotations.UInt32;
import com.google.common.base.Objects;
import com.google.common.primitives.UnsignedInteger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class NodeId {

    public static NodeId NullNumeric = new NodeId(0, 0);
    public static NodeId NullString = new NodeId(0, "");
    public static NodeId NullGuid = new NodeId(0, new UUID(0, 0));
    public static NodeId NullOpaque = new NodeId(0, ByteString.NullValue);

    public static NodeId NullValue = NullNumeric;

    @UInt16
    private final int namespaceIndex;
    private final Object identifier;

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public NodeId(@UInt16 int namespaceIndex, @UInt32 Number identifier) {
        checkArgument(identifier.longValue() >= 0 && identifier.longValue() <= UnsignedInteger.MAX_VALUE.longValue());

        this.namespaceIndex = namespaceIndex;
        this.identifier = identifier.longValue();
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public NodeId(@UInt16 int namespaceIndex, String identifier) {
        checkNotNull(identifier);

        this.namespaceIndex = namespaceIndex;
        this.identifier = identifier;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public NodeId(@UInt16 int namespaceIndex, UUID identifier) {
        checkNotNull(identifier);

        this.namespaceIndex = namespaceIndex;
        this.identifier = identifier;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public NodeId(@UInt16 int namespaceIndex, ByteString identifier) {
        checkNotNull(identifier);

        this.namespaceIndex = namespaceIndex;
        this.identifier = identifier;
    }

    public int getNamespaceIndex() {
        return namespaceIndex;
    }

    public Object getIdentifier() {
        return identifier;
    }

    public IdType getType() {
        if (identifier instanceof Long) {
            return IdType.Numeric;
        } else if (identifier instanceof String) {
            return IdType.String;
        } else if (identifier instanceof UUID) {
            return IdType.Guid;
        } else {
            return IdType.Opaque;
        }
    }

    public ExpandedNodeId expanded() {
        return new ExpandedNodeId(this);
    }

    public boolean isNull() {
        return namespaceIndex == 0 &&
                (NullNumeric.equals(this) || NullString.equals(this) || NullGuid.equals(this) || NullOpaque.equals(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeId nodeId = (NodeId) o;

        return namespaceIndex == nodeId.namespaceIndex && identifier.equals(nodeId.identifier);
    }

    @Override
    public int hashCode() {
        int result = namespaceIndex;
        result = 31 * result + identifier.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("ns", namespaceIndex)
                .add("id", identifier)
                .toString();
    }

    // TODO Re-write this crap... or at the very least write some good unit tests.

    private static final Pattern INT_INT = Pattern.compile("ns=(\\d*);i=(\\d*)");
    private static final Pattern NONE_INT = Pattern.compile("i=(\\d*)");

    private static final Pattern INT_STRING = Pattern.compile("ns=(\\d*);s=(.*)");
    private static final Pattern NONE_STRING = Pattern.compile("s=(.*)");

    private static final Pattern INT_GUID = Pattern.compile("ns=(\\d*);g=([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})");
    private static final Pattern NONE_GUID = Pattern.compile("g=([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})");

    private static final Pattern INT_OPAQUE = Pattern.compile("ns=(\\d*);b=([0-9a-zA-Z\\+/=]*)");
    private static final Pattern NONE_OPAQUE = Pattern.compile("b=([0-9a-zA-Z\\+/=]*)");

    public static NodeId parse(@Nonnull String s) {
        Matcher m;

        m = NONE_STRING.matcher(s);
        if (m.matches()) {
            String obj = m.group(1);
            return new NodeId(0, obj);
        }

        m = NONE_INT.matcher(s);
        if (m.matches()) {
            Integer obj = Integer.valueOf(m.group(1));
            return new NodeId(0, obj);
        }

        m = NONE_GUID.matcher(s);
        if (m.matches()) {
            UUID obj = UUID.fromString(m.group(1));
            return new NodeId(0, obj);
        }

        m = NONE_OPAQUE.matcher(s);
        if (m.matches()) {
            byte[] obj = DatatypeConverter.parseBase64Binary(m.group(1));
            return new NodeId(0, ByteString.of(obj));
        }

        m = INT_INT.matcher(s);
        if (m.matches()) {
            Integer nsi = Integer.valueOf(m.group(1));
            Integer obj = Integer.valueOf(m.group(2));
            return new NodeId(nsi, obj);
        }

        m = INT_STRING.matcher(s);
        if (m.matches()) {
            Integer nsi = Integer.valueOf(m.group(1));
            String obj = m.group(2);
            return new NodeId(nsi, obj);
        }

        m = INT_GUID.matcher(s);
        if (m.matches()) {
            Integer nsi = Integer.valueOf(m.group(1));
            UUID obj = UUID.fromString(m.group(2));
            return new NodeId(nsi, obj);
        }

        m = INT_OPAQUE.matcher(s);
        if (m.matches()) {
            Integer nsi = Integer.valueOf(m.group(1));
            byte[] obj = DatatypeConverter.parseBase64Binary(m.group(2));
            return new NodeId(nsi, ByteString.of(obj));
        }

        throw new IllegalArgumentException("invalid string representation of a nodeId: " + s);
    }

}
