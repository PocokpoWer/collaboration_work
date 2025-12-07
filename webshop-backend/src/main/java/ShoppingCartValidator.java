import model.Product;

import java.util.List;

public class ShoppingCartValidator implements Validator<ShoppingCart> {

    //TODO
    // owner: nem lehet null és UserValidator szabályaival egyező
    // products list: nem lehet null, lehet üres, tényleges model.Product termék legyen
    // products list: nem tartalmazhat null vagy duplikált elemeket
    // totalPrice: nem lehet negatív, egyeznie kell a termékek árainak összegével

    private final UserValidator userValidator = new UserValidator();


    @Override
    public boolean isValid(ShoppingCart shoppingCart) {
        if (shoppingCart == null) {
            return false;
        }
        if (!userValidator.isValid(shoppingCart.getOwner())) {
            return false;
        }
        List<Product> products = shoppingCart.getProducts();
        if (products == null) {
            return false;
        }
        for (Product product : products) {
            if (product == null) return false;
            ProductValidator productValidator = new ProductValidator();
            if (!productValidator.isValid(product)) {
                return false;
            }
        }
        int sum = 0;
        for (Product p : products) {
            sum += (int) p.getPrice().getAmount();
        }
        if (shoppingCart.getTotalPrice() < 0) return false;
        if (shoppingCart.getTotalPrice() != sum) return false;

        return true;
    }
}
