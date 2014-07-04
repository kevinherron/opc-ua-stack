
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class ModifyMonitoredItemsResponse implements UaResponseMessage {

	public static final NodeId TypeId = Identifiers.ModifyMonitoredItemsResponse;
	public static final NodeId BinaryEncodingId = Identifiers.ModifyMonitoredItemsResponse_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.ModifyMonitoredItemsResponse_Encoding_DefaultXml;

	protected final ResponseHeader _responseHeader;
	protected final MonitoredItemModifyResult[] _results;
	protected final DiagnosticInfo[] _diagnosticInfos;

	public ModifyMonitoredItemsResponse(ResponseHeader _responseHeader, MonitoredItemModifyResult[] _results, DiagnosticInfo[] _diagnosticInfos) {

		this._responseHeader = _responseHeader;
		this._results = _results;
		this._diagnosticInfos = _diagnosticInfos;
	}

	public ResponseHeader getResponseHeader() { return _responseHeader; }
	public MonitoredItemModifyResult[] getResults() { return _results; }
	public DiagnosticInfo[] getDiagnosticInfos() { return _diagnosticInfos; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(ModifyMonitoredItemsResponse modifyMonitoredItemsResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", modifyMonitoredItemsResponse._responseHeader);
        encoder.encodeArray("Results", modifyMonitoredItemsResponse._results, encoder::encodeSerializable);
        encoder.encodeArray("DiagnosticInfos", modifyMonitoredItemsResponse._diagnosticInfos, encoder::encodeDiagnosticInfo);
	}

	public static ModifyMonitoredItemsResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);
        MonitoredItemModifyResult[] _results = decoder.decodeArray("Results", decoder::decodeSerializable, MonitoredItemModifyResult.class);
        DiagnosticInfo[] _diagnosticInfos = decoder.decodeArray("DiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

		return new ModifyMonitoredItemsResponse(_responseHeader, _results, _diagnosticInfos);
	}

	static {
		DelegateRegistry.registerEncoder(ModifyMonitoredItemsResponse::encode, ModifyMonitoredItemsResponse.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(ModifyMonitoredItemsResponse::decode, ModifyMonitoredItemsResponse.class, BinaryEncodingId, XmlEncodingId);
	}

}
