package io.vjattich.parser;

import io.vjattich.parser.model.ClassModel;
import io.vjattich.parser.model.FieldModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StringClassParserTest {

    @Test
    @DisplayName("parse a simple class")
    void oneFieldTest() {
        StringClassParser stringClassParser = new StringClassParser("class A {private int z = 0;}");

        assertThat(stringClassParser.parse()).isEqualTo(List.of(
                new ClassModel().setName("A").setFields(List.of(new FieldModel().setName("z").setType("int")))
        ));
    }

    @Test
    @DisplayName("parse a class with different field types")
    void manyFieldsTest() {
        StringClassParser stringClassParser = new StringClassParser("""
                class ABC {
                    private String a = "0";
                    private BigDecimal b = BigDecimal.ZERO;
                    private BigInteger c = BigInteger.ZERO;
                    private Date d = new Date();
                    private LocalDate e = LocalDate.now();
                    private LocalDateTime f  = LocalDateTime.now();
                    private Integer g = 0;
                    private int h = 0;
                    private Long i = 0L;
                    private long j = 0;
                    private Double k = 0.0;
                    private double l = 0.0;
                }
                """
        );

        assertThat(stringClassParser.parse())
                .isEqualTo(
                        List.of(
                                new ClassModel()
                                        .setName("ABC")
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
                                        )
                        )
                );
    }

    @Test
    @DisplayName("parse a class with inner class in it")
    void innerClassTest() {

        StringClassParser stringClassParser = new StringClassParser("""
                  class ABC {
                     private String a = "0";
                     private Inner inner;
                     
                     
                     private static class Inner {
                        private String a_inn = "1";
                     }
                  }
                """);

        assertThat(stringClassParser.parse())
                .containsAll(
                        List.of(
                                new ClassModel()
                                        .setName("ABC")
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
                        )
                );
    }

    @Test
    @DisplayName("parse a class with link class in it")
    void linkClassTest() {

        StringClassParser stringClassParser = new StringClassParser("""
                  
                  import java.time.LocalDate;
                  
                  public class Test {
                      protected MOLines molines;
                      protected LocalDate packageDateTime;
                      protected String packageId;
                  }
                  
                  public class MOLines {
                      protected MOLine moline;
                  }
                """);

        assertThat(stringClassParser.parse())
                .containsAll(
                        List.of(
                                new ClassModel()
                                        .setName("Test")
                                        .setFields(
                                                List.of(
                                                        new FieldModel().setName("molines").setType("MOLines"),
                                                        new FieldModel().setName("packageDateTime").setType("LocalDate"),
                                                        new FieldModel().setName("packageId").setType("String")
                                                )
                                        ),
                                new ClassModel()
                                        .setName("MOLines")
                                        .setFields(
                                                List.of(new FieldModel().setName("moline").setType("MOLine"))
                                        )
                        )
                );
    }

}
