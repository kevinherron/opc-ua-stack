package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class HistoryUpdateRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.HistoryUpdateRequest;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryUpdateRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryUpdateRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final ExtensionObject[] _historyUpdateDetails;

    public HistoryUpdateRequest(RequestHeader _requestHeader, ExtensionObject[] _historyUpdateDetails) {
        this._requestHeader = _requestHeader;
        this._historyUpdateDetails = _historyUpdateDetails;
    }

    public RequestHeader getRequestHeader() { return _requestHeader; }

    public ExtensionObject[] getHistoryUpdateDetails() { return _historyUpdateDetails; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(HistoryUpdateRequest historyUpdateRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", historyUpdateRequest._requestHeader);
        encoder.encodeArray("HistoryUpdateDetails", historyUpdateRequest._historyUpdateDetails, encoder::encodeExtensionObject);
    }

    public static HistoryUpdateRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        ExtensionObject[] _historyUpdateDetails = decoder.decodeArray("HistoryUpdateDetails", decoder::decodeExtensionObject, ExtensionObject.class);

        return new HistoryUpdateRequest(_requestHeader, _historyUpdateDetails);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryUpdateRequest::encode, HistoryUpdateRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryUpdateRequest::decode, HistoryUpdateRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
