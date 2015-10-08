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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("FilterOperand")
public class FilterOperand implements UaStructure {

    public static final NodeId TypeId = Identifiers.FilterOperand;
    public static final NodeId BinaryEncodingId = Identifiers.FilterOperand_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.FilterOperand_Encoding_DefaultXml;


    public FilterOperand() {
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


    public static void encode(FilterOperand filterOperand, UaEncoder encoder) {
    }

    public static FilterOperand decode(UaDecoder decoder) {

        return new FilterOperand();
    }

    static {
        DelegateRegistry.registerEncoder(FilterOperand::encode, FilterOperand.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(FilterOperand::decode, FilterOperand.class, BinaryEncodingId, XmlEncodingId);
    }

}
