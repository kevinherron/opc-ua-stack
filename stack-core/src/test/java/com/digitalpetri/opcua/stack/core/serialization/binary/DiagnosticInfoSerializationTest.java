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

import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DiagnosticInfoSerializationTest extends BinarySerializationFixture {

    @DataProvider(name = "DiagnosticInfoProvider")
    public Object[][] getDiagnosticInfos() {
        return new Object[][]{
                {null},
                {new DiagnosticInfo(1, -1, -1, -1, null, null, null)},
                {new DiagnosticInfo(-1, 1, -1, -1, null, null, null)},
                {new DiagnosticInfo(-1, -1, 1, -1, null, null, null)},
                {new DiagnosticInfo(-1, -1, -1, 1, null, null, null)},
                {new DiagnosticInfo(-1, -1, -1, -1, "hello, world", null, null)},
                {new DiagnosticInfo(-1, -1, -1, -1, null, StatusCode.GOOD, null)},
                {new DiagnosticInfo(-1, -1, -1, -1, null, null, new DiagnosticInfo(1, 2, 3, 4, "abc", StatusCode.GOOD, null))},
                {new DiagnosticInfo(1, 2, 3, 4, "abc", StatusCode.GOOD, null)},
        };
    }

    @Test(dataProvider = "DiagnosticInfoProvider")
    public void testDiagnosticInfoRoundTrip(DiagnosticInfo diagnosticInfo) {
        encoder.encodeDiagnosticInfo(null, diagnosticInfo);
        DiagnosticInfo decoded = decoder.decodeDiagnosticInfo(null);

        assertEquals(decoded, diagnosticInfo);
    }

}
