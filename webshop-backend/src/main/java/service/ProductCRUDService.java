package service;

import dao.Dao;
import lombok.AllArgsConstructor;
import model.Product;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductCRUDService {
    private final Dao<Product, Long> productDao;

    public Optional<Product> findByID(long id) {
        return productDao.findById(id);
    }

    public void addProduct(Product product) {
        productDao.save(product);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public void deleteProduct(long id) {
        productDao.delete(id);
    }
}