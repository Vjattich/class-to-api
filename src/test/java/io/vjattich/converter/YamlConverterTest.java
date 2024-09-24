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

        assertThat(converter.convert()).isEqualTo(System.lineSeparator() +
                "A: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  z: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator()
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

        assertThat(converter.convert()).isEqualTo(System.lineSeparator() +
                "ABC: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  a: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  b: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  c: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  d: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date" + System.lineSeparator() +
                "  e: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date" + System.lineSeparator() +
                "  f: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date-time" + System.lineSeparator() +
                "  g: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "  h: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "  i: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  j: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  k: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  l: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator()
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

        assertThat(converter.convert()).isEqualTo(System.lineSeparator() +
                "ABC: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  a: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  inner: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/Inner'" + System.lineSeparator() +
                "Inner: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  a_inn: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator()
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

        assertThat(converter.convert()).isEqualTo(System.lineSeparator() +
                "ABC: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  a: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  inner: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/Inner'" + System.lineSeparator() +
                "  listString: " + System.lineSeparator() +
                "   type: array" + System.lineSeparator() +
                "   items: " + System.lineSeparator() +
                "    type: string" + System.lineSeparator() +
                "  listInnerList: " + System.lineSeparator() +
                "   type: array" + System.lineSeparator() +
                "   items: " + System.lineSeparator() +
                "    $ref: '#/components/schemas/InnerList'" + System.lineSeparator() +
                "Inner: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  a_inn: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "InnerList: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  b_inn: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator()
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

        assertThat(converter.convert()).isEqualTo(System.lineSeparator() +
                "Test: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  molines: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/MOLines'" + System.lineSeparator() +
                "  packageDateTime: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  packageId: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "MOLines: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  moline: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/MOLine'" + System.lineSeparator() +
                "  detailsDocuments: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/DetailsDocuments'" + System.lineSeparator() +
                "  supplier: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/Supplier'" + System.lineSeparator() +
                "  specifications: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/Specifications'" + System.lineSeparator() +
                "  fz: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "DetailsDocuments: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  detailsDocument: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/DetailsDocument'" + System.lineSeparator() +
                "  stage: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/Stage'" + System.lineSeparator() +
                "  codeSubs: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  bonumber: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  advances: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/Advances'" + System.lineSeparator() +
                "  docSubject: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  kbkline: " + System.lineSeparator() +
                "   $ref: '#/components/schemas/KBKline'" + System.lineSeparator() +
                "  sumLine: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  sumRestraint: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "Advances: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  signAdvance: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  percentageAdvance: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  sumAdvance: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  codeAIP: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  sumCredit: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "DetailsDocument: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  docType: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  docNumber: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  docDate: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date" + System.lineSeparator() +
                "  docAmount: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  docID: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "KBKline: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  grbs: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  functional: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  purpose: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  expense: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  economic: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  kvfo: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  sbo: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "Stage: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  planEndDate: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date" + System.lineSeparator() +
                "  planEndSum: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  sid: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  externalSid: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "MOLine: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  pid: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  ordCount: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  monumber: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  modate: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date" + System.lineSeparator() +
                "  innOwner: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  cppOwner: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  account: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  numAgr: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  dateAgr: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "   format: date" + System.lineSeparator() +
                "  sumAgr: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  ikz: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  registryNumberEIS: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  mofio: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  signCancellation: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  oldNumber: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "Specifications: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  specification: " + System.lineSeparator() +
                "   type: array" + System.lineSeparator() +
                "   items: " + System.lineSeparator() +
                "    $ref: '#/components/schemas/Specification'" + System.lineSeparator() +
                "Specification: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  specificationId: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  sid: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  productionName: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  productionPrice: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  amount: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  summ: " + System.lineSeparator() +
                "   type: integer" + System.lineSeparator() +
                "   format: int64" + System.lineSeparator() +
                "  oksmcode: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  okeicode: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  ktrucode: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  ktruname: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "Supplier: " + System.lineSeparator() +
                " type: object" + System.lineSeparator() +
                " properties: " + System.lineSeparator() +
                "  name: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  inn: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  kpp: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  bankName: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  bik: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  korrS: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  rs: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator() +
                "  supAccount: " + System.lineSeparator() +
                "   type: string" + System.lineSeparator());
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
                  type: integer
                  format: int64
                 sumRestraint:\s
                  type: integer
                  format: int64
               Advances:\s
                type: object
                properties:\s
                 signAdvance:\s
                  type: string
                 percentageAdvance:\s
                  type: string
                 sumAdvance:\s
                  type: integer
                  format: int64
                 codeAIP:\s
                  type: string
                 sumCredit:\s
                  type: integer
                  format: int64
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
                  type: integer
                  format: int64
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
                  type: integer
                  format: int64
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
                  type: integer
                  format: int64
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
                  type: integer
                  format: int64
                 amount:\s
                  type: string
                 summ:\s
                  type: integer
                  format: int64
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
