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
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("TrustListDataType")
public class TrustListDataType implements UaStructure {

    public static final NodeId TypeId = Identifiers.TrustListDataType;
    public static final NodeId BinaryEncodingId = Identifiers.TrustListDataType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.TrustListDataType_Encoding_DefaultXml;

    protected final UInteger _specifiedLists;
    protected final ByteString[] _trustedCertificates;
    protected final ByteString[] _trustedCrls;
    protected final ByteString[] _issuerCertificates;
    protected final ByteString[] _issuerCrls;

    public TrustListDataType() {
        this._specifiedLists = null;
        this._trustedCertificates = null;
        this._trustedCrls = null;
        this._issuerCertificates = null;
        this._issuerCrls = null;
    }

    public TrustListDataType(UInteger _specifiedLists, ByteString[] _trustedCertificates, ByteString[] _trustedCrls, ByteString[] _issuerCertificates, ByteString[] _issuerCrls) {
        this._specifiedLists = _specifiedLists;
        this._trustedCertificates = _trustedCertificates;
        this._trustedCrls = _trustedCrls;
        this._issuerCertificates = _issuerCertificates;
        this._issuerCrls = _issuerCrls;
    }

    public UInteger getSpecifiedLists() { return _specifiedLists; }

    public ByteString[] getTrustedCertificates() { return _trustedCertificates; }

    public ByteString[] getTrustedCrls() { return _trustedCrls; }

    public ByteString[] getIssuerCertificates() { return _issuerCertificates; }

    public ByteString[] getIssuerCrls() { return _issuerCrls; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(TrustListDataType trustListDataType, UaEncoder encoder) {
        encoder.encodeUInt32("SpecifiedLists", trustListDataType._specifiedLists);
        encoder.encodeArray("TrustedCertificates", trustListDataType._trustedCertificates, encoder::encodeByteString);
        encoder.encodeArray("TrustedCrls", trustListDataType._trustedCrls, encoder::encodeByteString);
        encoder.encodeArray("IssuerCertificates", trustListDataType._issuerCertificates, encoder::encodeByteString);
        encoder.encodeArray("IssuerCrls", trustListDataType._issuerCrls, encoder::encodeByteString);
    }

    public static TrustListDataType decode(UaDecoder decoder) {
        UInteger _specifiedLists = decoder.decodeUInt32("SpecifiedLists");
        ByteString[] _trustedCertificates = decoder.decodeArray("TrustedCertificates", decoder::decodeByteString, ByteString.class);
        ByteString[] _trustedCrls = decoder.decodeArray("TrustedCrls", decoder::decodeByteString, ByteString.class);
        ByteString[] _issuerCertificates = decoder.decodeArray("IssuerCertificates", decoder::decodeByteString, ByteString.class);
        ByteString[] _issuerCrls = decoder.decodeArray("IssuerCrls", decoder::decodeByteString, ByteString.class);

        return new TrustListDataType(_specifiedLists, _trustedCertificates, _trustedCrls, _issuerCertificates, _issuerCrls);
    }

    static {
        DelegateRegistry.registerEncoder(TrustListDataType::encode, TrustListDataType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(TrustListDataType::decode, TrustListDataType.class, BinaryEncodingId, XmlEncodingId);
    }

}
