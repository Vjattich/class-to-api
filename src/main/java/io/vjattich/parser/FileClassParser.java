package io.vjattich.parser;

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

        //try to search the file
        File search = searcher.search();

        try (FileInputStream is = new FileInputStream(search)) {
            //convert file to a string and parse
            return new StringClassParser(new String(Objects.requireNonNull(is.readAllBytes()))).parse();
        } catch (Exception e) {
            throw new RuntimeException("something went wrong while class load", e);
        }
    }

}
