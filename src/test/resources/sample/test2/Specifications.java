import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Specifications {

    protected List<Specification> specification;

    public List<Specification> getSpecification() {
        if (specification == null) {
            specification = new ArrayList<Specification>();
        }
        return this.specification;
    }

}