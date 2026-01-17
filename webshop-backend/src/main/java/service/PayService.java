package service;

import model.MonetaryAmount;
import model.MoneyCurrency;
import model.User;
import utils.PrintUtils;

import java.math.BigDecimal;

public class PayService {

    /**
     * Tries to pay the contents of the given shopping cart.
     * If the user's balance is enough:
     * - subtract the total price from the user's balance
     * - prints which user bought which products
     * - clears the cart
     * Otherwise :
     * - prints an error message about insufficient balance
     */

    public void pay(ShoppingCart cart) {

        //Owner Data's :

        User owner = cart.getOwner();
        if (owner == null) {
            PrintUtils.error("Error: This cart has no owner.");
        }
        MonetaryAmount balance = owner.getBalance();

        BigDecimal totalPrice = cart.getTotalPrice();
        BigDecimal balanceAmount = balance.getAmount();

        //Check : May you have enough money?

        if (balanceAmount.compareTo(totalPrice) >= 0) {
            BigDecimal newAmount = balanceAmount.subtract(totalPrice);
            MonetaryAmount newBalance = new MonetaryAmount(
                    newAmount,
                    balance.getCurrency()
            );

            owner.setBalance(newBalance);

            PrintUtils.success("Payment accepted!");
            PrintUtils.success(owner.getName() + " bought: " + cart.getProducts());
            PrintUtils.success("You are a Rich Kid" +
                    "(for now)");
            cart.clear();
        } else {
            BigDecimal missing = totalPrice.subtract(balanceAmount);
            PrintUtils.error("Error: user " + owner.getName() + " has insufficient balance. Needed: " + totalPrice +
                    ", available: " + balanceAmount);
            PrintUtils.info("You are Poor Bro :D (missing: " + missing + " " + MoneyCurrency.EUR + ")");
            PrintUtils.info("Tip: use option 10 (Add balance) to become a RichKid");
        }
    }
}