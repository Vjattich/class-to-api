package io.vjattich.converter;

import io.vjattich.parser.ClassParser;
import io.vjattich.parser.model.ClassModel;
import io.vjattich.parser.model.FieldModel;

import java.util.List;

public class YamlConverter implements Converter {

    private final ClassParser parser;

    public YamlConverter(ClassParser parser) {
        this.parser = parser;
    }

    @Override
    public String convert() {

        //parse a class
        List<ClassModel> parsedClasses = parser.parse();

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < parsedClasses.size(); i++) {

            ClassModel classModel = parsedClasses.get(i);

            if (i == 0) {
                stringBuilder.append(System.lineSeparator());
            }

            stringBuilder.append(classModel.name).append(": ").append(System.lineSeparator());
            stringBuilder.append(" ").append("type").append(": ").append("object").append(System.lineSeparator());
            stringBuilder.append(" ").append("properties").append(": ").append(System.lineSeparator());

            for (FieldModel field : classModel.fields) {
                stringBuilder.append("  ").append(field.name).append(": ").append(System.lineSeparator());
                stringBuilder.append(getType(field));
            }

        }

        return stringBuilder.toString();
    }

    private String getType(FieldModel field) {

        if (field.type.equals("int") || field.type.equals("Integer")) {
            return "   " + "type" + ": " + "integer" + System.lineSeparator();
        }
        if (field.type.equals("long") || field.type.equals("Long") || field.type.equals("BigInteger")) {
            return "   " + "type" + ": " + "integer" + System.lineSeparator() +
                    "   " + "format" + ": " + "int64" + System.lineSeparator();
        }
        if (field.type.equals("BigDecimal") || field.type.equals("double") || field.type.equals("Double")) {
            return "   " + "type" + ": " + "integer" + System.lineSeparator() +
                    "   " + "format" + ": " + "int64" + System.lineSeparator();
        }
        if (field.type.equals("String")) {
            return "   " + "type" + ": " + "string" + System.lineSeparator();
        }
        if (field.type.equals("Boolean") || field.type.equals("boolean")) {
            return "   " + "type" + ": " + "boolean" + System.lineSeparator();
        }
        if (field.type.equals("Date") || field.type.equals("LocalDate")) {
            return "   " + "type" + ": " + "string" + System.lineSeparator() +
                    "   " + "format" + ": " + "date" + System.lineSeparator();
        }
        if (field.type.equals("LocalDateTime")) {
            return "   " + "type" + ": " + "string" + System.lineSeparator() +
                    "   " + "format" + ": " + "date-time" + System.lineSeparator();
        }
        if (field.type.equals("List") || field.type.equals("Set")) {
            return "   " + "type" + ": " + "array" + System.lineSeparator() +
                    "   " + "items" + ": " + System.lineSeparator() +
                    " " + getType(field.subType);
        }

        return "   " + "$ref: '#/components/schemas/" + field.type + "'" + System.lineSeparator();
    }

}
