package service;

import utils.PrintUtils;

public class BuyProduct {
    PayService payService = new PayService();

    public void pay(ShoppingCart cart) {
        payService.pay(cart);
        PrintUtils.line();
        PrintUtils.success("Payment successful!");
    }
}