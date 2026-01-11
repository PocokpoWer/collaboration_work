import model.MonetaryAmount;
import model.MoneyCurrency;
import model.Product;
import org.junit.jupiter.api.Test;
import validators.ProductValidator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductValidatorTest {

    @Test
    public void shouldNameWithUppercaseReturnFalse() {
        Product product = new Product(6L, "Bread", 5,
                new MonetaryAmount(new BigDecimal(500), MoneyCurrency.HUF));
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldEmptyNameReturnFalse() {
        Product product = new Product(5L, "",
                5, new MonetaryAmount(new BigDecimal("500"), MoneyCurrency.HUF));
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldInvalidStockReturnFalse() {
        Product product = new Product(4L, "apple", 0,
                new MonetaryAmount(new BigDecimal(500), MoneyCurrency.HUF));
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldInvalidPriceReturnFalse() {
        Product product = new Product(3L, "milk", 5,
                new MonetaryAmount(new BigDecimal(-100), MoneyCurrency.HUF));
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldValidProductReturnTrue() {
        Product product = new Product(1L, "cheese", 10,
                new MonetaryAmount(new BigDecimal(1020.0), MoneyCurrency.HUF)
        );
        assertTrue(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldNameWithDigitsReturnFalse() {
        Product product = new Product(2L, "cheese2", 10,
                new MonetaryAmount(new BigDecimal(1020.0), MoneyCurrency.HUF)
        );
        assertFalse(new ProductValidator().isValid(product));
    }
}