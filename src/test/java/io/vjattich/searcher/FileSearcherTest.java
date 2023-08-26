package io.vjattich.searcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class FileSearcherTest {

    @Test
    @DisplayName("Pass a full path to a class file")
    void passFullClassPath() {

        var path = Objects.requireNonNull(this.getClass().getClassLoader().getResource("sample/test1/Test.java")).getPath();

        File search = new FileSearcher(path).search();

        assertThat(search).isFile();
        assertThat(search).hasName("Test.java");
    }

    //disabled cuz I don't know how to test it correctly
//    @Test
    @DisplayName("Pass a file name that lies in running jar folder")
    void fileLiesNearRunningJar() {

        File search = new FileSearcher("classes/io/vjattich/Main.class").search();

        assertThat(search).isFile();
        assertThat(search).hasName("Main.class");
    }

}