package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString

public class MonetaryAmount {
    private double amount;
    private MoneyCurrency currency;

    public double convert(double amount, MoneyCurrency fromCurrency, MoneyCurrency toCurrency) {
        if (fromCurrency == MoneyCurrency.HUF && toCurrency == MoneyCurrency.EUR) {
            return amount / 380;
        }
        if (fromCurrency == MoneyCurrency.HUF && toCurrency == MoneyCurrency.USD) {
            return amount / 350;
        }
        if (fromCurrency == MoneyCurrency.USD && toCurrency == MoneyCurrency.EUR) {
            return amount / 0.86;
        }
        if (fromCurrency == MoneyCurrency.EUR && toCurrency == MoneyCurrency.USD) {
            return amount / 1.16;
        }
        return amount;
    }
}