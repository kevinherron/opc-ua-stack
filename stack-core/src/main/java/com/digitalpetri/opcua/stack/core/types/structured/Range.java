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

@UaDataType("Range")
public class Range implements UaStructure {

    public static final NodeId TypeId = Identifiers.Range;
    public static final NodeId BinaryEncodingId = Identifiers.Range_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.Range_Encoding_DefaultXml;

    protected final Double _low;
    protected final Double _high;

    public Range() {
        this._low = null;
        this._high = null;
    }

    public Range(Double _low, Double _high) {
        this._low = _low;
        this._high = _high;
    }

    public Double getLow() {
        return _low;
    }

    public Double getHigh() {
        return _high;
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


    public static void encode(Range range, UaEncoder encoder) {
        encoder.encodeDouble("Low", range._low);
        encoder.encodeDouble("High", range._high);
    }

    public static Range decode(UaDecoder decoder) {
        Double _low = decoder.decodeDouble("Low");
        Double _high = decoder.decodeDouble("High");

        return new Range(_low, _high);
    }

    static {
        DelegateRegistry.registerEncoder(Range::encode, Range.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(Range::decode, Range.class, BinaryEncodingId, XmlEncodingId);
    }

}
