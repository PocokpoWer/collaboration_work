import model.MonetaryAmount;
import model.MoneyCurrency;
import model.Product;
import org.junit.jupiter.api.Test;
import service.ShoppingCart;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    private static List<Product> products;
    private static int totalPrice;

    @Test
    void shouldAddProduct() throws IOException, InterruptedException {
        ShoppingCart shoppingCart = new ShoppingCart();
        Product p = Product.builder()
                .id(1)
                .name("laptop")
                .stock(1)
                .price(new MonetaryAmount(new BigDecimal(2500), MoneyCurrency.EUR))
                .build();
        assertTrue(shoppingCart.addProduct(p));
    }

    @Test
    void removeProduct() {
    }

    @Test
    void exchangeCurrency() {
    }

    @Test
    void clear() {
    }
}