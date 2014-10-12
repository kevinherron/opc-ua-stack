package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class RequestHeader implements UaStructure {

    public static final NodeId TypeId = Identifiers.RequestHeader;
    public static final NodeId BinaryEncodingId = Identifiers.RequestHeader_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RequestHeader_Encoding_DefaultXml;

    protected final NodeId _authenticationToken;
    protected final DateTime _timestamp;
    protected final Long _requestHandle;
    protected final Long _returnDiagnostics;
    protected final String _auditEntryId;
    protected final Long _timeoutHint;
    protected final ExtensionObject _additionalHeader;

    public RequestHeader(NodeId _authenticationToken, DateTime _timestamp, Long _requestHandle, Long _returnDiagnostics, String _auditEntryId, Long _timeoutHint, ExtensionObject _additionalHeader) {
        this._authenticationToken = _authenticationToken;
        this._timestamp = _timestamp;
        this._requestHandle = _requestHandle;
        this._returnDiagnostics = _returnDiagnostics;
        this._auditEntryId = _auditEntryId;
        this._timeoutHint = _timeoutHint;
        this._additionalHeader = _additionalHeader;
    }

    public NodeId getAuthenticationToken() { return _authenticationToken; }

    public DateTime getTimestamp() { return _timestamp; }

    public Long getRequestHandle() { return _requestHandle; }

    public Long getReturnDiagnostics() { return _returnDiagnostics; }

    public String getAuditEntryId() { return _auditEntryId; }

    public Long getTimeoutHint() { return _timeoutHint; }

    public ExtensionObject getAdditionalHeader() { return _additionalHeader; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(RequestHeader requestHeader, UaEncoder encoder) {
        encoder.encodeNodeId("AuthenticationToken", requestHeader._authenticationToken);
        encoder.encodeDateTime("Timestamp", requestHeader._timestamp);
        encoder.encodeUInt32("RequestHandle", requestHeader._requestHandle);
        encoder.encodeUInt32("ReturnDiagnostics", requestHeader._returnDiagnostics);
        encoder.encodeString("AuditEntryId", requestHeader._auditEntryId);
        encoder.encodeUInt32("TimeoutHint", requestHeader._timeoutHint);
        encoder.encodeExtensionObject("AdditionalHeader", requestHeader._additionalHeader);
    }

    public static RequestHeader decode(UaDecoder decoder) {
        NodeId _authenticationToken = decoder.decodeNodeId("AuthenticationToken");
        DateTime _timestamp = decoder.decodeDateTime("Timestamp");
        Long _requestHandle = decoder.decodeUInt32("RequestHandle");
        Long _returnDiagnostics = decoder.decodeUInt32("ReturnDiagnostics");
        String _auditEntryId = decoder.decodeString("AuditEntryId");
        Long _timeoutHint = decoder.decodeUInt32("TimeoutHint");
        ExtensionObject _additionalHeader = decoder.decodeExtensionObject("AdditionalHeader");

        return new RequestHeader(_authenticationToken, _timestamp, _requestHandle, _returnDiagnostics, _auditEntryId, _timeoutHint, _additionalHeader);
    }

    static {
        DelegateRegistry.registerEncoder(RequestHeader::encode, RequestHeader.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RequestHeader::decode, RequestHeader.class, BinaryEncodingId, XmlEncodingId);
    }

}
