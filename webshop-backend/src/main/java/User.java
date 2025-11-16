import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class User {
    private int id;
    private String name;
    private String city;
    private int age;
    private Country country;
    private ShoppingCart shoppingCart;
    private MonetaryAmount balance;
}

