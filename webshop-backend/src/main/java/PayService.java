import model.MonetaryAmount;

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
        MonetaryAmount balance = owner.getBalance();

        int totalPrice = cart.getTotalPrice();
        double balanceAmount = balance.getAmount();

        //Check : May you have enough money?

        if (balanceAmount >= totalPrice) {
            double newAmount = balanceAmount - totalPrice;
            MonetaryAmount newBalance = new MonetaryAmount(
                    newAmount,
                    balance.getCurrency()
            );

            owner.setBalance(newBalance);

            System.out.println(owner.getName() + " bought: " + cart.getProducts());
            cart.clear();
        } else {
            System.out.println("Error: user " + owner.getName() + "has insufficient balance. Needed: " + totalPrice + ", available "
            + balanceAmount);
        }
    }
}
