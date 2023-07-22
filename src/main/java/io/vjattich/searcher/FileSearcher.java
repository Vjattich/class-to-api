package io.vjattich.searcher;

import io.vjattich.Main;

import java.io.File;

public class FileSearcher implements Searcher {

    private final String filePath;

    public FileSearcher(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public File search() {

        File file = new File(filePath);

        if (file.exists()) {
            return file;
        }

        File jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File fileNearJar = new File(jarPath.getParentFile().getAbsolutePath() + File.separator + filePath);

        if (fileNearJar.exists()) {
            return fileNearJar;
        }

        throw new RuntimeException("File not found" + filePath);
    }
}
