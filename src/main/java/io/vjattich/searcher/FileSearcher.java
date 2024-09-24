package io.vjattich.searcher;

import io.vjattich.Main;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileSearcher implements Searcher {

    private final String filePath;

    public FileSearcher(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<File> search() {

        //was given a full path to the class
        File file = new File(filePath);

        //if not exists
        if (Boolean.FALSE.equals(file.exists())) {
            throw new RuntimeException("File not found" + filePath);
        }

        if (file.isFile()) {
            return Collections.singletonList(file);
        }

        if (file.isDirectory()) {
            return Arrays.asList(file.listFiles());
        }

        //if class lies near the running jar
        File jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File fileNearJar = new File(jarPath.getParentFile().getAbsolutePath() + File.separator + filePath);

        if (fileNearJar.exists()) {
            return Collections.singletonList(fileNearJar);
        }

        throw new RuntimeException("File not found" + filePath);
    }
}
