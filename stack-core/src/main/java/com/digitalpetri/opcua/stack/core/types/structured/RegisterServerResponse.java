
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class RegisterServerResponse implements UaResponseMessage {

	public static final NodeId TypeId = Identifiers.RegisterServerResponse;
	public static final NodeId BinaryEncodingId = Identifiers.RegisterServerResponse_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.RegisterServerResponse_Encoding_DefaultXml;

	protected final ResponseHeader _responseHeader;

	public RegisterServerResponse(ResponseHeader _responseHeader) {

		this._responseHeader = _responseHeader;
	}

	public ResponseHeader getResponseHeader() { return _responseHeader; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(RegisterServerResponse registerServerResponse, UaEncoder encoder) {
        encoder.encodeSerializable("ResponseHeader", registerServerResponse._responseHeader);
	}

	public static RegisterServerResponse decode(UaDecoder decoder) {
        ResponseHeader _responseHeader = decoder.decodeSerializable("ResponseHeader", ResponseHeader.class);

		return new RegisterServerResponse(_responseHeader);
	}

	static {
		DelegateRegistry.registerEncoder(RegisterServerResponse::encode, RegisterServerResponse.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(RegisterServerResponse::decode, RegisterServerResponse.class, BinaryEncodingId, XmlEncodingId);
	}

}
