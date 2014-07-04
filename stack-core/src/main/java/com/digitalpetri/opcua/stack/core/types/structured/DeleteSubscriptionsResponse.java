
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class DeleteSubscriptionsResponse implements UaResponseMessage {

	public static final NodeId TypeId = Identifiers.DeleteSubscriptionsResponse;
	public static final NodeId BinaryEncodingId = Identifiers.DeleteSubscriptionsResponse_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.DeleteSubscriptionsResponse_Encoding_DefaultXml;

	protected final ResponseHeader _responseHeader;
	protected final StatusCode[] _results;
	protected final DiagnosticInfo[] _diagnosticInfos;

	public DeleteSubscriptionsResponse(ResponseHeader _responseHeader, StatusCode[] _results, DiagnosticInfo[] _diagnosticInfos) {

		this._responseHeader = _responseHeader;
		this._results = _results;
		this._diagnosticInfos = _diagnosticInfos;
	}

	public ResponseHeader getResponseHeader() { return _responseHeader; }
	public StatusCode[] getResults() { return _results; }
	public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(DeleteSubscriptionsResponse deleteSubscriptionsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", deleteSubscriptionsResponse._responseHeader);
        encoder.encodeArray("Results", deleteSubscriptionsResponse._results, encoder::encodeStatusCode);
        encoder.encodeArray("DiagnosticInfos", deleteSubscriptionsResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
	}

	public static DeleteSubscriptionsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        StatusCode[] _results = decoder.decodeArray("Results", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

		return new DeleteSubscriptionsResponse(_responseHeader, _results, _diagnosticInfos);
	}

	static {
		DelegateRegistry.registerEncoder(DeleteSubscriptionsResponse::encode, DeleteSubscriptionsResponse.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(DeleteSubscriptionsResponse::decode, DeleteSubscriptionsResponse.class, BinaryEncodingId, XmlEncodingId);
	}

}
