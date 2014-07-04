
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class EventFilterResult extends MonitoringFilterResult {

	public static final NodeId TypeId = Identifiers.EventFilterResult;
	public static final NodeId BinaryEncodingId = Identifiers.EventFilterResult_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.EventFilterResult_Encoding_DefaultXml;

	protected final StatusCode[] _selectClauseResults;
	protected final DiagnosticInfo[] _selectClauseDiagnosticInfos;
	protected final ContentFilterResult _whereClauseResult;

	public EventFilterResult(StatusCode[] _selectClauseResults, DiagnosticInfo[] _selectClauseDiagnosticInfos, ContentFilterResult _whereClauseResult) {
        super();

		this._selectClauseResults = _selectClauseResults;
		this._selectClauseDiagnosticInfos = _selectClauseDiagnosticInfos;
		this._whereClauseResult = _whereClauseResult;
	}

	public StatusCode[] getSelectClauseResults() { return _selectClauseResults; }
	public DiagnosticInfo[] getSelectClauseDiagnosticInfos() { return _selectClauseDiagnosticInfos; }
	public ContentFilterResult getWhereClauseResult() { return _whereClauseResult; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(EventFilterResult eventFilterResult, UaEncoder encoder) {
        encoder.encodeArray("SelectClauseResults", eventFilterResult._selectClauseResults, encoder::encodeStatusCode);
        encoder.encodeArray("SelectClauseDiagnosticInfos", eventFilterResult._selectClauseDiagnosticInfos, encoder::encodeDiagnosticInfo);
        encoder.encodeSerializable("WhereClauseResult", eventFilterResult._whereClauseResult);
	}

	public static EventFilterResult decode(UaDecoder decoder) {
        StatusCode[] _selectClauseResults = decoder.decodeArray("SelectClauseResults", decoder::decodeStatusCode, StatusCode.class);
        DiagnosticInfo[] _selectClauseDiagnosticInfos = decoder.decodeArray("SelectClauseDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);
        ContentFilterResult _whereClauseResult = decoder.decodeSerializable("WhereClauseResult", ContentFilterResult.class);

		return new EventFilterResult(_selectClauseResults, _selectClauseDiagnosticInfos, _whereClauseResult);
	}

	static {
		DelegateRegistry.registerEncoder(EventFilterResult::encode, EventFilterResult.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(EventFilterResult::decode, EventFilterResult.class, BinaryEncodingId, XmlEncodingId);
	}

}
