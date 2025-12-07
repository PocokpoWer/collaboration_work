import model.MonetaryAmount;
import model.MoneyCurrency;
import model.Product;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestProductValidator {

    @Test
    public void shouldNameWithUppercaseReturnFalse() {
        Product product = new Product(6L, "Bread",
                new MonetaryAmount(500, MoneyCurrency.HUF),
                5
        );
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldEmptyNameReturnFalse() {
        Product product = new Product(5L, "",
                new MonetaryAmount(500, MoneyCurrency.HUF),
                5
        );
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldInvalidStockReturnFalse() {
        Product product = new Product(4L, "apple",
                new MonetaryAmount(500, MoneyCurrency.HUF),
                0
        );
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldInvalidPriceReturnFalse() {
        Product product = new Product(3L, "milk",
                new MonetaryAmount(-100, MoneyCurrency.HUF),
                5
        );
        assertFalse(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldValidProductReturnTrue() {
        Product product = new Product(1L, "cheese",
                new MonetaryAmount(1020.0, MoneyCurrency.HUF),
                10
        );
        assertTrue(new ProductValidator().isValid(product));
    }

    @Test
    public void shouldNameWithDigitsReturnFalse() {
        Product product = new Product(2L, "cheese2",
                new MonetaryAmount(1020.0, MoneyCurrency.HUF),
                10
        );
        assertFalse(new ProductValidator().isValid(product));
    }
}