import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private int age;
    private Address address;
    private ShoppingCart shoppingCart;
    private MonetaryAmount balance;
}
