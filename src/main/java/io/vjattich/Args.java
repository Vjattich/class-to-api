package io.vjattich;

import io.vjattich.argparser.ArgParser;
import io.vjattich.argparser.StringHolder;

import java.util.Objects;

public class Args {

    private final String filePath;
    private final String output;

    public Args(String[] args) {

        ArgParser argParser = new ArgParser("Class to api converter");
        StringHolder filePath = new StringHolder();
        argParser.addOption("-file %s #name of the operating file or folder", filePath);

        StringHolder outputFilePath = new StringHolder();
        argParser.addOption("-output %s #name of path were output of converting will be saved", outputFilePath);

        argParser.matchAllArgs(args);

        this.filePath = filePath.value;
        this.output = outputFilePath.value;
    }

    public String getOutput() {
        return output;
    }

    public boolean hasOutput() {
        return Objects.nonNull(output);
    }

    public String getFilePath() {
        return filePath;
    }

}
