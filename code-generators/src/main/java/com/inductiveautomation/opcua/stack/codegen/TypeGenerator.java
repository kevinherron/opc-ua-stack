package com.inductiveautomation.opcua.stack.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class TypeGenerator {

    public static void main(String[] args) throws JDOMException, IOException {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        Velocity.init();

        File structuredOutputDir = new File("/Users/kevin/Desktop/generated-classes/structured/");
        if (!structuredOutputDir.exists() && !structuredOutputDir.mkdirs()) {
            System.out.println("Could not create output dir: " + structuredOutputDir);
            System.exit(-1);
        }

        File enumeratedOutputDir = new File("/Users/kevin/Desktop/generated-classes/enumerated/");
        if (!enumeratedOutputDir.exists() && !enumeratedOutputDir.mkdirs()) {
            System.out.println("Could not create output dir: " + enumeratedOutputDir);
            System.exit(-1);
        }

        InputStream xmlSource = TypeGenerator.class.getClassLoader().getResourceAsStream("Opc.Ua.Types.bsd.xml");
        SAXBuilder jdomBuilder = new SAXBuilder();
        Document jdomDocument = jdomBuilder.build(xmlSource);
        Element root = jdomDocument.getRootElement();
        Namespace opcNamespace = root.getNamespace("opc");

        for (StructuredType structuredType : getStructuredTypes(root, opcNamespace)) {
            VelocityContext context = new VelocityContext();
            context.put("structuredType", structuredType);

            File f = new File(structuredOutputDir, structuredType.getName() + ".java");
            FileWriter fw = new FileWriter(f);
            Velocity.mergeTemplate("StructuredType.vm", "UTF-8", context, fw);
            fw.flush();
            fw.close();
        }

        for (EnumeratedType enumeratedType : getEnumeratedTypes(root, opcNamespace)) {
            VelocityContext context = new VelocityContext();
            context.put("enumeratedType", enumeratedType);

            File f = new File(enumeratedOutputDir, enumeratedType.getName() + ".java");
            FileWriter fw = new FileWriter(f);
            Velocity.mergeTemplate("EnumeratedType.vm", "UTF-8", context, fw);
            fw.flush();
            fw.close();
        }
    }

    private static List<StructuredType> getStructuredTypes(Element root, Namespace opcNamespace) throws JDOMException, IOException {
        List<StructuredType> structuredTypes = Lists.newArrayList();

        for (Element element : root.getChildren("StructuredType", opcNamespace)) {
            String name = element.getAttributeValue("Name");
            String baseType = element.getAttributeValue("BaseType");

            if (baseType != null) {
                List<StructuredType.Field> fields = Lists.newArrayList();
                List<Element> fieldElements = element.getChildren("Field", opcNamespace);

                for (Element fieldElement : fieldElements) {
                    String fieldName = fieldElement.getAttributeValue("Name");
                    String fieldType = fieldElement.getAttributeValue("TypeName");
                    String fieldSourceType = fieldElement.getAttributeValue("SourceType");
                    boolean array = fieldElement.getAttributeValue("LengthField") != null;

                    StructuredType.Field field = new StructuredType.Field(
                            fieldName,
                            StructuredType.FieldType.from(fieldType, array),
                            Optional.ofNullable(fieldSourceType),
                            array
                    );

                    if (!fieldName.startsWith("NoOf")) {
                        fields.add(field);
                    }
                }

                structuredTypes.add(new StructuredType(name, baseType, fields));
            }
        }

        structuredTypes.forEach(System.out::println);

        return structuredTypes;
    }

    public static List<EnumeratedType> getEnumeratedTypes(Element root, Namespace opcNamespace) {
        List<EnumeratedType> enumeratedTypes = Lists.newArrayList();

        for (Element element : root.getChildren("EnumeratedType", opcNamespace)) {
            String name = element.getAttributeValue("Name");

            List<EnumeratedType.Field> fields = Lists.newArrayList();

            for (Element fieldElement : element.getChildren("EnumeratedValue", opcNamespace)) {
                String fieldName = fieldElement.getAttributeValue("Name");
                String fieldValue = fieldElement.getAttributeValue("Value");

                EnumeratedType.Field field = new EnumeratedType.Field(fieldName, fieldValue);
                fields.add(field);
            }

            enumeratedTypes.add(new EnumeratedType(name, fields));
        }

        return enumeratedTypes;
    }

}
