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
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("ContentFilterResult")
public class ContentFilterResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.ContentFilterResult;
    public static final NodeId BinaryEncodingId = Identifiers.ContentFilterResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ContentFilterResult_Encoding_DefaultXml;

    protected final ContentFilterElementResult[] _elementResults;
    protected final DiagnosticInfo[] _elementDiagnosticInfos;

    public ContentFilterResult() {
        this._elementResults = null;
        this._elementDiagnosticInfos = null;
    }

    public ContentFilterResult(ContentFilterElementResult[] _elementResults, DiagnosticInfo[] _elementDiagnosticInfos) {
        this._elementResults = _elementResults;
        this._elementDiagnosticInfos = _elementDiagnosticInfos;
    }

    public ContentFilterElementResult[] getElementResults() {
        return _elementResults;
    }

    public DiagnosticInfo[] getElementDiagnosticInfos() {
        return _elementDiagnosticInfos;
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


    public static void encode(ContentFilterResult contentFilterResult, UaEncoder encoder) {
        encoder.encodeArray("ElementResults", contentFilterResult._elementResults, encoder::encodeSerializable);
        encoder.encodeArray("ElementDiagnosticInfos", contentFilterResult._elementDiagnosticInfos, encoder::encodeDiagnosticInfo);
    }

    public static ContentFilterResult decode(UaDecoder decoder) {
        ContentFilterElementResult[] _elementResults = decoder.decodeArray("ElementResults", decoder::decodeSerializable, ContentFilterElementResult.class);
        DiagnosticInfo[] _elementDiagnosticInfos = decoder.decodeArray("ElementDiagnosticInfos", decoder::decodeDiagnosticInfo, DiagnosticInfo.class);

        return new ContentFilterResult(_elementResults, _elementDiagnosticInfos);
    }

    static {
        DelegateRegistry.registerEncoder(ContentFilterResult::encode, ContentFilterResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ContentFilterResult::decode, ContentFilterResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
