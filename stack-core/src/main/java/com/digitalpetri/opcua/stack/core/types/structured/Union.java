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

@UaDataType("Union")
public class Union implements UaStructure {

    public static final NodeId TypeId = Identifiers.Union;
    public static final NodeId BinaryEncodingId = Identifiers.Union_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.Union_Encoding_DefaultXml;


    public Union() {
    }



    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(Union union, UaEncoder encoder) {
    }

    public static Union decode(UaDecoder decoder) {

        return new Union();
    }

    static {
        DelegateRegistry.registerEncoder(Union::encode, Union.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(Union::decode, Union.class, BinaryEncodingId, XmlEncodingId);
    }

}
