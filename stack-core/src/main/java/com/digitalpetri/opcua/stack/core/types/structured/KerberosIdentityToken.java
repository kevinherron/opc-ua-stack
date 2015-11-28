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
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("KerberosIdentityToken")
public class KerberosIdentityToken extends UserIdentityToken {

    public static final NodeId TypeId = Identifiers.KerberosIdentityToken;
    public static final NodeId BinaryEncodingId = Identifiers.KerberosIdentityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.KerberosIdentityToken_Encoding_DefaultXml;

    protected final ByteString _ticketData;

    public KerberosIdentityToken() {
        super(null);
        this._ticketData = null;
    }

    public KerberosIdentityToken(String _policyId, ByteString _ticketData) {
        super(_policyId);
        this._ticketData = _ticketData;
    }

    public ByteString getTicketData() { return _ticketData; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(KerberosIdentityToken kerberosIdentityToken, UaEncoder encoder) {
        encoder.encodeString("PolicyId", kerberosIdentityToken._policyId);
        encoder.encodeByteString("TicketData", kerberosIdentityToken._ticketData);
    }

    public static KerberosIdentityToken decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");
        ByteString _ticketData = decoder.decodeByteString("TicketData");

        return new KerberosIdentityToken(_policyId, _ticketData);
    }

    static {
        DelegateRegistry.registerEncoder(KerberosIdentityToken::encode, KerberosIdentityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(KerberosIdentityToken::decode, KerberosIdentityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
