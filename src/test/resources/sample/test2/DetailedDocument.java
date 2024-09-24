import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DetailsDocuments {

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