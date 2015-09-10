package com.digitalpetri.opcua.stack.core;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class AttributeIdTest {

    @Test
    public void testFrom() throws Exception {
        for (AttributeId attributeId : AttributeId.values()) {
            int id = attributeId.id();

            assertEquals(attributeId, AttributeId.from(id).get());
        }

        assertFalse(AttributeId.from(-1).isPresent());
        assertFalse(AttributeId.from(0).isPresent());
        assertFalse(AttributeId.from(23).isPresent());
    }

}
