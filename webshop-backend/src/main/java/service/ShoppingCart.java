package service;

import jakarta.persistence.*;
import lombok.*;
import model.MoneyCurrency;
import model.Product;
import model.User;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString

public class ShoppingCart {
    private List<Product> products = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @ToString.Exclude
    private User owner;

    public boolean addProduct(Product p) throws IOException, InterruptedException {
        if (p.getStock() > 0) {
            products.add(p);
            totalPrice = totalPrice.add(exchangeCurrency(p.getPrice().getAmount(), p.getPrice().getCurrency(), MoneyCurrency.EUR));
            p.setStock(p.getStock() - 1);
            return true;
        } else {
            System.out.println("model.Product " + p.getName() + " is out of stock!");
            return false;
        }
    }

    public boolean removeProduct(Product p) throws IOException, InterruptedException, RuntimeException {
        if (products.remove(p)) {
            totalPrice = totalPrice.subtract(exchangeCurrency(p.getPrice().getAmount(), p.getPrice().getCurrency(), MoneyCurrency.EUR));
            p.setStock(p.getStock() + 1);
            return true;
        } else {
            System.out.println("model.Product " + p.getName() + " is not in the cart!");
            return false;
        }
    }

    public BigDecimal exchangeCurrency(BigDecimal amount, MoneyCurrency fromCurrency, MoneyCurrency toCurrency) throws IOException, InterruptedException {

        if (fromCurrency == MoneyCurrency.EUR) {
            return amount;
        }
        String url = "https://latest.currency-api.pages.dev/v1/currencies/eur.json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        String json = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response = mapper.readValue(json, Map.class);
        Map<String, Object> rates = (Map<String, Object>) response.get("eur");
        String to = toCurrency.toString().toLowerCase();
        String from = fromCurrency.toString().toLowerCase();

        if (fromCurrency == MoneyCurrency.EUR) {
            BigDecimal rate = new BigDecimal(rates.get(to).toString());
            return amount.multiply(rate);
        } else if (toCurrency == MoneyCurrency.EUR) {
            BigDecimal rate = new BigDecimal(rates.get(from).toString());
            return amount.divide(rate, 2, BigDecimal.ROUND_HALF_UP);
        }
        throw new IllegalArgumentException("Unsupported currency conversion");
    }

    public void clear() {
        products.clear();
        totalPrice = BigDecimal.ZERO;
    }
}