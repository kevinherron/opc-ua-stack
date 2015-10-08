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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ViewDescription")
public class ViewDescription implements UaStructure {

    public static final NodeId TypeId = Identifiers.ViewDescription;
    public static final NodeId BinaryEncodingId = Identifiers.ViewDescription_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ViewDescription_Encoding_DefaultXml;

    protected final NodeId _viewId;
    protected final DateTime _timestamp;
    protected final UInteger _viewVersion;

    public ViewDescription() {
        this._viewId = null;
        this._timestamp = null;
        this._viewVersion = null;
    }

    public ViewDescription(NodeId _viewId, DateTime _timestamp, UInteger _viewVersion) {
        this._viewId = _viewId;
        this._timestamp = _timestamp;
        this._viewVersion = _viewVersion;
    }

    public NodeId getViewId() {
        return _viewId;
    }

    public DateTime getTimestamp() {
        return _timestamp;
    }

    public UInteger getViewVersion() {
        return _viewVersion;
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


    public static void encode(ViewDescription viewDescription, UaEncoder encoder) {
        encoder.encodeNodeId("ViewId", viewDescription._viewId);
        encoder.encodeDateTime("Timestamp", viewDescription._timestamp);
        encoder.encodeUInt32("ViewVersion", viewDescription._viewVersion);
    }

    public static ViewDescription decode(UaDecoder decoder) {
        NodeId _viewId = decoder.decodeNodeId("ViewId");
        DateTime _timestamp = decoder.decodeDateTime("Timestamp");
        UInteger _viewVersion = decoder.decodeUInt32("ViewVersion");

        return new ViewDescription(_viewId, _timestamp, _viewVersion);
    }

    static {
        DelegateRegistry.registerEncoder(ViewDescription::encode, ViewDescription.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ViewDescription::decode, ViewDescription.class, BinaryEncodingId, XmlEncodingId);
    }

}
