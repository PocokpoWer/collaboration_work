public class Main {
    public static void main(String[] args) {
        Product product = new Product(2L, "kenyér", new MonetaryAmount(850.5, "HUF"), 10);
        System.out.println("Valid product: " + ProductValidator.isValidProduct(product));
        Product product2 = new Product(2L, "kenyér123", new MonetaryAmount(850.5, "HUF"), 10);
        System.out.println("Invalid product: " + ProductValidator.isValidProduct(product));
    }
}
