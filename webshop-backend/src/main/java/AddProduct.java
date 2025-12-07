import exceptions.MissingParamException;
import exceptions.ValidationException;
import model.MonetaryAmount;
import model.MoneyCurrency;
import model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddProduct {

    private final List<Product> products;
    private final ProductValidator validator;

    public AddProduct(List<Product> products, ProductValidator validator) {
        this.products = products;
        this.validator = validator;
    }

    public void execute(String arguments) throws MissingParamException, ValidationException {

        Map<String, String> params = parseParams(arguments);

        String name = params.get("name");
        String price = params.get("price");
        String stock = params.get("stock");

        if (name == null || price == null || stock == null) {
            throw new MissingParamException("name, price , or stock missing");
        }

        double priceValue;
        int stockValue;
        try {
            priceValue = Double.parseDouble(price);
            stockValue = Integer.parseInt(stock);
        } catch (NumberFormatException e) {
            throw new ValidationException("price or stock doesn't a number", e);
        }

        MonetaryAmount monetaryAmount = new MonetaryAmount(priceValue, MoneyCurrency.HUF);
        long newId = products.size() + 1L; //normal Id's before we don't had an order!
        Product product = new Product(newId, name, monetaryAmount, stockValue);

        if (!validator.isValid(product)) {
            throw new ValidationException("Data is not valid!");
        }
        products.add(product);
    }

    private Map<String, String> parseParams(String arguments) {
        Map<String, String> result = new HashMap<>();

        if (arguments == null || arguments.isBlank()) {
            return result;
        }

        String[] parts = arguments.split("\\s+");
        for (String part : parts) {
            String[] kv = part.split("=");
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[0].trim();
                result.put(key,value);
            }
        }
        return result;
    }
}
