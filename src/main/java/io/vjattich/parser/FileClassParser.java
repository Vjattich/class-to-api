package io.vjattich.parser;

import io.vjattich.parser.model.ClassModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Objects;

public class FileClassParser implements ClassParser {

    private final File classFile;

    public FileClassParser(File classFile) {
        this.classFile = classFile;
    }

    @Override
    public List<ClassModel> parse() {
        try (FileInputStream is = new FileInputStream(classFile)) {
            return new StringClassParser(new String(Objects.requireNonNull(is.readAllBytes()))).parse();
        } catch (Exception e) {
            throw new RuntimeException("something went wrong while class load", e);
        }
    }

}
