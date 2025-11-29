import java.util.Currency;

public interface CurrencyConverter {
    double convert(double amount, MoneyCurrency fromMoneyCurrency, MoneyCurrency toMoneyCurrency);
}
