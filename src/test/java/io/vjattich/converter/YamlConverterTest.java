package io.vjattich.converter;

import io.vjattich.parser.FileClassParser;
import io.vjattich.parser.StringClassParser;
import io.vjattich.searcher.Searcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


public class YamlConverterTest {

    @Test
    @DisplayName("convert simple class")
    void oneFieldTest() {

        YamlConverter converter = new YamlConverter(new StringClassParser("class A {private int z = 0;}"));

        assertThat(converter.convert()).isEqualToIgnoringNewLines(
                "A: " +
                        " type: object" +
                        " properties: " +
                        "  z: " +
                        "   type: integer"
        );
    }

    @Test
    @DisplayName("pass a class with different types fields")
    void manyFieldsTest() {

        YamlConverter converter = new YamlConverter(new StringClassParser("""
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
                """));

        assertThat(converter.convert()).isEqualToIgnoringNewLines(
                "ABC: " +
                        " type: object" +
                        " properties: " +
                        "  a: " +
                        "   type: string" +
                        "  b: " +
                        "   type: number" +
                        "  c: " +
                        "   type: integer" +
                        "   format: int64" +
                        "  d: " +
                        "   type: string" +
                        "   format: date" +
                        "  e: " +
                        "   type: string" +
                        "   format: date" +
                        "  f: " +
                        "   type: string" +
                        "   format: date-time" +
                        "  g: " +
                        "   type: integer" +
                        "  h: " +
                        "   type: integer" +
                        "  i: " +
                        "   type: integer" +
                        "   format: int64" +
                        "  j: " +
                        "   type: integer" +
                        "   format: int64" +
                        "  k: " +
                        "   type: number" +
                        "   format: double" +
                        "  l: " +
                        "   type: number" +
                        "   format: double" + System.lineSeparator()
        );
    }

    @Test
    @DisplayName("pass a class to converter with a inner class in it")
    void referenceTest() {

        YamlConverter converter = new YamlConverter(new StringClassParser("""
                  class ABC {
                     private String a = "0";
                     private Inner inner;
                     
                     private static class Inner {
                        private String a_inn = "1";
                     }
                  }
                """));

        assertThat(converter.convert()).isEqualToIgnoringNewLines(
                "ABC: " +
                        " type: object" +
                        " properties: " +
                        "  a: " +
                        "   type: string" +
                        "  inner: " +
                        "   $ref: '#/components/schemas/Inner'" +
                        "Inner: " +
                        " type: object" +
                        " properties: " +
                        "  a_inn: " +
                        "   type: string"
        );
    }

    @Test
    @DisplayName("pass a class to converter with a list of strings and list of objects")
    void listTest() {

        YamlConverter converter = new YamlConverter(new StringClassParser("""
                  class ABC {
                     private String a = "0";
                     private Inner inner;
                     private List<String> listString = new ArrayList();
                     private List<InnerList> listInnerList = new ArrayList();
                     
                     private static class Inner {
                        private String a_inn = "1";
                     }
                     
                     private static class InnerList {
                        private String b_inn = "1";
                     }
                  }
                """));

        assertThat(converter.convert()).isEqualToIgnoringNewLines(
                "ABC: " +
                        " type: object" +
                        " properties: " +
                        "  a: " +
                        "   type: string" +
                        "  inner: " +
                        "   $ref: '#/components/schemas/Inner'" +
                        "  listString: " +
                        "   type: array" +
                        "   items: " +
                        "    type: string" +
                        "  listInnerList: " +
                        "   type: array" +
                        "   items: " +
                        "    $ref: '#/components/schemas/InnerList'" +
                        "Inner: " +
                        " type: object" +
                        " properties: " +
                        "  a_inn: " +
                        "   type: string" +
                        "InnerList: " +
                        " type: object" +
                        " properties: " +
                        "  b_inn: " +
                        "   type: string"
        );
    }

