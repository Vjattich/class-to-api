package io.vjattich.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import io.vjattich.parser.model.ClassModel;
import io.vjattich.parser.model.FieldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringClassParser implements ClassParser {

    private final String stringClazz;

    public StringClassParser(String stringClazz) {
        this.stringClazz = stringClazz;
    }

    @Override
    public List<ClassModel> parse() {
        return parse(StaticJavaParser.parse(stringClazz));
    }

    private List<ClassModel> parse(CompilationUnit parseResult) {

        var lst = new ArrayList<ClassModel>();

        for (ClassOrInterfaceDeclaration classDeclaration : parseResult.findAll(ClassOrInterfaceDeclaration.class)) {

            lst.add(
                    new ClassModel()
                            .setName(
                                    classDeclaration.getName().getIdentifier()
                            )
                            .setFields(
                                    classDeclaration
                                            .findAll(FieldDeclaration.class)
                                            .stream()
                                            .filter(fieldDeclaration -> Boolean.FALSE.equals(fieldDeclaration.isStatic()))
                                            .filter(fieldDeclaration -> fieldDeclaration.getParentNode().orElseThrow().equals(classDeclaration))
                                            .map(fieldDeclaration -> {

                                                VariableDeclarator variable = fieldDeclaration.getVariable(0);

                                                List<Node> childNodes = variable.getType().getChildNodes();
                                                if (childNodes.size() == 2) {
                                                    return new FieldModel()
                                                            .setName(variable.getName().getIdentifier())
                                                            .setType(childNodes.get(0).toString())
                                                            .setSubType(
                                                                    new FieldModel()
                                                                            .setName(childNodes.get(1).toString())
                                                                            .setType(childNodes.get(1).toString())
                                                            );
                                                }

                                                if (childNodes.size() == 1 || variable.getType().isPrimitiveType()) {
                                                    return new FieldModel()
                                                            .setName(variable.getName().getIdentifier())
                                                            .setType(variable.getType().asString());
                                                }

                                                return null;
                                            })
                                            .filter(Objects::nonNull)
                                            .toList()
                            )
            );

        }

        return lst;
    }

}
