package converter.parser;

import io.vjattich.converter.YamlConverter;
import io.vjattich.parser.ResourcesClassParser;
import io.vjattich.parser.StringClassParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class YamlConverterTest {

    @Test
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
    void realClassTest() {

        YamlConverter converter = new YamlConverter(new ResourcesClassParser("sample/test1/Test.java"));

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


}
