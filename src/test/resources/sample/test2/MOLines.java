import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MOLines {

    protected MOLine moline;
    protected DetailsDocuments detailsDocuments;
    protected Supplier supplier;
    protected Specifications specifications;
    protected String fz;

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