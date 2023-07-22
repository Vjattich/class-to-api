package io.vjattich.parser;

import io.vjattich.parser.model.ClassModel;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class ResourcesClassParser implements ClassParser {

    private final String classPath;

    public ResourcesClassParser(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public List<ClassModel> parse() {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(classPath)) {
            return new StringClassParser(new String(Objects.requireNonNull(inputStream).readAllBytes())).parse();
        } catch (Exception e) {
            throw new RuntimeException("something went wrong while class load", e);
        }
    }

}
