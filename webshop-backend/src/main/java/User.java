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

    // FIXME: needed for the TestPayService test
    public User(int id, String name, MonetaryAmount balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }
}
