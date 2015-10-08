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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.BrowseDirection;

@UaDataType("BrowseDescription")
public class BrowseDescription implements UaStructure {

    public static final NodeId TypeId = Identifiers.BrowseDescription;
    public static final NodeId BinaryEncodingId = Identifiers.BrowseDescription_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.BrowseDescription_Encoding_DefaultXml;

    protected final NodeId _nodeId;
    protected final BrowseDirection _browseDirection;
    protected final NodeId _referenceTypeId;
    protected final Boolean _includeSubtypes;
    protected final UInteger _nodeClassMask;
    protected final UInteger _resultMask;

    public BrowseDescription() {
        this._nodeId = null;
        this._browseDirection = null;
        this._referenceTypeId = null;
        this._includeSubtypes = null;
        this._nodeClassMask = null;
        this._resultMask = null;
    }

    public BrowseDescription(NodeId _nodeId, BrowseDirection _browseDirection, NodeId _referenceTypeId, Boolean _includeSubtypes, UInteger _nodeClassMask, UInteger _resultMask) {
        this._nodeId = _nodeId;
        this._browseDirection = _browseDirection;
        this._referenceTypeId = _referenceTypeId;
        this._includeSubtypes = _includeSubtypes;
        this._nodeClassMask = _nodeClassMask;
        this._resultMask = _resultMask;
    }

    public NodeId getNodeId() {
        return _nodeId;
    }

    public BrowseDirection getBrowseDirection() {
        return _browseDirection;
    }

    public NodeId getReferenceTypeId() {
        return _referenceTypeId;
    }

    public Boolean getIncludeSubtypes() {
        return _includeSubtypes;
    }

    public UInteger getNodeClassMask() {
        return _nodeClassMask;
    }

    public UInteger getResultMask() {
        return _resultMask;
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


    public static void encode(BrowseDescription browseDescription, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", browseDescription._nodeId);
        encoder.encodeEnumeration("BrowseDirection", browseDescription._browseDirection);
        encoder.encodeNodeId("ReferenceTypeId", browseDescription._referenceTypeId);
        encoder.encodeBoolean("IncludeSubtypes", browseDescription._includeSubtypes);
        encoder.encodeUInt32("NodeClassMask", browseDescription._nodeClassMask);
        encoder.encodeUInt32("ResultMask", browseDescription._resultMask);
    }

    public static BrowseDescription decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        BrowseDirection _browseDirection = decoder.decodeEnumeration("BrowseDirection", BrowseDirection.class);
        NodeId _referenceTypeId = decoder.decodeNodeId("ReferenceTypeId");
        Boolean _includeSubtypes = decoder.decodeBoolean("IncludeSubtypes");
        UInteger _nodeClassMask = decoder.decodeUInt32("NodeClassMask");
        UInteger _resultMask = decoder.decodeUInt32("ResultMask");

        return new BrowseDescription(_nodeId, _browseDirection, _referenceTypeId, _includeSubtypes, _nodeClassMask, _resultMask);
    }

    static {
        DelegateRegistry.registerEncoder(BrowseDescription::encode, BrowseDescription.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(BrowseDescription::decode, BrowseDescription.class, BinaryEncodingId, XmlEncodingId);
    }

}
