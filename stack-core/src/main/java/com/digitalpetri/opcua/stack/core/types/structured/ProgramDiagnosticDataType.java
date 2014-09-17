package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public class ProgramDiagnosticDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.ProgramDiagnosticDataType;
    public static final NodeId BinaryEncodingId = Identifiers.ProgramDiagnosticDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ProgramDiagnosticDataType_Encoding_DefaultXml;

    protected final NodeId _createSessionId;
    protected final String _createClientName;
    protected final DateTime _invocationCreationTime;
    protected final DateTime _lastTransitionTime;
    protected final String _lastMethodCall;
    protected final NodeId _lastMethodSessionId;
    protected final Argument[] _lastMethodInputArguments;
    protected final Argument[] _lastMethodOutputArguments;
    protected final DateTime _lastMethodCallTime;
    protected final StatusResult _lastMethodReturnStatus;

    public ProgramDiagnosticDataType(NodeId _createSessionId, String _createClientName, DateTime _invocationCreationTime, DateTime _lastTransitionTime, String _lastMethodCall, NodeId _lastMethodSessionId, Argument[] _lastMethodInputArguments, Argument[] _lastMethodOutputArguments, DateTime _lastMethodCallTime, StatusResult _lastMethodReturnStatus) {
        this._createSessionId = _createSessionId;
        this._createClientName = _createClientName;
        this._invocationCreationTime = _invocationCreationTime;
        this._lastTransitionTime = _lastTransitionTime;
        this._lastMethodCall = _lastMethodCall;
        this._lastMethodSessionId = _lastMethodSessionId;
        this._lastMethodInputArguments = _lastMethodInputArguments;
        this._lastMethodOutputArguments = _lastMethodOutputArguments;
        this._lastMethodCallTime = _lastMethodCallTime;
        this._lastMethodReturnStatus = _lastMethodReturnStatus;
    }

    public NodeId getCreateSessionId() { return _createSessionId; }

    public String getCreateClientName() { return _createClientName; }

    public DateTime getInvocationCreationTime() { return _invocationCreationTime; }

    public DateTime getLastTransitionTime() { return _lastTransitionTime; }

    public String getLastMethodCall() { return _lastMethodCall; }

    public NodeId getLastMethodSessionId() { return _lastMethodSessionId; }

    public Argument[] getLastMethodInputArguments() { return _lastMethodInputArguments; }

    public Argument[] getLastMethodOutputArguments() { return _lastMethodOutputArguments; }

    public DateTime getLastMethodCallTime() { return _lastMethodCallTime; }

    public StatusResult getLastMethodReturnStatus() { return _lastMethodReturnStatus; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(ProgramDiagnosticDataType programDiagnosticDataType, UaEncoder encoder) {
        encoder.encodeNodeId("CreateSessionId", programDiagnosticDataType._createSessionId);
        encoder.encodeString("CreateClientName", programDiagnosticDataType._createClientName);
        encoder.encodeDateTime("InvocationCreationTime", programDiagnosticDataType._invocationCreationTime);
        encoder.encodeDateTime("LastTransitionTime", programDiagnosticDataType._lastTransitionTime);
        encoder.encodeString("LastMethodCall", programDiagnosticDataType._lastMethodCall);
        encoder.encodeNodeId("LastMethodSessionId", programDiagnosticDataType._lastMethodSessionId);
        encoder.encodeArray("LastMethodInputArguments", programDiagnosticDataType._lastMethodInputArguments, encoder::encodeSerializable);
        encoder.encodeArray("LastMethodOutputArguments", programDiagnosticDataType._lastMethodOutputArguments, encoder::encodeSerializable);
        encoder.encodeDateTime("LastMethodCallTime", programDiagnosticDataType._lastMethodCallTime);
        encoder.encodeSerializable("LastMethodReturnStatus", programDiagnosticDataType._lastMethodReturnStatus);
    }

    public static ProgramDiagnosticDataType decode(UaDecoder decoder) {
        NodeId _createSessionId = decoder.decodeNodeId("CreateSessionId");
        String _createClientName = decoder.decodeString("CreateClientName");
        DateTime _invocationCreationTime = decoder.decodeDateTime("InvocationCreationTime");
        DateTime _lastTransitionTime = decoder.decodeDateTime("LastTransitionTime");
        String _lastMethodCall = decoder.decodeString("LastMethodCall");
        NodeId _lastMethodSessionId = decoder.decodeNodeId("LastMethodSessionId");
        Argument[] _lastMethodInputArguments = decoder.decodeArray("LastMethodInputArguments", decoder::decodeSerializable, Argument.class);
        Argument[] _lastMethodOutputArguments = decoder.decodeArray("LastMethodOutputArguments", decoder::decodeSerializable, Argument.class);
        DateTime _lastMethodCallTime = decoder.decodeDateTime("LastMethodCallTime");
        StatusResult _lastMethodReturnStatus = decoder.decodeSerializable("LastMethodReturnStatus", StatusResult.class);

        return new ProgramDiagnosticDataType(_createSessionId, _createClientName, _invocationCreationTime, _lastTransitionTime, _lastMethodCall, _lastMethodSessionId, _lastMethodInputArguments, _lastMethodOutputArguments, _lastMethodCallTime, _lastMethodReturnStatus);
    }

    static {
        DelegateRegistry.registerEncoder(ProgramDiagnosticDataType::encode, ProgramDiagnosticDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ProgramDiagnosticDataType::decode, ProgramDiagnosticDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
