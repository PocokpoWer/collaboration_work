import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ShoppingCart {
    private List<Product> products;
    private int totalPrice;
    CurrencyConverter converter;
    private User owner;

    public ShoppingCart(User owner) {
        this.owner = owner;
        this.products = new ArrayList<>();
        this.totalPrice = 0;
    }

    public boolean addProduct(Product p, User u) {
        if (p.getStock() > 0) {
            products.add(p);
            totalPrice += p.getPrice().getAmount();
            p.setStock(p.getStock() - 1);
            return true;
        } else {
            System.out.println("Product " + p.getName() + " is out of stock!");
            return false;
        }
    }

    public boolean removeProduct(Product p) {
        if (products.remove(p)) {
            totalPrice -= p.getPrice().getAmount();
            p.setStock(p.getStock() + 1);
            return true;
        } else {
            System.out.println("Product " + p.getName() + " is not in the cart!");
            return false;
        }
    }

    public void clear() {
        products.clear();
        totalPrice = 0;
    }
}
