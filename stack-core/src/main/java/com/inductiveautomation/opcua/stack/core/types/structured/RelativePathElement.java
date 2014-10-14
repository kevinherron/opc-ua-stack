package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;

public class RelativePathElement implements UaStructure {

    public static final NodeId TypeId = Identifiers.RelativePathElement;
    public static final NodeId BinaryEncodingId = Identifiers.RelativePathElement_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.RelativePathElement_Encoding_DefaultXml;

    protected final NodeId _referenceTypeId;
    protected final Boolean _isInverse;
    protected final Boolean _includeSubtypes;
    protected final QualifiedName _targetName;

    public RelativePathElement(NodeId _referenceTypeId, Boolean _isInverse, Boolean _includeSubtypes, QualifiedName _targetName) {
        this._referenceTypeId = _referenceTypeId;
        this._isInverse = _isInverse;
        this._includeSubtypes = _includeSubtypes;
        this._targetName = _targetName;
    }

    public NodeId getReferenceTypeId() {
        return _referenceTypeId;
    }

    public Boolean getIsInverse() {
        return _isInverse;
    }

    public Boolean getIncludeSubtypes() {
        return _includeSubtypes;
    }

    public QualifiedName getTargetName() {
        return _targetName;
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


    public static void encode(RelativePathElement relativePathElement, UaEncoder encoder) {
        encoder.encodeNodeId("ReferenceTypeId", relativePathElement._referenceTypeId);
        encoder.encodeBoolean("IsInverse", relativePathElement._isInverse);
        encoder.encodeBoolean("IncludeSubtypes", relativePathElement._includeSubtypes);
        encoder.encodeQualifiedName("TargetName", relativePathElement._targetName);
    }

    public static RelativePathElement decode(UaDecoder decoder) {
        NodeId _referenceTypeId = decoder.decodeNodeId("ReferenceTypeId");
        Boolean _isInverse = decoder.decodeBoolean("IsInverse");
        Boolean _includeSubtypes = decoder.decodeBoolean("IncludeSubtypes");
        QualifiedName _targetName = decoder.decodeQualifiedName("TargetName");

        return new RelativePathElement(_referenceTypeId, _isInverse, _includeSubtypes, _targetName);
    }

    static {
        DelegateRegistry.registerEncoder(RelativePathElement::encode, RelativePathElement.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(RelativePathElement::decode, RelativePathElement.class, BinaryEncodingId, XmlEncodingId);
    }

}
