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

package com.digitalpetri.opcua.stack.core.serialization.binary;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.XmlElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExtensionObjectSerializationTest extends BinarySerializationFixture {

    @DataProvider
    public Object[][] getExtensionObjects() {
        return new Object[][]{
                {new ExtensionObject(ByteString.of(new byte[]{1, 2, 3, 4}), new NodeId(1, 2))},
                {new ExtensionObject(XmlElement.of("<a>hello</a>"), new NodeId(1, 2))},
        };
    }

    @Test(dataProvider = "getExtensionObjects", description = "ExtensionObject is round-trip serializable.")
    public void testExtensionObjectRoundTrip(ExtensionObject xo) throws Exception {
        encoder.encodeExtensionObject(null, xo);
        ExtensionObject decoded = decoder.decodeExtensionObject(null);

        assertEquals(decoded, xo);
    }

}
