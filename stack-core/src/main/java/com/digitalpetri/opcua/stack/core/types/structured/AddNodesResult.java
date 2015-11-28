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

@UaDataType("AddNodesResult")
public class AddNodesResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.AddNodesResult;
    public static final NodeId BinaryEncodingId = Identifiers.AddNodesResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.AddNodesResult_Encoding_DefaultXml;

    protected final StatusCode _statusCode;
    protected final NodeId _addedNodeId;

    public AddNodesResult() {
        this._statusCode = null;
        this._addedNodeId = null;
    }

    public AddNodesResult(StatusCode _statusCode, NodeId _addedNodeId) {
        this._statusCode = _statusCode;
        this._addedNodeId = _addedNodeId;
    }

    public StatusCode getStatusCode() { return _statusCode; }

    public NodeId getAddedNodeId() { return _addedNodeId; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(AddNodesResult addNodesResult, UaEncoder encoder) {
        encoder.encodeStatusCode("StatusCode", addNodesResult._statusCode);
        encoder.encodeNodeId("AddedNodeId", addNodesResult._addedNodeId);
    }

    public static AddNodesResult decode(UaDecoder decoder) {
        StatusCode _statusCode = decoder.decodeStatusCode("StatusCode");
        NodeId _addedNodeId = decoder.decodeNodeId("AddedNodeId");

        return new AddNodesResult(_statusCode, _addedNodeId);
    }

    static {
        DelegateRegistry.registerEncoder(AddNodesResult::encode, AddNodesResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(AddNodesResult::decode, AddNodesResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
