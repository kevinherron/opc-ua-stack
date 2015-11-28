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

@UaDataType("DeleteAtTimeDetails")
public class DeleteAtTimeDetails extends HistoryUpdateDetails {

    public static final NodeId TypeId = Identifiers.DeleteAtTimeDetails;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteAtTimeDetails_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteAtTimeDetails_Encoding_DefaultXml;

    protected final DateTime[] _reqTimes;

    public DeleteAtTimeDetails() {
        super(null);
        this._reqTimes = null;
    }

    public DeleteAtTimeDetails(NodeId _nodeId, DateTime[] _reqTimes) {
        super(_nodeId);
        this._reqTimes = _reqTimes;
    }

    public DateTime[] getReqTimes() { return _reqTimes; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }


    public static void encode(DeleteAtTimeDetails deleteAtTimeDetails, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", deleteAtTimeDetails._nodeId);
        encoder.encodeArray("ReqTimes", deleteAtTimeDetails._reqTimes, encoder::encodeDateTime);
    }

    public static DeleteAtTimeDetails decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        DateTime[] _reqTimes = decoder.decodeArray("ReqTimes", decoder::decodeDateTime, DateTime.class);

        return new DeleteAtTimeDetails(_nodeId, _reqTimes);
    }

    static {
        DelegateRegistry.registerEncoder(DeleteAtTimeDetails::encode, DeleteAtTimeDetails.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(DeleteAtTimeDetails::decode, DeleteAtTimeDetails.class, BinaryEncodingId, XmlEncodingId);
    }

}
