package controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.Product;
import model.User;
import service.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class ChatContext {
    private final User user;
    private final List<Product> products;
    private final ShoppingCart cart;

    public BigDecimal getBalanceAmount() {
        return user.getBalance().getAmount();
    }

    public boolean hasItemsInCart() {
        return !cart.getProducts().isEmpty();
    }
}