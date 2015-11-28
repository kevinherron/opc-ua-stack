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
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

@UaDataType("BrowsePathResult")
public class BrowsePathResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.BrowsePathResult;
    public static final NodeId BinaryEncodingId = Identifiers.BrowsePathResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowsePathResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final BrowsePathTarget[] _targets;

    public BrowsePathResult() {
        this._statusCode = null;
        this._targets = null;
    }

    public BrowsePathResult(StatusCode _statusCode, BrowsePathTarget[] _targets) {
        this._statusCode = _statusCode;
        this._targets = _targets;
    }

    public StatusCode getStatusCode() { return _statusCode; }

    public BrowsePathTarget[] getTargets() { return _targets; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(BrowsePathResult browsePathResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", browsePathResult._statusCode);
        encoder.encodeArray("Targets", browsePathResult._targets, encoder::encodeSerializable);
    }

    public static BrowsePathResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        BrowsePathTarget[] _targets = decoder.decodeArray("Targets", decoder::decodeSerializable, BrowsePathTarget.class);

        return new BrowsePathResult(_statusCode, _targets);
    }

    static {
        DelegateRegistry.registerEncoder(BrowsePathResult::encode, BrowsePathResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowsePathResult::decode, BrowsePathResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
