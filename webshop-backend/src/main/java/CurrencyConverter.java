import model.MoneyCurrency;

public interface CurrencyConverter {
    double convert(double amount, MoneyCurrency fromMoneyCurrency, MoneyCurrency toMoneyCurrency);
}
