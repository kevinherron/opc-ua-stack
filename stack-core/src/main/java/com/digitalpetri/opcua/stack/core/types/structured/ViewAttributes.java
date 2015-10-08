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
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaDataType("ViewAttributes")
public class ViewAttributes extends NodeAttributes {

    public static final NodeId TypeId = Identifiers.ViewAttributes;
    public static final NodeId BinaryEncodingId = Identifiers.ViewAttributes_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ViewAttributes_Encoding_DefaultXml;

    protected final Boolean _containsNoLoops;
    protected final UByte _eventNotifier;

    public ViewAttributes() {
        super(null, null, null, null, null);
        this._containsNoLoops = null;
        this._eventNotifier = null;
    }

    public ViewAttributes(UInteger _specifiedAttributes, LocalizedText _displayName, LocalizedText _description, UInteger _writeMask, UInteger _userWriteMask, Boolean _containsNoLoops, UByte _eventNotifier) {
        super(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask);
        this._containsNoLoops = _containsNoLoops;
        this._eventNotifier = _eventNotifier;
    }

    public Boolean getContainsNoLoops() {
        return _containsNoLoops;
    }

    public UByte getEventNotifier() {
        return _eventNotifier;
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


    public static void encode(ViewAttributes viewAttributes, UaEncoder encoder) {
        encoder.encodeUInt32("SpecifiedAttributes", viewAttributes._specifiedAttributes);
        encoder.encodeLocalizedText("DisplayName", viewAttributes._displayName);
        encoder.encodeLocalizedText("Description", viewAttributes._description);
        encoder.encodeUInt32("WriteMask", viewAttributes._writeMask);
        encoder.encodeUInt32("UserWriteMask", viewAttributes._userWriteMask);
        encoder.encodeBoolean("ContainsNoLoops", viewAttributes._containsNoLoops);
        encoder.encodeByte("EventNotifier", viewAttributes._eventNotifier);
    }

    public static ViewAttributes decode(UaDecoder decoder) {
        UInteger _specifiedAttributes = decoder.decodeUInt32("SpecifiedAttributes");
        LocalizedText _displayName = decoder.decodeLocalizedText("DisplayName");
        LocalizedText _description = decoder.decodeLocalizedText("Description");
        UInteger _writeMask = decoder.decodeUInt32("WriteMask");
        UInteger _userWriteMask = decoder.decodeUInt32("UserWriteMask");
        Boolean _containsNoLoops = decoder.decodeBoolean("ContainsNoLoops");
        UByte _eventNotifier = decoder.decodeByte("EventNotifier");

        return new ViewAttributes(_specifiedAttributes, _displayName, _description, _writeMask, _userWriteMask, _containsNoLoops, _eventNotifier);
    }

    static {
        DelegateRegistry.registerEncoder(ViewAttributes::encode, ViewAttributes.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(ViewAttributes::decode, ViewAttributes.class, BinaryEncodingId, XmlEncodingId);
    }

}
