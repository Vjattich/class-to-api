package io.vjattich.searcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class FileSearcherTest {

    @Test
    @DisplayName("Pass a full path to a class file")
    void passFullClassPath() {

        var path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("sample/test1/Test.java")).getPath();

        List<File> search = new FileSearcher(path).search();

        assertThat(search.get(0)).isFile();
        assertThat(search.get(0)).hasName("Test.java");
    }

    //disabled cuz I don't know how to test it correctly
//    @Test
    @DisplayName("Pass a file name that lies in running jar folder")
    void fileLiesNearRunningJar() {

        List<File> search = new FileSearcher("classes/io/vjattich/Main.class").search();
        
        assertThat(search.get(0)).isFile();
        assertThat(search.get(0)).hasName("Main.class");
    }

    @Test
    @DisplayName("Load all classes in a folder")
    void findPackOfClasses() {

        var path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("sample/test2")).getPath();

        List<File> search = new FileSearcher(path).search();

        assertThat(search).hasSize(5);
        assertThat(search.stream().map(File::getName).toList()).containsExactly("DetailedDocument.java", "MOLines.java", "Specification.java", "Specifications.java", "Test.java");
    }

}