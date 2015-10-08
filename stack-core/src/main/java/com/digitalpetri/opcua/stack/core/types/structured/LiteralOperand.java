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
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

@UaDataType("LiteralOperand")
public class LiteralOperand extends FilterOperand {

    public static final NodeId TypeId = Identifiers.LiteralOperand;
    public static final NodeId BinaryEncodingId = Identifiers.LiteralOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.LiteralOperand_Encoding_DefaultXml;

    protected final Variant _value;

    public LiteralOperand() {
        super();
        this._value = null;
    }

    public LiteralOperand(Variant _value) {
        super();
        this._value = _value;
    }

    public Variant getValue() {
        return _value;
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


    public static void encode(LiteralOperand literalOperand, UaEncoder encoder) {
        encoder.encodeVariant("Value", literalOperand._value);
    }

    public static LiteralOperand decode(UaDecoder decoder) {
        Variant _value = decoder.decodeVariant("Value");

        return new LiteralOperand(_value);
    }

    static {
        DelegateRegistry.registerEncoder(LiteralOperand::encode, LiteralOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(LiteralOperand::decode, LiteralOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
