package io.vjattich.parser;

import io.vjattich.cleaner.StringClassCleaner;
import io.vjattich.parser.model.ClassModel;
import io.vjattich.searcher.Searcher;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Objects;

public class FileClassParser implements ClassParser {

    private final Searcher searcher;

    public FileClassParser(Searcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public List<ClassModel> parse() {

        //try to search the files
        List<File> files = searcher.search();

        StringBuilder stringClazz = new StringBuilder(System.lineSeparator());

        for (File file : files) {
            try (FileInputStream is = new FileInputStream(file)) {
                //convert file to a string and parse
                stringClazz.append(System.lineSeparator()).append(new String(Objects.requireNonNull(is.readAllBytes())));
            } catch (Exception e) {
                throw new RuntimeException("something went wrong while class load", e);
            }
        }

        return new StringClassParser(new StringClassCleaner(stringClazz.toString()).clean()).parse();
    }

}
