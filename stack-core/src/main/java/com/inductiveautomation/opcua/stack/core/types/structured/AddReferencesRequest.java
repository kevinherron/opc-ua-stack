package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public class AddReferencesRequest implements UaRequestMessage {

    public static final NodeId TypeId = Identifiers.AddReferencesRequest;
    public static final NodeId BinaryEncodingId = Identifiers.AddReferencesRequest_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AddReferencesRequest_Encoding_DefaultXml;

    protected final RequestHeader _requestHeader;
    protected final AddReferencesItem[] _referencesToAdd;

    public AddReferencesRequest(RequestHeader _requestHeader, AddReferencesItem[] _referencesToAdd) {
        this._requestHeader = _requestHeader;
        this._referencesToAdd = _referencesToAdd;
    }

    public RequestHeader getRequestHeader() {
        return _requestHeader;
    }

    public AddReferencesItem[] getReferencesToAdd() {
        return _referencesToAdd;
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


    public static void encode(AddReferencesRequest addReferencesRequest, UaEncoder encoder) {
        encoder.encodeSerializable("RequestHeader", addReferencesRequest._requestHeader);
        encoder.encodeArray("ReferencesToAdd", addReferencesRequest._referencesToAdd, encoder::encodeSerializable);
    }

    public static AddReferencesRequest decode(UaDecoder decoder) {
        RequestHeader _requestHeader = decoder.decodeSerializable("RequestHeader", RequestHeader.class);
        AddReferencesItem[] _referencesToAdd = decoder.decodeArray("ReferencesToAdd", decoder::decodeSerializable, AddReferencesItem.class);

        return new AddReferencesRequest(_requestHeader, _referencesToAdd);
    }

    static {
        DelegateRegistry.registerEncoder(AddReferencesRequest::encode, AddReferencesRequest.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AddReferencesRequest::decode, AddReferencesRequest.class, BinaryEncodingId, XmlEncodingId);
    }

}
