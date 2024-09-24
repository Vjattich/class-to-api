import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Specification {

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