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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

@UaDataType("ReadAtTimeDetails")
public class ReadAtTimeDetails extends HistoryReadDetails {

    public static final NodeId TypeId = Identifiers.ReadAtTimeDetails;
    public static final NodeId BinaryEncodingId = Identifiers.ReadAtTimeDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ReadAtTimeDetails_Encoding_DefaultXml;

    protected final DateTime[] _reqTimes;
    protected final Boolean _useSimpleBounds;

    public ReadAtTimeDetails() {
        super();
        this._reqTimes = null;
        this._useSimpleBounds = null;
    }

    public ReadAtTimeDetails(DateTime[] _reqTimes, Boolean _useSimpleBounds) {
        super();
        this._reqTimes = _reqTimes;
        this._useSimpleBounds = _useSimpleBounds;
    }

    public DateTime[] getReqTimes() {
        return _reqTimes;
    }

    public Boolean getUseSimpleBounds() {
        return _useSimpleBounds;
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


    public static void encode(ReadAtTimeDetails readAtTimeDetails, UaEncoder encoder) {
        encoder.encodeArray("ReqTimes", readAtTimeDetails._reqTimes, encoder::encodeDateTime);
        encoder.encodeBoolean("UseSimpleBounds", readAtTimeDetails._useSimpleBounds);
    }

    public static ReadAtTimeDetails decode(UaDecoder decoder) {
        DateTime[] _reqTimes = decoder.decodeArray("ReqTimes", decoder::decodeDateTime, DateTime.class);
        Boolean _useSimpleBounds = decoder.decodeBoolean("UseSimpleBounds");

        return new ReadAtTimeDetails(_reqTimes, _useSimpleBounds);
    }

    static {
        DelegateRegistry.registerEncoder(ReadAtTimeDetails::encode, ReadAtTimeDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ReadAtTimeDetails::decode, ReadAtTimeDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
