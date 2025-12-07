import model.Product;

public class ProductValidator implements Validator<Product> {

    //TODO
    // product-name: only lowercase letters and spaces are allowed
    // product-price: must be positive
    // product-stock: must be a positive number

    public static boolean isValidCountry(Country country) { return country != null; }
    
    public static boolean isValidStock(int stock) {
        return stock > 0;
    }

    @Override
    public boolean isValid(Product product) {
        if (!product.getName().matches("[a-z ]+")) {
            return false;
        }
        if (product.getPrice().getAmount() < 0) {
            return false;
        }
        if (product.getStock() <= 0) {
            return false;
        }
        return true;
    }
}