
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class QueryNextRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.QueryNextRequest;
	public static final NodeId BinaryEncodingId = Identifiers.QueryNextRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.QueryNextRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final Boolean _releaseContinuationPoint;
	protected final ByteString _continuationPoint;

	public QueryNextRequest(RequestHeader _requestHeader, Boolean _releaseContinuationPoint, ByteString _continuationPoint) {

		this._requestHeader = _requestHeader;
		this._releaseContinuationPoint = _releaseContinuationPoint;
		this._continuationPoint = _continuationPoint;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public Boolean getReleaseContinuationPoint() { return _releaseContinuationPoint; }
	public ByteString getContinuationPoint() { return _continuationPoint; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(QueryNextRequest queryNextRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", queryNextRequest._requestHeader);
		encoder.encodeBoolean("ReleaseContinuationPoint", queryNextRequest._releaseContinuationPoint);
		encoder.encodeByteString("ContinuationPoint", queryNextRequest._continuationPoint);
	}

	public static QueryNextRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Boolean _releaseContinuationPoint = decoder.decodeBoolean("ReleaseContinuationPoint");
        ByteString _continuationPoint = decoder.decodeByteString("ContinuationPoint");

		return new QueryNextRequest(_requestHeader, _releaseContinuationPoint, _continuationPoint);
	}

	static {
		DelegateRegistry.registerEncoder(QueryNextRequest::encode, QueryNextRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(QueryNextRequest::decode, QueryNextRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
