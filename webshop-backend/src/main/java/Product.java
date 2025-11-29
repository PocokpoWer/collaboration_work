import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Product {
    private long id;
    private String name;
    private MonetaryAmount price;
    private MoneyCurrency moneyCurrency;
    private int stock;

    public void setStock(int stock) {
        this.stock = stock;
    }
}
