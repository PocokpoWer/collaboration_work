import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderReportService {
    public Map<Product, Integer> getTotalSalesProduct(List<User> users) {
        Map<Product, Integer> sales = new HashMap<>();
        for (User user : users) {
            ShoppingCart shoppingCart = user.getShoppingCart();
            if (shoppingCart != null) {
                for (Product product : shoppingCart.getProducts()) {
                    sales.put(product, sales.getOrDefault(product, 0) + 1);
                }
            }
        }
        return sales;
    }

    public BigDecimal getTotalRevenue(List<User> users) {
        BigDecimal revenue = BigDecimal.ZERO;
        for (User user : users) {
            ShoppingCart shoppingCart = user.getShoppingCart();
            if (shoppingCart != null) {
                for (Product product : shoppingCart.getProducts()) {
                    revenue = revenue.add(BigDecimal.valueOf(product.getPrice().getAmount()));
                }
            }
        }
        return revenue;
    }

    public Map<User, Integer> getOrderCountPerUser(List<User> users) {
        Map<User, Integer> orders = new HashMap<>();
        for (User user : users) {
            ShoppingCart shoppingCart = user.getShoppingCart();
            if (shoppingCart != null && !shoppingCart.getProducts().isEmpty()) {
                orders.put(user, 1);
            } else {
                orders.put(user, 0);
            }
        }
        return orders;
    }
}
