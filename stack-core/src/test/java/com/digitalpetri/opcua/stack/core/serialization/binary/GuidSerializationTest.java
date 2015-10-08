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

import java.util.UUID;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GuidSerializationTest extends BinarySerializationFixture {

    @DataProvider(name = "GuidProvider")
    public Object[][] getGuids() {
        return new Object[][]{
                {UUID.fromString("C496578A-0DFE-4b8f-870A-745238C6AEAE")},
                {UUID.fromString("72962B91-FA75-4ae6-8D28-B404DC7DAF63")},
                {UUID.randomUUID()},
                {UUID.randomUUID()},
                {UUID.randomUUID()},
        };
    }

    @Test(dataProvider = "GuidProvider", description = "Guid is round-trip serializable.")
    public void testGuidRoundTrip(UUID uuid) throws Exception {
        encoder.encodeGuid(null, uuid);
        UUID decoded = decoder.decodeGuid(null);

        assertEquals(decoded, uuid);
    }

}
