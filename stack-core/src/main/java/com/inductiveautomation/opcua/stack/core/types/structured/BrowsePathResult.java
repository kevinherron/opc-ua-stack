package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

public class BrowsePathResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.BrowsePathResult;
    public static final NodeId BinaryEncodingId = Identifiers.BrowsePathResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowsePathResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final BrowsePathTarget[] _targets;

    public BrowsePathResult(StatusCode _statusCode, BrowsePathTarget[] _targets) {
        this._statusCode = _statusCode;
        this._targets = _targets;
    }

    public StatusCode getStatusCode() {
        return _statusCode;
    }

    public BrowsePathTarget[] getTargets() {
        return _targets;
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


    public static void encode(BrowsePathResult browsePathResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", browsePathResult._statusCode);
        encoder.encodeArray("Targets", browsePathResult._targets, encoder::encodeSerializable);
    }

    public static BrowsePathResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        BrowsePathTarget[] _targets = decoder.decodeArray("Targets", decoder::decodeSerializable, BrowsePathTarget.class);

        return new BrowsePathResult(_statusCode, _targets);
    }

    static {
        DelegateRegistry.registerEncoder(BrowsePathResult::encode, BrowsePathResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowsePathResult::decode, BrowsePathResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