    @Test
    @DisplayName("convert real world class to api")
    void realClassTest() {

        YamlConverter converter = new YamlConverter(new FileClassParser(
                new Searcher() {
                    @Override
                    public List<File> search() {
                        try {
                            return Collections.singletonList(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("sample/test1/Test.java")).toURI()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ));

        assertThat(converter.convert()).isEqualToIgnoringNewLines(
                "Test: " +
                        " type: object" +
                        " properties: " +
                        "  molines: " +
                        "   $ref: '#/components/schemas/MOLines'" +
                        "  packageDateTime: " +
                        "   type: string" +
                        "  packageId: " +
                        "   type: string" +
                        "MOLines: " +
                        " type: object" +
                        " properties: " +
                        "  moline: " +
                        "   $ref: '#/components/schemas/MOLine'" +
                        "  detailsDocuments: " +
                        "   $ref: '#/components/schemas/DetailsDocuments'" +
                        "  supplier: " +
                        "   $ref: '#/components/schemas/Supplier'" +
                        "  specifications: " +
                        "   $ref: '#/components/schemas/Specifications'" +
                        "  fz: " +
                        "   type: string" +
                        "DetailsDocuments: " +
                        " type: object" +
                        " properties: " +
                        "  detailsDocument: " +
                        "   $ref: '#/components/schemas/DetailsDocument'" +
                        "  stage: " +
                        "   $ref: '#/components/schemas/Stage'" +
                        "  codeSubs: " +
                        "   type: string" +
                        "  bonumber: " +
                        "   type: string" +
                        "  advances: " +
                        "   $ref: '#/components/schemas/Advances'" +
                        "  docSubject: " +
                        "   type: string" +
                        "  kbkline: " +
                        "   $ref: '#/components/schemas/KBKline'" +
                        "  sumLine: " +
                        "   type: number" +
                        "  sumRestraint: " +
                        "   type: number" +
                        "Advances: " +
                        " type: object" +
                        " properties: " +
                        "  signAdvance: " +
                        "   type: string" +
                        "  percentageAdvance: " +
                        "   type: string" +
                        "  sumAdvance: " +
                        "   type: number" +
                        "  codeAIP: " +
                        "   type: string" +
                        "  sumCredit: " +
                        "   type: number" +
                        "DetailsDocument: " +
                        " type: object" +
                        " properties: " +
                        "  docType: " +
                        "   type: string" +
                        "  docNumber: " +
                        "   type: string" +
                        "  docDate: " +
                        "   type: string" +
                        "   format: date" +
                        "  docAmount: " +
                        "   type: number" +
                        "  docID: " +
                        "   type: string" +
                        "KBKline: " +
                        " type: object" +
                        " properties: " +
                        "  grbs: " +
                        "   type: string" +
                        "  functional: " +
                        "   type: string" +
                        "  purpose: " +
                        "   type: string" +
                        "  expense: " +
                        "   type: string" +
                        "  economic: " +
                        "   type: string" +
                        "  kvfo: " +
                        "   type: string" +
                        "  sbo: " +
                        "   type: string" +
                        "Stage: " +
                        " type: object" +
                        " properties: " +
                        "  planEndDate: " +
                        "   type: string" +
                        "   format: date" +
                        "  planEndSum: " +
                        "   type: number" +
                        "  sid: " +
                        "   type: string" +
                        "  externalSid: " +
                        "   type: string" +
                        "MOLine: " +
                        " type: object" +
                        " properties: " +
                        "  pid: " +
                        "   type: string" +
                        "  ordCount: " +
                        "   type: integer" +
                        "   format: int64" +
                        "  monumber: " +
                        "   type: string" +
                        "  modate: " +
                        "   type: string" +
                        "   format: date" +
                        "  innOwner: " +
                        "   type: string" +
                        "  cppOwner: " +
                        "   type: string" +
                        "  account: " +
                        "   type: string" +
                        "  numAgr: " +
                        "   type: string" +
                        "  dateAgr: " +
                        "   type: string" +
                        "   format: date" +
                        "  sumAgr: " +
                        "   type: number" +
                        "  ikz: " +
                        "   type: string" +
                        "  registryNumberEIS: " +
                        "   type: string" +
                        "  mofio: " +
                        "   type: string" +
                        "  signCancellation: " +
                        "   type: string" +
                        "  oldNumber: " +
                        "   type: string" +
                        "Specifications: " +
                        " type: object" +
                        " properties: " +
                        "  specification: " +
                        "   type: array" +
                        "   items: " +
                        "    $ref: '#/components/schemas/Specification'" +
                        "Specification: " +
                        " type: object" +
                        " properties: " +
                        "  specificationId: " +
                        "   type: string" +
                        "  sid: " +
                        "   type: string" +
                        "  productionName: " +
                        "   type: string" +
                        "  productionPrice: " +
                        "   type: number" +
                        "  amount: " +
                        "   type: string" +
                        "  summ: " +
                        "   type: number" +
                        "  oksmcode: " +
                        "   type: string" +
                        "  okeicode: " +
                        "   type: string" +
                        "  ktrucode: " +
                        "   type: string" +
                        "  ktruname: " +
                        "   type: string" +
                        "Supplier: " +
                        " type: object" +
                        " properties: " +
                        "  name: " +
                        "   type: string" +
                        "  inn: " +
                        "   type: string" +
                        "  kpp: " +
                        "   type: string" +
                        "  bankName: " +
                        "   type: string" +
                        "  bik: " +
                        "   type: string" +
                        "  korrS: " +
                        "   type: string" +
                        "  rs: " +
                        "   type: string" +
                        "  supAccount: " +
                        "   type: string"
        );
    }

    @Test
    @DisplayName("convert list of files")
    void converListFiles() {
        //GIVEN
        YamlConverter converter = new YamlConverter(
                new FileClassParser(
                        new Searcher() {
                            @Override
                            public List<File> search() {
                                try {
                                    URL resource = Objects.requireNonNull(this.getClass().getClassLoader().getResource("sample/test2"));
                                    File[] files = Objects.requireNonNull(new File(resource.toURI()).listFiles());
                                    return Arrays.asList(files);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                )
        );

        //WHEN
        String result = converter.convert();

        //THEN
        assertThat(result).isEqualToIgnoringNewLines(
                """
                                       
                        DetailsDocuments:\s
                         type: object
                         properties:\s
                          detailsDocument:\s
                           $ref: '#/components/schemas/DetailsDocument'
                          stage:\s
                           $ref: '#/components/schemas/Stage'
                          codeSubs:\s
                           type: string
                          bonumber:\s
                           type: string
                          advances:\s
                           $ref: '#/components/schemas/Advances'
                          docSubject:\s
                           type: string
                          kbkline:\s
                           $ref: '#/components/schemas/KBKline'
                          sumLine:\s
                           type: number
                          sumRestraint:\s
                           type: number
                        Advances:\s
                         type: object
                         properties:\s
                          signAdvance:\s
                           type: string
                          percentageAdvance:\s
                           type: string
                          sumAdvance:\s
                           type: number
                          codeAIP:\s
                           type: string
                          sumCredit:\s
                           type: number
                        DetailsDocument:\s
                         type: object
                         properties:\s
                          docType:\s
                           type: string
                          docNumber:\s
                           type: string
                          docDate:\s
                           type: string
                           format: date
                          docAmount:\s
                           type: number
                          docID:\s
                           type: string
                        KBKline:\s
                         type: object
                         properties:\s
                          grbs:\s
                           type: string
                          functional:\s
                           type: string
                          purpose:\s
                           type: string
                          expense:\s
                           type: string
                          economic:\s
                           type: string
                          kvfo:\s
                           type: string
                          sbo:\s
                           type: string
                        Stage:\s
                         type: object
                         properties:\s
                          planEndDate:\s
                           type: string
                           format: date
                          planEndSum:\s
                           type: number
                          sid:\s
                           type: string
                          externalSid:\s
                           type: string
                        MOLines:\s
                         type: object
                         properties:\s
                          moline:\s
                           $ref: '#/components/schemas/MOLine'
                          detailsDocuments:\s
                           $ref: '#/components/schemas/DetailsDocuments'
                          supplier:\s
                           $ref: '#/components/schemas/Supplier'
                          specifications:\s
                           $ref: '#/components/schemas/Specifications'
                          fz:\s
                           type: string
                        MOLine:\s
                         type: object
                         properties:\s
                          pid:\s
                           type: string
                          ordCount:\s
                           type: integer
                           format: int64
                          monumber:\s
                           type: string
                          modate:\s
                           type: string
                           format: date
                          innOwner:\s
                           type: string
                          cppOwner:\s
                           type: string
                          account:\s
                           type: string
                          numAgr:\s
                           type: string
                          dateAgr:\s
                           type: string
                           format: date
                          sumAgr:\s
                           type: number
                          ikz:\s
                           type: string
                          registryNumberEIS:\s
                           type: string
                          mofio:\s
                           type: string
                          signCancellation:\s
                           type: string
                          oldNumber:\s
                           type: string
                        Supplier:\s
                         type: object
                         properties:\s
                          name:\s
                           type: string
                          inn:\s
                           type: string
                          kpp:\s
                           type: string
                          bankName:\s
                           type: string
                          bik:\s
                           type: string
                          korrS:\s
                           type: string
                          rs:\s
                           type: string
                          supAccount:\s
                           type: string
                        Specification:\s
                         type: object
                         properties:\s
                          specificationId:\s
                           type: string
                          sid:\s
                           type: string
                          productionName:\s
                           type: string
                          productionPrice:\s
                           type: number
                          amount:\s
                           type: string
                          summ:\s
                           type: number
                          oksmcode:\s
                           type: string
                          okeicode:\s
                           type: string
                          ktrucode:\s
                           type: string
                          ktruname:\s
                           type: string
                        Specifications:\s
                         type: object
                         properties:\s
                          specification:\s
                           type: array
                           items:\s
                            $ref: '#/components/schemas/Specification'
                        Test:\s
                         type: object
                         properties:\s
                          molines:\s
                           $ref: '#/components/schemas/MOLines'
                          packageDateTime:\s
                           type: string
                          packageId:\s
                           type: string
                         """
        );
    }


}
