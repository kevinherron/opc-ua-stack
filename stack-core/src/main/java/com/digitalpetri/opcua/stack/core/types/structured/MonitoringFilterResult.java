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

@UaDataType("MonitoringFilterResult")
public class MonitoringFilterResult implements UaStructure {

    public static final NodeId TypeId = Identifiers.MonitoringFilterResult;
    public static final NodeId BinaryEncodingId = Identifiers.MonitoringFilterResult_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.MonitoringFilterResult_Encoding_DefaultXml;


    public MonitoringFilterResult() {
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


    public static void encode(MonitoringFilterResult monitoringFilterResult, UaEncoder encoder) {
    }

    public static MonitoringFilterResult decode(UaDecoder decoder) {

        return new MonitoringFilterResult();
    }

    static {
        DelegateRegistry.registerEncoder(MonitoringFilterResult::encode, MonitoringFilterResult.class, BinaryEncodingId, XmlEncodingId);
        DelegateRegistry.registerDecoder(MonitoringFilterResult::decode, MonitoringFilterResult.class, BinaryEncodingId, XmlEncodingId);
    }

}
