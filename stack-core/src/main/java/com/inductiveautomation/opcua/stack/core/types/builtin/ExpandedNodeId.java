package com.inductiveautomation.opcua.stack.core.types.builtin;

import java.util.Optional;
import java.util.UUID;

import com.google.common.base.Objects;
import com.google.common.primitives.UnsignedInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;
import com.inductiveautomation.opcua.stack.core.types.enumerated.IdType;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public final class ExpandedNodeId {

    public static final ExpandedNodeId NULL_VALUE = new ExpandedNodeId(NodeId.NULL_VALUE, null, 0);

    private final NodeId nodeId;
    private final String namespaceUri;
    private final long serverIndex;

    public ExpandedNodeId(NodeId nodeId) {
        this(nodeId, null, 0);
    }

    public ExpandedNodeId(NodeId nodeId, String namespaceUri, long serverIndex) {
        this.nodeId = nodeId;
        this.namespaceUri = namespaceUri;
        this.serverIndex = serverIndex;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(UShort namespaceIndex, UInteger identifier, String namespaceUri, long serverIndex) {
        checkArgument(identifier.longValue() >= 0 && identifier.longValue() <= UnsignedInteger.MAX_VALUE.longValue());

        this.nodeId = new NodeId(namespaceIndex, identifier);
        this.namespaceUri = namespaceUri;
        this.serverIndex = serverIndex;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(int namespaceIndex, int identifier, String namespaceUri, long serverIndex) {
        this(ushort(namespaceIndex), uint(identifier), namespaceUri, serverIndex);
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(UShort namespaceIndex, String identifier, String namespaceUri, long serverIndex) {
        checkNotNull(identifier);

        this.nodeId = new NodeId(namespaceIndex, identifier);
        this.namespaceUri = namespaceUri;
        this.serverIndex = serverIndex;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(int namespaceIndex, String identifier, String namespaceUri, long serverIndex) {
        this(ushort(namespaceIndex), identifier, namespaceUri, serverIndex);
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(UShort namespaceIndex, UUID identifier, String namespaceUri, long serverIndex) {
        checkNotNull(identifier);

        this.nodeId = new NodeId(namespaceIndex, identifier);
        this.namespaceUri = namespaceUri;
        this.serverIndex = serverIndex;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(int namespaceIndex, UUID identifier, String namespaceUri, long serverIndex) {
        this(ushort(namespaceIndex), identifier, namespaceUri, serverIndex);
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(UShort namespaceIndex, ByteString identifier, String namespaceUri, long serverIndex) {
        checkNotNull(identifier);

        this.nodeId = new NodeId(namespaceIndex, identifier);
        this.namespaceUri = namespaceUri;
        this.serverIndex = serverIndex;
    }

    /**
     * @param namespaceIndex the index for a namespace URI. An index of 0 is used for OPC UA defined NodeIds.
     * @param identifier     the identifier for a node in the address space of an OPC UA Server.
     */
    public ExpandedNodeId(int namespaceIndex, ByteString identifier, String namespaceUri, long serverIndex) {
        this(ushort(namespaceIndex), identifier, namespaceUri, serverIndex);
    }

    public UShort getNamespaceIndex() {
        return nodeId.getNamespaceIndex();
    }

    public Object getIdentifier() {
        return nodeId.getIdentifier();
    }

    public IdType getType() {
        return nodeId.getType();
    }

    public String getNamespaceUri() {
        return namespaceUri;
    }

    public long getServerIndex() {
        return serverIndex;
    }

    public boolean isLocal() {
        return serverIndex == 0;
    }

    public boolean isNull() {
        return nodeId.isNull();
    }

    /**
     * If this {@link ExpandedNodeId} resides on the local server ({@code serverIndex == 0}), return its representation
     * as a local {@link NodeId}.
     *
     * @return a local {@link NodeId}, if {@code serverIndex == 0}.
     */
    public Optional<NodeId> local() {
        return isLocal() ? Optional.of(nodeId) : Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpandedNodeId that = (ExpandedNodeId) o;

        return Objects.equal(serverIndex, that.serverIndex) &&
                Objects.equal(nodeId.getIdentifier(), that.nodeId.getIdentifier()) &&
                (Objects.equal(namespaceUri, that.namespaceUri) || Objects.equal(nodeId.getNamespaceIndex(), that.nodeId.getNamespaceIndex()));
    }

    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (namespaceUri != null ? namespaceUri.hashCode() : 0);
        result = 31 * result + (int) (serverIndex ^ (serverIndex >>> 32));
        return result;
    }

    @Override
    public String toString() {
        Objects.ToStringHelper helper = Objects.toStringHelper(this);

        if (namespaceUri != null && namespaceUri.length() > 0) {
            helper.add("ns", namespaceUri);
        } else {
            helper.add("ns", getNamespaceIndex());
        }

        helper.add("id", getIdentifier());
        helper.add("serverIndex", getServerIndex());

        return helper.toString();
    }

}
