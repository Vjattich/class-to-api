package sample;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Test {

    protected MOLines molines;
    protected String packageDateTime;
    protected String packageId;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class MOLines {

        protected MOLine moline;
        protected DetailsDocuments detailsDocuments;
        protected Supplier supplier;
        protected Specifications specifications;
        protected String fz;

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class DetailsDocuments {

            protected DetailsDocument detailsDocument;
            protected Stage stage;
            protected String codeSubs;
            protected String bonumber;
            protected Advances advances;
            protected String docSubject;
            protected KBKline kbkline;
            protected BigDecimal sumLine;
            protected BigDecimal sumRestraint;

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class Advances {

                protected String signAdvance;
                protected String percentageAdvance;
                protected BigDecimal sumAdvance;
                protected String codeAIP;
                protected BigDecimal sumCredit;

            }

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class DetailsDocument {

                protected String docType;
                protected String docNumber;
                protected LocalDate docDate;
                protected BigDecimal docAmount;
                protected String docID;

            }

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class KBKline {

                protected String grbs;
                protected String functional;
                protected String purpose;
                protected String expense;
                protected String economic;
                protected String kvfo;
                protected String sbo;

            }

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class Stage {

                protected LocalDate planEndDate;
                protected BigDecimal planEndSum;
                protected String sid;
                protected String externalSid;

            }

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class MOLine {

            protected String pid;
            protected BigInteger ordCount;
            protected String monumber;
            protected LocalDate modate;
            protected String innOwner;
            protected String cppOwner;
            protected String account;
            protected String numAgr;
            protected LocalDate dateAgr;
            protected BigDecimal sumAgr;
            protected String ikz;
            protected String registryNumberEIS;
            protected String mofio;
            protected String signCancellation;
            protected String oldNumber;

        }


        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class Specifications {


            protected List<Specification> specification;


            public List<Specification> getSpecification() {
                if (specification == null) {
                    specification = new ArrayList<Specification>();
                }
                return this.specification;
            }


            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class Specification {

                protected String specificationId;
                protected String sid;
                protected String productionName;
                protected BigDecimal productionPrice;
                protected String amount;
                protected BigDecimal summ;
                protected String oksmcode;
                protected String okeicode;
                protected String ktrucode;
                protected String ktruname;

            }

        }

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class Supplier {

            protected String name;
            protected String inn;
            protected String kpp;
            protected String bankName;
            protected String bik;
            protected String korrS;
            protected String rs;
            protected String supAccount;

        }

    }

}