package io.vjattich;

import io.vjattich.converter.YamlConverter;
import io.vjattich.parser.FileClassParser;
import io.vjattich.searcher.FileSearcher;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new RuntimeException("You should put an arguments to program");
        }

        convertFileToApi(args[0]);
    }

    private static void convertFileToApi(String filePath) {
        System.out.println(
                new YamlConverter(
                        new FileClassParser(
                                new FileSearcher(filePath).search())
                ).convert()
        );
    }

}
