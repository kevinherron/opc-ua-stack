package com.inductiveautomation.opcua.stack.codegen;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

public class StructuredType {

    private final String name;
    private final String baseType;
    private final List<Field> fields;

    public StructuredType(String name, String baseType, List<Field> fields) {
        this.name = name;
        this.baseType = baseType;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public String getJavaName() {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public List<Field> getAllFields() {
        return fields;
    }

    public List<Field> getInstanceFields() {
        return fields.stream().filter(f -> !f.sourceType.isPresent()).collect(Collectors.toList());
    }

    public List<Field> getInheritedFields() {
        return fields.stream().filter(f -> f.sourceType.isPresent()).collect(Collectors.toList());
    }

    public String getInheritanceBlurb() {
        boolean request = fields.stream().anyMatch(f -> f.getName().equals("RequestHeader"));
        boolean response = fields.stream().anyMatch(f -> f.getName().equals("ResponseHeader"));
        boolean subType = !"ua:ExtensionObject".equals(baseType);

        if (request) {
            return "implements UaRequestMessage";
        } else if (response) {
            return "implements UaResponseMessage";
        } else if (subType) {
            return "extends " + getBaseType();
        } else {
            return "implements UaStructure";
        }
    }

    public boolean isSubType() {
        return !"ua:ExtensionObject".equals(baseType);
    }

    public boolean isRequest() {
        return name.endsWith("Request");
    }

    public boolean isResponse() {
        return name.endsWith("Response");
    }

    public boolean isMessage() {
        return name.endsWith("Request") || name.endsWith("Response");
    }

    public String getBaseType() {
        String[] ss = baseType.split(":");
        String ns = ss[0];
        String base = ss[1];
        if ("ExtensionObject".equals(base)) {
            return "UaStructure";
        } else if ("tns".equals(ns)) {
            return base;
        } else {
            throw new RuntimeException("unknown base type: " + base);
        }
    }

    @Override
    public String toString() {
        return "StructuredType{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }

    public static class Field {
        private final String name;
        private final FieldType type;
        private final Optional<String> sourceType;
        private final boolean array;

        public Field(String name, FieldType type, Optional<String> sourceType, boolean array) {
            this.name = name;
            this.type = type;
            this.sourceType = sourceType;
            this.array = array;
        }

        public String getName() {
            return name;
        }

        public String getJavaName() {
            return "_" + name.substring(0, 1).toLowerCase() + name.substring(1);
//            return name;
        }

        public FieldType getType() {
            return type;
        }

        public Optional<String> getSourceType() {
            return sourceType;
        }

        public boolean isArray() {
            return array;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "name='" + name + '\'' +
                    ", type=" + type +
                    ", sourceType=" + sourceType +
                    ", array=" + array +
                    '}';
        }
    }

    public static class FieldType {
        private final String name;
        private final String javaClass;
        private final String typeDeclaration;
        private final boolean builtin;

        FieldType(String name, String javaClass, String typeDeclaration, boolean builtin) {
            this.name = name;
            this.javaClass = javaClass;
            this.typeDeclaration = typeDeclaration;
            this.builtin = builtin;
        }

        public String getName() {
            return name;
        }

        public String getJavaClass() {
            return javaClass;
        }

        public String getTypeDeclaration() {
            return typeDeclaration;
        }

        public boolean isBuiltin() {
            return builtin;
        }

        private static ImmutableMap<String, String> JavaTypes =
                ImmutableMap.<String, String>builder()
                        .put("Byte", "Short")
                        .put("SByte", "Byte")
                        .put("Int16", "Short")
                        .put("UInt16", "Integer")
                        .put("Int32", "Integer")
                        .put("UInt32", "Long")
                        .put("Int64", "Long")
                        .put("UInt64", "Long")
                        .put("Guid", "UUID")
                        .build();

        public static FieldType from(String fqn, boolean array) {
            String prefix = fqn.split(":")[0];
            String name = fqn.split(":")[1];

            String typeDeclaration = JavaTypes.getOrDefault(name, name);
            String javaClass = typeDeclaration;
            if (array) {
                typeDeclaration = typeDeclaration + "[]";
            }

            return new FieldType(name, javaClass, typeDeclaration, "opc".equals(prefix) || "ua".equals(prefix));
        }

        @Override
        public String toString() {
            return "FieldType{" +
                    "name='" + name + '\'' +
                    ", builtin=" + builtin +
                    '}';
        }
    }
}
