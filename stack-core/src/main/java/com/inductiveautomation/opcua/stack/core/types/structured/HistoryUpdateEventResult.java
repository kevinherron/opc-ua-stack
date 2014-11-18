package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("HistoryUpdateEventResult")
public class HistoryUpdateEventResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.HistoryUpdateEventResult;
    public static final NodeId BinaryEncodingId = Identifiers.HistoryUpdateEventResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.HistoryUpdateEventResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final EventFilterResult _eventFilterResult;

    public HistoryUpdateEventResult() {
        this._statusCode = null;
        this._eventFilterResult = null;
    }

    public HistoryUpdateEventResult(StatusCode _statusCode, EventFilterResult _eventFilterResult) {
        this._statusCode = _statusCode;
        this._eventFilterResult = _eventFilterResult;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public EventFilterResult getEventFilterResult() {
        return _eventFilterResult;
    }

    @Override
    public NodeId getTypeId() {
        return TypeId;
    }

    @Override
    public NodeId getBinaryEncodingId() {
        return BinaryEncodingId;
    }

    @Override
    public NodeId getXmlEncodingId() {
        return XmlEncodingId;
    }


    public static void encode(HistoryUpdateEventResult historyUpdateEventResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", historyUpdateEventResult._statusCode);
        encoder.encodeSerializable("EventFilterResult", historyUpdateEventResult._eventFilterResult != null ? historyUpdateEventResult._eventFilterResult : new EventFilterResult());
    }

    public static HistoryUpdateEventResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        EventFilterResult _eventFilterResult = decoder.decodeSerializable("EventFilterResult", EventFilterResult.class);

        return new HistoryUpdateEventResult(_statusCode, _eventFilterResult);
    }

    static {
        DelegateRegistry.registerEncoder(HistoryUpdateEventResult::encode, HistoryUpdateEventResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(HistoryUpdateEventResult::decode, HistoryUpdateEventResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
