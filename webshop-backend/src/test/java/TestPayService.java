import model.MonetaryAmount;
import model.Product;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPayService{

    //  TODO: If user has enough money:
    //   - take money from user
    //   - remove products from cart
    //   - cart becomes empty
    //   - total price = 0


    //  TODO: If user does not have enough money:
    //   - do not change balance
    //   - do not change cart
    //   - total price stays same


    @Test
    public void shouldSuccessfulPayment() {
        User user = User.builder()
                .id(1)
                .name("Sanyi")
                .balance(new MonetaryAmount(1520, "EUR") )
                .build();
        ShoppingCart cart = new ShoppingCart(user);
        Product milk = new Product(20L, "milk",
                new MonetaryAmount(2, "EUR"), 10);
        cart.addProduct(milk);

        PayService payService = new PayService();
        payService.pay(cart);

        assertEquals(1518, user.getBalance().getAmount(), 0.001);
        assertTrue(cart.getProducts().isEmpty());
        assertEquals(0, cart.getTotalPrice());
    }
    @Test
    public void testInsufficientBalance() {
        User user = User.builder()
                .id(2)
                .name("Béla")
                .age(30)
                .address(new Address(Country.HUNGARY, "Budapest"))
                .balance(new MonetaryAmount(10, "EUR"))
                .build();
        ShoppingCart cart = new ShoppingCart(user);
        user.setShoppingCart(cart);

        Product product = new Product(2, "milk", new MonetaryAmount(20, "EUR"), 3);
        cart.addProduct(product);

        PayService payService = new PayService();
        payService.pay(cart);

        assertEquals(10.0, user.getBalance().getAmount(), 0.001);
        assertFalse(cart.getProducts().isEmpty());
        assertEquals(20.0, cart.getTotalPrice(), 0.001);
    }
}