import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestProductValidator {
    @Test
    public void ShouldNameWithUppercaseReturnFalse() {
        Product product = new Product(6L, "Bread",
                new MonetaryAmount(500, "HUF"),
                5
        );
        assertFalse(ProductValidator.isValidProduct(product));
    }

    @Test
    public void ShouldEmptyNameReturnFalse() {
        Product product = new Product(5L, "",
                new MonetaryAmount(500, "HUF"),
                5
        );
        assertFalse(ProductValidator.isValidProduct(product));
    }

    @Test
    public void ShouldInvalidStockReturnFalse() {
        Product product = new Product(4L, "apple",
                new MonetaryAmount(500, "HUF"),
                0
        );
        assertFalse(ProductValidator.isValidProduct(product));
    }
    @Test
    public void ShouldInvalidPriceReturnFalse() {
        Product product = new Product(3L, "milk",
                new MonetaryAmount(-100, "HUF"),
                5
        );
        assertFalse(ProductValidator.isValidProduct(product));
    }

    @Test
    public void shouldValidProductReturnTrue() {
        Product product = new Product(1L, "cheese",
                new MonetaryAmount(1020.0, "HUF"),
                10);
        assertTrue(ProductValidator.isValidProduct(product));
    }

    @Test
    public void shouldValidProductReturnFalse() {
        Product product = new Product(1L, "cheese2",
                new MonetaryAmount(1020.0, "HUF"),
                10);
        assertFalse(ProductValidator.isValidProduct(product));
    }
}