package com.inductiveautomation.opcua.stack.core.types.builtin;

import org.testng.annotations.Test;

public class VariantTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void variantCannotContainVariant() {
        new Variant(new Variant(null));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void variantCannotContainDataValue() {
        new Variant(new DataValue(Variant.NullValue));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void variantCannotContainDiagnosticInfo() {
        new Variant(DiagnosticInfo.NullValue);
    }

}
