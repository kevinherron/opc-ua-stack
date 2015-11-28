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

@UaDataType("SignedSoftwareCertificate")
public class SignedSoftwareCertificate implements UaStructure {

    public static final NodeId TypeId = Identifiers.SignedSoftwareCertificate;
    public static final NodeId BinaryEncodingId = Identifiers.SignedSoftwareCertificate_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.SignedSoftwareCertificate_Encoding_DefaultXml;

    protected final ByteString _certificateData;
    protected final ByteString _signature;

    public SignedSoftwareCertificate() {
        this._certificateData = null;
        this._signature = null;
    }

    public SignedSoftwareCertificate(ByteString _certificateData, ByteString _signature) {
        this._certificateData = _certificateData;
        this._signature = _signature;
    }

    public ByteString getCertificateData() { return _certificateData; }

    public ByteString getSignature() { return _signature; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(SignedSoftwareCertificate signedSoftwareCertificate, UaEncoder encoder) {
        encoder.encodeByteString("CertificateData", signedSoftwareCertificate._certificateData);
        encoder.encodeByteString("Signature", signedSoftwareCertificate._signature);
    }

    public static SignedSoftwareCertificate decode(UaDecoder decoder) {
        ByteString _certificateData = decoder.decodeByteString("CertificateData");
        ByteString _signature = decoder.decodeByteString("Signature");

        return new SignedSoftwareCertificate(_certificateData, _signature);
    }

    static {
        DelegateRegistry.registerEncoder(SignedSoftwareCertificate::encode, SignedSoftwareCertificate.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(SignedSoftwareCertificate::decode, SignedSoftwareCertificate.class, BinaryEncodingId, XmlEncodingId);
    }

}
