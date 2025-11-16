public class ProductValidator {

    public static boolean isValidName(String name) {
        return name.matches("[a-z ]+");
    }

    public static boolean isValidPrice(MonetaryAmount price) { return price.getAmount() > 0;
    }

    public static boolean isValidStock(int stock) {
        return stock > 0;
    }

    public static boolean isValidProduct(Product product) {
        return isValidName(product.getName()) && isValidPrice(product.getPrice()) && isValidStock(product.getStock());
    }
}