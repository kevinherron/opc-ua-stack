package com.digitalpetri.opcua.stack.codegen;

import java.util.List;

public class EnumeratedType {

    private final String name;
    private final List<Field> fields;

    public EnumeratedType(String name, List<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public String getJavaName() {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public List<Field> getFields() {
        return fields;
    }

    public static class Field {
        private final String name;
        private final String value;

        public Field(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

}
