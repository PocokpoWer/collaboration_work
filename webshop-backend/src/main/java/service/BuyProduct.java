package service;

public class BuyProduct {
    PayService payService = new PayService();

    public void pay(ShoppingCart cart) {
        payService.pay(cart);
    }
}