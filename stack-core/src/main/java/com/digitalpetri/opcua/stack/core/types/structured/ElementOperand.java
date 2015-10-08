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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ElementOperand")
public class ElementOperand extends FilterOperand {

    public static final NodeId TypeId = Identifiers.ElementOperand;
    public static final NodeId BinaryEncodingId = Identifiers.ElementOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ElementOperand_Encoding_DefaultXml;

    protected final UInteger _index;

    public ElementOperand() {
        super();
        this._index = null;
    }

    public ElementOperand(UInteger _index) {
        super();
        this._index = _index;
    }

    public UInteger getIndex() {
        return _index;
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


    public static void encode(ElementOperand elementOperand, UaEncoder encoder) {
        encoder.encodeUInt32("Index", elementOperand._index);
    }

    public static ElementOperand decode(UaDecoder decoder) {
        UInteger _index = decoder.decodeUInt32("Index");

        return new ElementOperand(_index);
    }

    static {
        DelegateRegistry.registerEncoder(ElementOperand::encode, ElementOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ElementOperand::decode, ElementOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
