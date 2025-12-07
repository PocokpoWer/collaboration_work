import model.MonetaryAmount;

public class MonetaryAmountValidator implements Validator<MonetaryAmount> {

    @Override
    public boolean isValid(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null) return false;

        if (monetaryAmount.getAmount() <= 0) return false;

        String currency = monetaryAmount.getCurrency();
        if (currency == null) return false;

        if (!(currency.equals("HUF") || currency.equals("EUR") || currency.equals("USD"))) {
            return false;
        }

        return true;
    }
}