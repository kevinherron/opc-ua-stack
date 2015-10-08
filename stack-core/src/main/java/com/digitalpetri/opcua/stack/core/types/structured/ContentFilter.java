/*
 * Copyright 2015 Kevin Herron
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.stack.core.types.structured;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.serialization.DelegateRegistry;
import com.digitalpetri.opcua.stack.core.serialization.UaDecoder;
import com.digitalpetri.opcua.stack.core.serialization.UaEncoder;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("ContentFilter")
public class ContentFilter implements UaStructure {

    public static final NodeId TypeId = Identifiers.ContentFilter;
    public static final NodeId BinaryEncodingId = Identifiers.ContentFilter_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ContentFilter_Encoding_DefaultXml;

    protected final ContentFilterElement[] _elements;

    public ContentFilter() {
        this._elements = null;
    }

    public ContentFilter(ContentFilterElement[] _elements) {
        this._elements = _elements;
    }

    public ContentFilterElement[] getElements() {
        return _elements;
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


    public static void encode(ContentFilter contentFilter, UaEncoder encoder) {
        encoder.encodeArray("Elements", contentFilter._elements, encoder::encodeSerializable);
    }

    public static ContentFilter decode(UaDecoder decoder) {
        ContentFilterElement[] _elements = decoder.decodeArray("Elements", decoder::decodeSerializable, ContentFilterElement.class);

        return new ContentFilter(_elements);
    }

    static {
        DelegateRegistry.registerEncoder(ContentFilter::encode, ContentFilter.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ContentFilter::decode, ContentFilter.class, BinaryEncodingId, XmlEncodingId);
    }

}
