package com.digitalpetri.opcua.stack.core.serialization;

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
