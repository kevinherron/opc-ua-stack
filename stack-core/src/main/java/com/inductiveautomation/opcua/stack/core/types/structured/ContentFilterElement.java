package com.inductiveautomation.opcua.stack.core.types.structured;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.serialization.DelegateRegistry;
import com.inductiveautomation.opcua.stack.core.serialization.UaDecoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaEncoder;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.UaDataType;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.FilterOperator;

@UaDataType("ContentFilterElement")
public class ContentFilterElement implements UaStructure {

    public static final NodeId TypeId = Identifiers.ContentFilterElement;
    public static final NodeId BinaryEncodingId = Identifiers.ContentFilterElement_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ContentFilterElement_Encoding_DefaultXml;

    protected final FilterOperator _filterOperator;
    protected final ExtensionObject[] _filterOperands;

    public ContentFilterElement() {
        this._filterOperator = null;
        this._filterOperands = null;
    }

    public ContentFilterElement(FilterOperator _filterOperator, ExtensionObject[] _filterOperands) {
        this._filterOperator = _filterOperator;
        this._filterOperands = _filterOperands;
    }

    public FilterOperator getFilterOperator() {
        return _filterOperator;
    }

    public ExtensionObject[] getFilterOperands() {
        return _filterOperands;
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


    public static void encode(ContentFilterElement contentFilterElement, UaEncoder encoder) {
        encoder.encodeEnumeration("FilterOperator", contentFilterElement._filterOperator);
        encoder.encodeArray("FilterOperands", contentFilterElement._filterOperands, encoder::encodeExtensionObject);
    }

    public static ContentFilterElement decode(UaDecoder decoder) {
        FilterOperator _filterOperator = decoder.decodeEnumeration("FilterOperator", FilterOperator.class);
        ExtensionObject[] _filterOperands = decoder.decodeArray("FilterOperands", decoder::decodeExtensionObject, ExtensionObject.class);

        return new ContentFilterElement(_filterOperator, _filterOperands);
    }

    static {
        DelegateRegistry.registerEncoder(ContentFilterElement::encode, ContentFilterElement.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ContentFilterElement::decode, ContentFilterElement.class, BinaryEncodingId, XmlEncodingId);
    }

}
