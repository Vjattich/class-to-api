package io.vjattich.parser;

import io.vjattich.parser.model.ClassModel;
import io.vjattich.parser.model.FieldModel;
import io.vjattich.searcher.Searcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class FileClassParserTest {

    @Test
    @DisplayName("parsen a simple class")
    void parseListFilesTest() {
        //GIVEN
        FileClassParser fileClassParser = new FileClassParser(
                new Searcher() {
                    @Override
                    public List<File> search() {
                        try {
                            URL resource = Objects.requireNonNull(this.getClass().getClassLoader().getResource("sample/test3"));
                            File[] files = Objects.requireNonNull(new File(resource.toURI()).listFiles());
                            return Arrays.asList(files);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        //WHEN
        List<ClassModel> result = fileClassParser.parse();

        //THEN
        assertThat(result).isEqualTo(List.of(
                new ClassModel()
                        .setName("Test")
                        .setFields(
                                List.of(
                                        new FieldModel().setName("a").setType("String"),
                                        new FieldModel().setName("b").setType("BigDecimal"),
                                        new FieldModel().setName("c").setType("BigInteger"),
                                        new FieldModel().setName("d").setType("Date"),
                                        new FieldModel().setName("e").setType("LocalDate"),
                                        new FieldModel().setName("f").setType("LocalDateTime"),
                                        new FieldModel().setName("g").setType("Integer"),
                                        new FieldModel().setName("h").setType("int"),
                                        new FieldModel().setName("i").setType("Long"),
                                        new FieldModel().setName("j").setType("long"),
                                        new FieldModel().setName("k").setType("Double"),
                                        new FieldModel().setName("l").setType("double")
                                )
                        ),
                new ClassModel()
                        .setName("Test1")
                        .setFields(
                                List.of(
                                        new FieldModel().setName("a").setType("String"),
                                        new FieldModel().setName("inner").setType("Inner")
                                )
                        ),
                new ClassModel()
                        .setName("Inner")
                        .setFields(
                                List.of(new FieldModel().setName("a_inn").setType("String"))
                        )
        ));
    }

}
