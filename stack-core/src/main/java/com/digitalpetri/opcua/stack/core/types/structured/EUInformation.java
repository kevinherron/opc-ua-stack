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
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("EUInformation")
public class EUInformation implements UaStructure {

    public static final NodeId TypeId = Identifiers.EUInformation;
    public static final NodeId BinaryEncodingId = Identifiers.EUInformation_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.EUInformation_Encoding_DefaultXml;

    protected final String _namespaceUri;
    protected final Integer _unitId;
    protected final LocalizedText _displayName;
    protected final LocalizedText _description;

    public EUInformation() {
        this._namespaceUri = null;
        this._unitId = null;
        this._displayName = null;
        this._description = null;
    }

    public EUInformation(String _namespaceUri, Integer _unitId, LocalizedText _displayName, LocalizedText _description) {
        this._namespaceUri = _namespaceUri;
        this._unitId = _unitId;
        this._displayName = _displayName;
        this._description = _description;
    }

    public String getNamespaceUri() { return _namespaceUri; }

    public Integer getUnitId() { return _unitId; }

    public LocalizedText getDisplayName() { return _displayName; }

    public LocalizedText getDescription() { return _description; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(EUInformation eUInformation, UaEncoder encoder) {
        encoder.encodeString("NamespaceUri", eUInformation._namespaceUri);
        encoder.encodeInt32("UnitId", eUInformation._unitId);
        encoder.encodeLocalizedText("DisplayName", eUInformation._displayName);
        encoder.encodeLocalizedText("Description", eUInformation._description);
    }

    public static EUInformation decode(UaDecoder decoder) {
        String _namespaceUri = decoder.decodeString("NamespaceUri");
        Integer _unitId = decoder.decodeInt32("UnitId");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");

        return new EUInformation(_namespaceUri, _unitId, _displayName, _description);
    }

    static {
        DelegateRegistry.registerEncoder(EUInformation::encode, EUInformation.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(EUInformation::decode, EUInformation.class, BinaryEncodingId, XmlEncodingId);
    }

}
