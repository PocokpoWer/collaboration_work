import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class User {
    private int id;
    private String name;
    private int age;
    private ShoppingCart shoppingCart;
    private MonetaryAmount balance;
}

