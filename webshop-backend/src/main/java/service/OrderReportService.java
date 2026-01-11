package service;

import model.Product;
import model.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderReportService {
    List<User> users;

    public OrderReportService(List<User> users) {
        this.users = users;
    }

    public Map<Product, Integer> getTotalSalesProduct() {
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

    public BigDecimal getTotalRevenue() {
        BigDecimal revenue = BigDecimal.ZERO;
        for (User user : users) {
            ShoppingCart shoppingCart = user.getShoppingCart();
            if (shoppingCart != null) {
                for (Product product : shoppingCart.getProducts()) {
                    revenue.add(product.getPrice().getAmount());
                }
            }
        }
        return revenue;
    }

    public Map<User, Integer> getOrderCountPerUser() {
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