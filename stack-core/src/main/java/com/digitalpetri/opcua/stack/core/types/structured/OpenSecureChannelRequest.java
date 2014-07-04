
package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;

public class OpenSecureChannelRequest implements UaRequestMessage {

	public static final NodeId TypeId = Identifiers.OpenSecureChannelRequest;
	public static final NodeId BinaryEncodingId = Identifiers.OpenSecureChannelRequest_Encoding_DefaultBinary;
	public static final NodeId XmlEncodingId = Identifiers.OpenSecureChannelRequest_Encoding_DefaultXml;

	protected final RequestHeader _requestHeader;
	protected final Long _clientProtocolVersion;
	protected final SecurityTokenRequestType _requestType;
	protected final MessageSecurityMode _securityMode;
	protected final ByteString _clientNonce;
	protected final Long _requestedLifetime;

	public OpenSecureChannelRequest(RequestHeader _requestHeader, Long _clientProtocolVersion, SecurityTokenRequestType _requestType, MessageSecurityMode _securityMode, ByteString _clientNonce, Long _requestedLifetime) {

		this._requestHeader = _requestHeader;
		this._clientProtocolVersion = _clientProtocolVersion;
		this._requestType = _requestType;
		this._securityMode = _securityMode;
		this._clientNonce = _clientNonce;
		this._requestedLifetime = _requestedLifetime;
	}

	public RequestHeader getRequestHeader() { return _requestHeader; }
	public Long getClientProtocolVersion() { return _clientProtocolVersion; }
	public SecurityTokenRequestType getRequestType() { return _requestType; }
	public MessageSecurityMode getSecurityMode() { return _securityMode; }
	public ByteString getClientNonce() { return _clientNonce; }
	public Long getRequestedLifetime() { return _requestedLifetime; }

	@Override
	public NodeId getTypeId() { return TypeId; }

	@Override
	public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

	@Override
	public NodeId getXmlEncodingId() { return XmlEncodingId; }


	public static void encode(OpenSecureChannelRequest openSecureChannelRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", openSecureChannelRequest._requestHeader);
		encoder.encodeUInt32("ClientProtocolVersion", openSecureChannelRequest._clientProtocolVersion);
        encoder.encodeSerializable("RequestType", openSecureChannelRequest._requestType);
        encoder.encodeSerializable("SecurityMode", openSecureChannelRequest._securityMode);
		encoder.encodeByteString("ClientNonce", openSecureChannelRequest._clientNonce);
		encoder.encodeUInt32("RequestedLifetime", openSecureChannelRequest._requestedLifetime);
	}

	public static OpenSecureChannelRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        Long _clientProtocolVersion = decoder.decodeUInt32("ClientProtocolVersion");
        SecurityTokenRequestType _requestType = decoder.decodeSerializable("RequestType", SecurityTokenRequestType.class);
        MessageSecurityMode _securityMode = decoder.decodeSerializable("SecurityMode", MessageSecurityMode.class);
        ByteString _clientNonce = decoder.decodeByteString("ClientNonce");
        Long _requestedLifetime = decoder.decodeUInt32("RequestedLifetime");

		return new OpenSecureChannelRequest(_requestHeader, _clientProtocolVersion, _requestType, _securityMode, _clientNonce, _requestedLifetime);
	}

	static {
		DelegateRegistry.registerEncoder(OpenSecureChannelRequest::encode, OpenSecureChannelRequest.class, BinaryEncodingId, XmlEncodingId);
		DelegateRegistry.registerDecoder(OpenSecureChannelRequest::decode, OpenSecureChannelRequest.class, BinaryEncodingId, XmlEncodingId);
	}

}
