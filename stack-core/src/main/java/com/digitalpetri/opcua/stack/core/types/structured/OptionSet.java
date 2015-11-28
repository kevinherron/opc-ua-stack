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

@UaDataType("OptionSet")
public class OptionSet implements UaStructure {

    public static final NodeId TypeId = Identifiers.OptionSet;
    public static final NodeId BinaryEncodingId = Identifiers.OptionSet_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.OptionSet_Encoding_DefaultXml;

    protected final ByteString _value;
    protected final ByteString _validBits;

    public OptionSet() {
        this._value = null;
        this._validBits = null;
    }

    public OptionSet(ByteString _value, ByteString _validBits) {
        this._value = _value;
        this._validBits = _validBits;
    }

    public ByteString getValue() { return _value; }

    public ByteString getValidBits() { return _validBits; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(OptionSet optionSet, UaEncoder encoder) {
        encoder.encodeByteString("Value", optionSet._value);
        encoder.encodeByteString("ValidBits", optionSet._validBits);
    }

    public static OptionSet decode(UaDecoder decoder) {
        ByteString _value = decoder.decodeByteString("Value");
        ByteString _validBits = decoder.decodeByteString("ValidBits");

        return new OptionSet(_value, _validBits);
    }

    static {
        DelegateRegistry.registerEncoder(OptionSet::encode, OptionSet.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(OptionSet::decode, OptionSet.class, BinaryEncodingId, XmlEncodingId);
    }

}
