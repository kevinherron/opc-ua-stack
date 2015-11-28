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

@UaDataType("DoubleComplexNumberType")
public class DoubleComplexNumberType implements UaStructure {

    public static final NodeId TypeId = Identifiers.DoubleComplexNumberType;
    public static final NodeId BinaryEncodingId = Identifiers.DoubleComplexNumberType_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DoubleComplexNumberType_Encoding_DefaultXml;

    protected final Double _real;
    protected final Double _imaginary;

    public DoubleComplexNumberType() {
        this._real = null;
        this._imaginary = null;
    }

    public DoubleComplexNumberType(Double _real, Double _imaginary) {
        this._real = _real;
        this._imaginary = _imaginary;
    }

    public Double getReal() { return _real; }

    public Double getImaginary() { return _imaginary; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(DoubleComplexNumberType doubleComplexNumberType, UaEncoder encoder) {
        encoder.encodeDouble("Real", doubleComplexNumberType._real);
        encoder.encodeDouble("Imaginary", doubleComplexNumberType._imaginary);
    }

    public static DoubleComplexNumberType decode(UaDecoder decoder) {
        Double _real = decoder.decodeDouble("Real");
        Double _imaginary = decoder.decodeDouble("Imaginary");

        return new DoubleComplexNumberType(_real, _imaginary);
    }

    static {
        DelegateRegistry.registerEncoder(DoubleComplexNumberType::encode, DoubleComplexNumberType.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DoubleComplexNumberType::decode, DoubleComplexNumberType.class, BinaryEncodingId, XmlEncodingId);
    }

}
