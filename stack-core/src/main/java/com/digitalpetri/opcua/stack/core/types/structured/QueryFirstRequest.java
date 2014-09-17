package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class QueryFirstRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.QueryFirstRequest;
    public static final NodeId BinaryEncodingId = Identifiers.QueryFirstRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.QueryFirstRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final ViewDescription _view;
    protected final NodeTypeDescription[] _nodeTypes;
    protected final ContentFilter _filter;
    protected final Long _maxDataSetsToReturn;
    protected final Long _maxReferencesToReturn;

    public QueryFirstRequest(RequestHeader _requestHeader, ViewDescription _view, NodeTypeDescription[] _nodeTypes, ContentFilter _filter, Long _maxDataSetsToReturn, Long _maxReferencesToReturn) {
        this._requestHeader = _requestHeader;
        this._view = _view;
        this._nodeTypes = _nodeTypes;
        this._filter = _filter;
        this._maxDataSetsToReturn = _maxDataSetsToReturn;
        this._maxReferencesToReturn = _maxReferencesToReturn;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public ViewDescription getView() { return _view; }

    public NodeTypeDescription[] getNodeTypes() { return _nodeTypes; }

    public ContentFilter getFilter() { return _filter; }

    public Long getMaxDataSetsToReturn() { return _maxDataSetsToReturn; }

    public Long getMaxReferencesToReturn() { return _maxReferencesToReturn; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(QueryFirstRequest queryFirstRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", queryFirstRequest._requestHeader);
        encoder.encodeSerializable("View", queryFirstRequest._view);
        encoder.encodeArray("NodeTypes", queryFirstRequest._nodeTypes, encoder::encodeSerializable);
        encoder.encodeSerializable("Filter", queryFirstRequest._filter);
        encoder.encodeUInt32("MaxDataSetsToReturn", queryFirstRequest._maxDataSetsToReturn);
        encoder.encodeUInt32("MaxReferencesToReturn", queryFirstRequest._maxReferencesToReturn);
    }

    public static QueryFirstRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        ViewDescription _view = decoder.decodeSerializable("View", ViewDescription.class);
        NodeTypeDescription[] _nodeTypes = decoder.decodeArray("NodeTypes", decoder::decodeSerializable, NodeTypeDescription.class);
        ContentFilter _filter = decoder.decodeSerializable("Filter", ContentFilter.class);
        Long _maxDataSetsToReturn = decoder.decodeUInt32("MaxDataSetsToReturn");
        Long _maxReferencesToReturn = decoder.decodeUInt32("MaxReferencesToReturn");

        return new QueryFirstRequest(_requestHeader, _view, _nodeTypes, _filter, _maxDataSetsToReturn, _maxReferencesToReturn);
    }

    static {
        DelegateRegistry.registerEncoder(QueryFirstRequest::encode, QueryFirstRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(QueryFirstRequest::decode, QueryFirstRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
