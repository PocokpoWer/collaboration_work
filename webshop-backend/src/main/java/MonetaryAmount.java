import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MonetaryAmount {
    private double amount;
    private MoneyCurrency moneyCurrency;
}
/*
    @Override
    public double convert(double amount, MoneyCurrency fromMoneyCurrency, MoneyCurrency toMoneyCurrency) {

    }
        if (fromMoneyCurrency == MoneyCurrency.HUF && toMoneyCurrency == MoneyCurrency.EUR){return amount/
    }*/