package io.vjattich;

import io.vjattich.converter.YamlConverter;
import io.vjattich.parser.FileClassParser;
import io.vjattich.searcher.FileSearcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        //if none of files was given to program
        if (args.length == 0) {
            throw new RuntimeException("You should put an arguments to program");
        }

        convertFileToApi(new Args(args));
    }

    private static void convertFileToApi(Args arguments) {

        YamlConverter converter = new YamlConverter(
                new FileClassParser(
                        new FileSearcher(arguments.getFilePath())
                )
        );

        String convertClazz = converter.convert();

        if (arguments.hasOutput()) {

            try {
                Files.write(Paths.get(arguments.getOutput()), convertClazz.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong while writing content to file", e);
            }

        } else {
            System.out.println(convertClazz);
        }

    }

}
