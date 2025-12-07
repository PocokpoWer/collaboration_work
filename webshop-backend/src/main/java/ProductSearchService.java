import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchService {

    public List<Product> searchByName(List<Product> products, String namePart) {
        List<Product> result = new ArrayList<>();
        if (namePart == null) {
            return result;
        }

        String lower = namePart.toLowerCase();
        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(lower)) {
                result.add(p);
            }
        }
        return result;
    }


    public List<Product> searchByPriceRange(List<Product> products, double maxPrice, double minPrice) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            double price = p.getPrice().getAmount(); //model.MonetaryAmount - double!!
            if (price >= minPrice && price <= maxPrice) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByAvailability(List<Product> products) {
        List<Product> result = new ArrayList<>();
        for(Product p : products) {
            if(p.getStock() > 0) {
                result.add(p);
            }
        }
        return result;
    }
}
