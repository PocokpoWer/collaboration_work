package com.webshop.model;

public class Main {
    public static void main(String[] args) {
        User user = new User("Ákos");
        Product carrot = new Product("Carrot", 300, 5);
        Product apple = new Product("Apple", 250, 0);

        ShoppingCart cart = new ShoppingCart(user);

        cart.addProduct(carrot);
        cart.addProduct(carrot);
        cart.addProduct(apple);

        System.out.println(cart);

        cart.removeProduct(carrot);
        System.out.println(cart);
    }
}
