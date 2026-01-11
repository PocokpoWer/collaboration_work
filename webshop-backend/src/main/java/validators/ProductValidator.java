package validators;

import model.Product;

import java.math.BigDecimal;

public class ProductValidator implements Validator<Product> {

    //TODO
    // product-name: only lowercase letters and spaces are allowed
    // product-price: must be positive
    // product-stock: must be a positive number

    @Override
    public boolean isValid(Product product) {
        if (!product.getName().matches("[a-z ]+")) {
            return false;
        }
        if (product.getPrice().getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (product.getStock() <= 0) {
            return false;
        }
        return true;
    }
}