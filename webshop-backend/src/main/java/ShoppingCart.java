import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;
    private int totalPrice;
    private User owner;

    public ShoppingCart(User owner) {
        this.owner = owner;
        this.products = new ArrayList<>();
        this.totalPrice = 0;
    }

    public boolean addProduct(Product p) {
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

    public List<Product> getProducts() {
        return products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "owner=" + owner.getName() +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}
