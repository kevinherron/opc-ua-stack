package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;

public class QueryDataSet implements UaStructure {

    public static final NodeId TypeId = Identifiers.QueryDataSet;
    public static final NodeId BinaryEncodingId = Identifiers.QueryDataSet_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.QueryDataSet_Encoding_DefaultXml;

    protected final ExpandedNodeId _nodeId;
    protected final ExpandedNodeId _typeDefinitionNode;
    protected final Variant[] _values;

    public QueryDataSet(ExpandedNodeId _nodeId, ExpandedNodeId _typeDefinitionNode, Variant[] _values) {
        this._nodeId = _nodeId;
        this._typeDefinitionNode = _typeDefinitionNode;
        this._values = _values;
    }

    public ExpandedNodeId getNodeId() { return _nodeId; }

    public ExpandedNodeId getTypeDefinitionNode() { return _typeDefinitionNode; }

    public Variant[] getValues() { return _values; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(QueryDataSet queryDataSet, UaEncoder encoder) {
        encoder.encodeExpandedNodeId("NodeId", queryDataSet._nodeId);
        encoder.encodeExpandedNodeId("TypeDefinitionNode", queryDataSet._typeDefinitionNode);
        encoder.encodeArray("Values", queryDataSet._values, encoder::encodeVariant);
    }

    public static QueryDataSet decode(UaDecoder decoder) {
        ExpandedNodeId _nodeId = decoder.decodeExpandedNodeId("NodeId");
        ExpandedNodeId _typeDefinitionNode = decoder.decodeExpandedNodeId("TypeDefinitionNode");
        Variant[] _values = decoder.decodeArray("Values", decoder::decodeVariant, Variant.class);

        return new QueryDataSet(_nodeId, _typeDefinitionNode, _values);
    }

    static {
        DelegateRegistry.registerEncoder(QueryDataSet::encode, QueryDataSet.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(QueryDataSet::decode, QueryDataSet.class, BinaryEncodingId, XmlEncodingId);
    }

}
