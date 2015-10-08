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

@UaDataType("UserNameIdentityToken")
public class UserNameIdentityToken extends UserIdentityToken {

    public static final NodeId TypeId = Identifiers.UserNameIdentityToken;
    public static final NodeId BinaryEncodingId = Identifiers.UserNameIdentityToken_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.UserNameIdentityToken_Encoding_DefaultXml;

    protected final String _userName;
    protected final ByteString _password;
    protected final String _encryptionAlgorithm;

    public UserNameIdentityToken() {
        super(null);
        this._userName = null;
        this._password = null;
        this._encryptionAlgorithm = null;
    }

    public UserNameIdentityToken(String _policyId, String _userName, ByteString _password, String _encryptionAlgorithm) {
        super(_policyId);
        this._userName = _userName;
        this._password = _password;
        this._encryptionAlgorithm = _encryptionAlgorithm;
    }

    public String getUserName() {
        return _userName;
    }

    public ByteString getPassword() {
        return _password;
    }

    public String getEncryptionAlgorithm() {
        return _encryptionAlgorithm;
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


    public static void encode(UserNameIdentityToken userNameIdentityToken, UaEncoder encoder) {
        encoder.encodeString("PolicyId", userNameIdentityToken._policyId);
        encoder.encodeString("UserName", userNameIdentityToken._userName);
        encoder.encodeByteString("Password", userNameIdentityToken._password);
        encoder.encodeString("EncryptionAlgorithm", userNameIdentityToken._encryptionAlgorithm);
    }

    public static UserNameIdentityToken decode(UaDecoder decoder) {
        String _policyId = decoder.decodeString("PolicyId");
        String _userName = decoder.decodeString("UserName");
        ByteString _password = decoder.decodeByteString("Password");
        String _encryptionAlgorithm = decoder.decodeString("EncryptionAlgorithm");

        return new UserNameIdentityToken(_policyId, _userName, _password, _encryptionAlgorithm);
    }

    static {
        DelegateRegistry.registerEncoder(UserNameIdentityToken::encode, UserNameIdentityToken.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(UserNameIdentityToken::decode, UserNameIdentityToken.class, BinaryEncodingId, XmlEncodingId);
    }

}
