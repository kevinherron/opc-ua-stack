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
import com.digitalpetri.opcua.stack.core.types.UaDataType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("AnonymousIdentityToken")
public class AnonymousIdentityToken extends UserIdentityToken {

    public static final NodeId TypeId = Identifiers.AnonymousIdentityToken;
    public static final NodeId BinaryEncodingId = Identifiers.AnonymousIdentityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AnonymousIdentityToken_Encoding_DefaultXml;


    public AnonymousIdentityToken() {
        super(null);
    }

    public AnonymousIdentityToken(String _policyId) {
        super(_policyId);
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


    public static void encode(AnonymousIdentityToken anonymousIdentityToken, UaEncoder encoder) {
        encoder.encodeString("PolicyId", anonymousIdentityToken._policyId);
    }

    public static AnonymousIdentityToken decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");

        return new AnonymousIdentityToken(_policyId);
    }

    static {
        DelegateRegistry.registerEncoder(AnonymousIdentityToken::encode, AnonymousIdentityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AnonymousIdentityToken::decode, AnonymousIdentityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
